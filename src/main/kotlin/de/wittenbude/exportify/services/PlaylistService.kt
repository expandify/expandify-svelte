package de.wittenbude.exportify.services

import de.wittenbude.exportify.connectors.Database
import de.wittenbude.exportify.connectors.SpotifyApiConnector
import de.wittenbude.exportify.connectors.upsert
import de.wittenbude.exportify.models.ExportifyUser
import de.wittenbude.exportify.models.Playlist
import de.wittenbude.exportify.models.convert

object PlaylistService {

    fun getUserPlaylists(exportifyUser: ExportifyUser): List<Playlist> {
        return SpotifyApiConnector(exportifyUser)
            .makePagingRequest(limit = 50) {spotifyApi -> spotifyApi.getListOfCurrentUsersPlaylists() }
            .mapNotNull { it.convert(TrackService.getPlaylistTracks(exportifyUser, it.id)) }
    }

    fun saveUserPlaylists(exportifyUser: ExportifyUser): List<Playlist> {
        return SpotifyApiConnector(exportifyUser)
            .makePagingRequest(limit = 50) {spotifyApi -> spotifyApi.getListOfCurrentUsersPlaylists() }
            .mapNotNull { it.convert(TrackService.savePlaylistTracks(exportifyUser, it.id)) }
            .onEach { Database.PLAYLIST.upsert(it) }
    }
}