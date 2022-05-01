package de.wittenbude.exportify.services

import de.wittenbude.exportify.connectors.SpotifyApiConnector
import de.wittenbude.exportify.connectors.SpotifyApiConnector.Companion.SCOPES
import de.wittenbude.exportify.security.ExportifyJWT
import io.ktor.server.application.*


fun buildAuthRedirectUri(state: String): String {
    return SpotifyApiConnector
        .getSpotifyAuthApi()
        .authorizationCodeUri()
        .scope(SCOPES)
        .state(state)
        .show_dialog(true)
        .build()
        .execute()
        .toString()
}

fun authenticate(call: ApplicationCall): String? {
    val code = call.request.queryParameters["code"] ?: return null
    return try {
        SpotifyApiConnector
            .getSpotifyAuthApi()
            .authorizationCode(code)
            .build()
            .execute()
            ?.let { credentials ->
                saveCurrentSpotifyUser(credentials.accessToken, credentials.refreshToken)
                    ?.also { saveExportifyUser(it.id, credentials.accessToken, credentials.refreshToken) }
                    ?.let { ExportifyJWT.create(it.id) }
            }
    } catch (e: Exception) {
        null
    }
}
