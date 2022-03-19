@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package de.wittenbude.exportify.models

import com.neovisionaries.i18n.CountryCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import se.michaelthelin.spotify.enums.ModelObjectType
import se.michaelthelin.spotify.enums.ProductType
import se.michaelthelin.spotify.model_objects.specification.User

@Serializable
data class SpotifyUser(
    val birthdate: String?,
    val country: CountryCode?,
    val displayName: String,
    val email: String?,
    val externalUrls: Map<String, String>?,
    val followerHref: String?,
    val followerTotal: Int?,
    val href: String?,
    @SerialName("_id") val id: String,
    val images: List<String>?,
    val product: ProductType?,
    val type: ModelObjectType?,
    val uri: String?
)

fun User.convert(): SpotifyUser {
    return SpotifyUser(
        birthdate,
        country,
        displayName,
        email,
        externalUrls?.externalUrls,
        followers?.href,
        followers?.total,
        href,
        id,
        images?.map { it.url },
        product,
        type,
        uri
    )
}