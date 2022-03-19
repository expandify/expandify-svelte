@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package de.wittenbude.exportify.models

import com.neovisionaries.i18n.CountryCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import se.michaelthelin.spotify.enums.ModelObjectType
import se.michaelthelin.spotify.model_objects.IPlaylistItem
import se.michaelthelin.spotify.model_objects.specification.*
import se.michaelthelin.spotify.model_objects.specification.Track as ApiTrack

@Serializable
data class Track(
    val albumId: String? = null,
    val artistIds: List<String>? = null,
    val availableMarkets: List<CountryCode>? = null,
    var addedBy: String? = null,
    var addedAt: String? = null,
    val discNumber: Int? = null,
    val durationMs: Int? = null,
    val explicit: Boolean? = null,
    val externalIds: Map<String, String>? = null,
    val externalUrls: Map<String, String>? = null,
    val href: String? = null,
    @SerialName("_id") val id: String,
    val isPlayable: Boolean? = null,
    val linkedFrom: String? = null,
    var isLocal: Boolean? = null,
    val restrictions: String? = null,
    val name: String,
    val popularity: Int? = null,
    val previewUrl: String? = null,
    val trackNumber: Int? = null,
    val type: ModelObjectType? = null,
    val uri: String? = null,
)

fun SavedTrack.convert(album: String?, artists: List<String>?): Track {
    val track = this.track.convert(album, artists)
    track.addedAt = this.addedAt?.toString()
    return track
}

fun TrackSimplified.convert(album: String, artists: List<String>?): Track {
    return Track(
        artistIds = artists,
        albumId = album,
        availableMarkets = availableMarkets?.asList(),
        discNumber = discNumber,
        durationMs = durationMs,
        explicit = isExplicit,
        externalUrls = externalUrls?.externalUrls,
        href = href,
        id = id,
        isPlayable = isPlayable,
        linkedFrom = linkedFrom?.toString(),
        name = name,
        previewUrl = previewUrl,
        trackNumber = trackNumber,
        type = type,
        uri = uri
    )
}

fun ApiTrack.convert(album: String?, artists: List<String>?): Track {
    return Track(
        albumId = album,
        artistIds = artists,
        availableMarkets = availableMarkets?.asList(),
        discNumber = discNumber,
        durationMs = durationMs,
        explicit = isExplicit,
        externalIds = externalIds?.externalIds,
        externalUrls = externalUrls?.externalUrls,
        href = href,
        id = id,
        isPlayable = isPlayable,
        linkedFrom = linkedFrom?.toString(),
        restrictions = restrictions?.reason,
        name = name,
        popularity = popularity,
        previewUrl = previewUrl,
        trackNumber = trackNumber,
        type = type,
        uri = uri
    )
}

fun IPlaylistItem.convert(): Track {
    return Track(
        durationMs = durationMs,
        externalUrls = externalUrls?.externalUrls,
        href = href,
        id = id,
        name = name,
        type = type,
        uri = uri
    )
}

fun PlaylistTrack.convert(): Track {
    val track = this.track.convert()
    track.addedAt = this.addedAt?.toString()
    track.addedBy = this.addedBy?.id
    track.isLocal = this.isLocal
    return track
}