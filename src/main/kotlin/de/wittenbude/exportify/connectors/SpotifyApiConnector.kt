package de.wittenbude.exportify.connectors

import de.wittenbude.exportify.models.ExportifyUser
import io.ktor.server.application.*
import se.michaelthelin.spotify.SpotifyApi
import se.michaelthelin.spotify.SpotifyHttpManager
import se.michaelthelin.spotify.exceptions.detailed.TooManyRequestsException
import se.michaelthelin.spotify.requests.IRequest
import se.michaelthelin.spotify.requests.data.IPagingCursorbasedRequestBuilder
import se.michaelthelin.spotify.requests.data.IPagingRequestBuilder
import java.net.URI

class SpotifyApiConnector(private val exportifyUser: ExportifyUser) {

    companion object {
        private var CLIENT_SECRET: String = ""
        private var CLIENT_ID: String = ""
        private var REDIRECT_URI: URI = URI("")
        const val SCOPES =
            "playlist-read-private,playlist-read-collaborative,user-library-read,user-follow-read,user-read-private"

        fun Application.configureSpotifyApi() {
            CLIENT_ID = environment.config.property("spotify.clientId").getString()
            CLIENT_SECRET = environment.config.property("spotify.clientSecret").getString()
            REDIRECT_URI = SpotifyHttpManager.makeUri("http://localhost:8080/callback")
        }

        fun getSpotifyAuthApi(): SpotifyApi {
            return SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRedirectUri(REDIRECT_URI)
                .build()
        }
    }

    private fun getSpotifyAccessApi(exportifyUser: ExportifyUser): SpotifyApi {
        return SpotifyApi
            .Builder()
            .setAccessToken(exportifyUser.accessToken)
            .setRefreshToken(exportifyUser.refreshToken)
            .build()
    }

    fun <T> makeRequest(request: (SpotifyApi) -> IRequest.Builder<T, *>): T {
        return try {
            request(getSpotifyAccessApi(exportifyUser))
                .build()
                .execute()
        } catch (tooManyRequests: TooManyRequestsException) {
            Thread.sleep((tooManyRequests.retryAfter * 1000).toLong())
            makeRequest(request)
        }
    }

    fun <T, BT : IPagingRequestBuilder<T, BT>> makePagingRequest(
        offset: Int = 0,
        limit: Int,
        pagingRequest: (SpotifyApi) -> IPagingRequestBuilder<T, BT>
    ): List<T> {
        val tracks: MutableList<T> = ArrayList()
        var finished = false
        var currentOffset = offset

        do {
            try {
                pagingRequest(getSpotifyAccessApi(exportifyUser))
                    .offset(currentOffset)
                    .limit(limit)
                    .build()
                    .execute()
                    .also { finished = it.next == null }
                    .also { tracks.addAll(it.items) }
                currentOffset += limit
            } catch (tooManyRequests: TooManyRequestsException) {
                Thread.sleep((tooManyRequests.retryAfter * 1000).toLong())
            }
        } while (!finished)

        return tracks
    }

    fun <T, BT : IPagingCursorbasedRequestBuilder<T, String, BT>> makeCursorPagingRequest(
        limit: Int,
        pagingRequest: (SpotifyApi) -> IPagingCursorbasedRequestBuilder<T, String, BT>
    ): List<T> {
        val tracks: MutableList<T> = ArrayList()
        var finished = false
        var cursor: String? = null

        do {
            try {
                pagingRequest(getSpotifyAccessApi(exportifyUser))
                    .apply { cursor?.let { this.after(it) } }
                    .limit(limit)
                    .build()
                    .execute()
                    .also { finished = it.next == null }
                    .also { tracks.addAll(it.items) }
                    .also { cursor = it?.cursors?.get(0)?.after }
            } catch (tooManyRequests: TooManyRequestsException) {
                Thread.sleep((tooManyRequests.retryAfter * 1000).toLong())
            }
        } while (!finished)

        return tracks
    }
}
