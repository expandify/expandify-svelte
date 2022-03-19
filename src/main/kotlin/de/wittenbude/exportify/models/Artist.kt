@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package de.wittenbude.exportify.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import se.michaelthelin.spotify.enums.ModelObjectType
import se.michaelthelin.spotify.model_objects.specification.Artist as ApiArtist

@Serializable
data class Artist(
    val externalUrls: Map<String, String>? = null,
    val followerHref: String? = null,
    val followerTotal: Int? = null,
    val genres: List<String>? = null,
    val href: String? = null,
    @SerialName("_id") val id: String,
    val images: List<String>? = null,
    val name: String,
    val popularity: Int? = null,
    val type: ModelObjectType? = null,
    val uri: String? = null,
)


fun ApiArtist.convert() : Artist {
    return Artist(
        externalUrls = externalUrls?.externalUrls,
        followerHref = followers?.href,
        followerTotal = followers?.total,
        genres = genres?.asList(),
        href = href,
        id = id,
        images = images?.map { it.url },
        name = name,
        popularity = popularity,
        type = type,
        uri = uri
    )
}