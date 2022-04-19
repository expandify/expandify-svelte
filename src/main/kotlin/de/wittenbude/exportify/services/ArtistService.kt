package de.wittenbude.exportify.services

import de.wittenbude.exportify.connectors.Database
import de.wittenbude.exportify.connectors.SpotifyApiConnector
import de.wittenbude.exportify.connectors.upsert
import de.wittenbude.exportify.models.Artist
import de.wittenbude.exportify.models.ExportifyUser
import de.wittenbude.exportify.models.convert
import org.litote.kmongo.findOneById
import se.michaelthelin.spotify.enums.ModelObjectType


fun getFollowedArtists(exportifyUser: ExportifyUser): List<Artist> {
    return SpotifyApiConnector(exportifyUser)
        .makeCursorPagingRequest(limit = 50) { spotifyApi -> spotifyApi.getUsersFollowedArtists(ModelObjectType.ARTIST) }
        .mapNotNull { it.convert() }
}

fun getArtists(exportifyUser: ExportifyUser, vararg artists: String): List<Artist> {
    if (artists.isEmpty()) return emptyList()

    val existingArtists = getExistingArtists(*artists).toMutableList()
    val newArtist = getNewArtists(existingArtists, *artists)
    if (newArtist.isEmpty()) return existingArtists

    val newArtists = SpotifyApiConnector(exportifyUser)
        .makeRequest { spotifyApi -> spotifyApi.getSeveralArtists(*newArtist.toTypedArray()) }
        .mapNotNull { it.convert() }

    existingArtists.addAll(newArtists)
    return existingArtists
}

fun saveFollowedArtists(exportifyUser: ExportifyUser): List<Artist> {
    return getFollowedArtists(exportifyUser)
        .onEach { Database.ARTIST.upsert(it) }

}


fun saveArtists(exportifyUser: ExportifyUser, vararg artists: String): List<Artist> {
    if (artists.isEmpty()) return emptyList()

    val existingArtists = getExistingArtists(*artists).toMutableList()
    val newArtist = getNewArtists(existingArtists, *artists)
    if (newArtist.isEmpty()) return existingArtists

    val newArtists = SpotifyApiConnector(exportifyUser)
        .makeRequest { spotifyApi -> spotifyApi.getSeveralArtists(*newArtist.toTypedArray()) }
        .mapNotNull { it.convert() }
        .onEach { Database.ARTIST.upsert(it) }

    existingArtists.addAll(newArtists)
    return existingArtists
}

private fun getExistingArtists(vararg artists: String): List<Artist> {
    val result: MutableList<Artist> = ArrayList()
    artists.onEach { artist ->
        Database.ARTIST.findOneById(artist)?.also {
            result.add(it)
        }
    }
    return result
}

private fun getNewArtists(existing: List<Artist>, vararg allArtist: String): List<String> {
    return allArtist.toMutableList().apply { this.removeAll { artist -> existing.map { it.id }.contains(artist) } }
}

