@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package de.wittenbude.exportify.models

import com.neovisionaries.i18n.CountryCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import se.michaelthelin.spotify.enums.AlbumType
import se.michaelthelin.spotify.enums.CopyrightType
import se.michaelthelin.spotify.enums.ModelObjectType
import se.michaelthelin.spotify.model_objects.specification.SavedAlbum
import se.michaelthelin.spotify.model_objects.specification.Album as ApiAlbum

@Serializable
data class Album(
    var addedAt: String? = null,
    val albumType: AlbumType? = null,
    val artists: List<String>? = null,
    val availableMarkets: List<CountryCode>? = null,
    val copyrights: Map<CopyrightType, String>? = null,
    val externalIds: Map<String, String>? = null,
    val externalUrls: Map<String, String>? = null,
    val genres: List<String>? = null,
    val href: String? = null,
    @SerialName("_id") val id: String,
    val images: List<String>? = null,
    val label: String? = null,
    val name: String,
    val popularity: Int? = null,
    val releaseDate: String? = null,
    val releaseDatePrecision: String? = null,
    val tracks: List<String>? = null,
    val type: ModelObjectType? = null,
    val uri: String? = null
)

fun SavedAlbum.convert(tracks: List<Track>?, artists: List<String>?) : Album {
    val album = this.album.convert(tracks, artists)
    album.addedAt = this.addedAt?.toString()
    return album
}

fun ApiAlbum.convert(tracks: List<Track>?, artists: List<String>?): Album {
    return Album(
        albumType = albumType,
        artists = artists,
        availableMarkets = availableMarkets?.asList(),
        copyrights = copyrights?.associate { it.type to it.text },
        externalIds = externalIds?.externalIds,
        externalUrls = externalUrls?.externalUrls,
        genres = genres?.toList(),
        href = href,
        id = id,
        images = images?.map { it.url },
        label = label,
        name = name,
        popularity = popularity,
        releaseDate = releaseDate,
        releaseDatePrecision = releaseDatePrecision?.getPrecision(),
        tracks = tracks?.map { it.id },
        type = type,
        uri = uri,
    )
}