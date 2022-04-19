package de.wittenbude.exportify.services

import de.wittenbude.exportify.connectors.Database
import de.wittenbude.exportify.connectors.SpotifyApiConnector
import de.wittenbude.exportify.connectors.upsert
import de.wittenbude.exportify.models.ExportifyUser
import de.wittenbude.exportify.models.SpotifyUser
import de.wittenbude.exportify.models.convert
import org.litote.kmongo.findOneById


fun saveExportifyUser(exportifyUser: ExportifyUser) {
    Database.EXPORTIFY_USER.upsert(exportifyUser)
}

fun saveExportifyUser(id: String, accessToken: String, refreshToken: String) {
    saveExportifyUser(ExportifyUser(id, accessToken, refreshToken))
}

fun loadExportifyUser(id: String): ExportifyUser? {
    return Database.EXPORTIFY_USER.findOneById(id)
}

fun saveCurrentSpotifyUser(exportifyUser: ExportifyUser): SpotifyUser? {
    return getCurrentSpotifyUser(exportifyUser)
        ?.also { Database.SPOTIFY_USER.upsert(it) }
}

fun saveCurrentSpotifyUser(accessToken: String, refreshToken: String): SpotifyUser? {
    return saveCurrentSpotifyUser(ExportifyUser(null, accessToken, refreshToken))
}

fun getCurrentSpotifyUser(exportifyUser: ExportifyUser): SpotifyUser? {
    return SpotifyApiConnector(exportifyUser)
        .makeRequest { spotifyApi -> spotifyApi.getCurrentUsersProfile() }
        ?.convert()
}
