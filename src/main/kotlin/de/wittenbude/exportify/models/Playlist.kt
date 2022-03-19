@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package de.wittenbude.exportify.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import se.michaelthelin.spotify.enums.ModelObjectType
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified
import se.michaelthelin.spotify.model_objects.specification.Playlist as ApiPlaylist

@Serializable
data class Playlist(
    val collaborative: Boolean? = null,
    val description: String? = null,
    val externalUrls: Map<String, String>? = null,
    val followerHref: String? = null,
    val followerTotal: Int? = null,
    val href: String? = null,
    @SerialName("_id") val id: String,
    val images: List<String>? = null,
    val name: String,
    val owner: String? = null,
    val publicAccess: Boolean? = null,
    val snapshotId: String? = null,
    val tracks: List<String>? = null,
    val type: ModelObjectType? = null,
    val uri: String? = null,
)


fun PlaylistSimplified.convert(tracks: List<Track>?): Playlist {
    return Playlist(
        collaborative = isCollaborative,
        externalUrls = externalUrls?.externalUrls,
        href = href,
        id = id,
        images = images?.map { it.url },
        name = name,
        owner = owner?.id,
        publicAccess = isPublicAccess,
        snapshotId = snapshotId,
        tracks = tracks?.map { it.id },
        type = type,
        uri = uri
    )
}

fun ApiPlaylist.convert(tracks: List<Track>?): Playlist {
    return Playlist(
        this.isCollaborative,
        description,
        externalUrls?.externalUrls,
        followers?.href,
        followers?.total,
        href,
        id,
        images?.map { it.url },
        name,
        owner?.id,
        isPublicAccess,
        snapshotId,
        tracks?.map { it.id },
        type,
        uri
    )
}