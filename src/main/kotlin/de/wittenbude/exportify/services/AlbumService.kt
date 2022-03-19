package de.wittenbude.exportify.services

import de.wittenbude.exportify.connectors.Database
import de.wittenbude.exportify.connectors.SpotifyApiConnector
import de.wittenbude.exportify.connectors.upsert
import de.wittenbude.exportify.models.Album
import de.wittenbude.exportify.models.ExportifyUser
import de.wittenbude.exportify.models.convert
import org.litote.kmongo.findOneById

object AlbumService {
    fun getUserAlbums(exportifyUser: ExportifyUser): List<Album> {
        return SpotifyApiConnector(exportifyUser)
            .makePagingRequest(limit = 50) {spotifyApi -> spotifyApi.getCurrentUsersSavedAlbums() }
            .mapNotNull { album ->
                val tracks = TrackService.getAlbumTracks(exportifyUser, album.album.id)
                album.convert(tracks, album.album.artists.map { it.id })
            }
    }

    fun getAlbum(exportifyUser: ExportifyUser, albumId: String?): Album? {
        if (albumId == null) return null
        Database.ALBUM.findOneById(albumId)?.run { return this }
        return SpotifyApiConnector(exportifyUser)
            .makeRequest {spotifyApi -> spotifyApi.getAlbum(albumId) }
            ?.let { album ->
                val tracks = TrackService.getAlbumTracks(exportifyUser, album.id)
                album.convert(tracks, album.artists.map { it.id })
            }
    }

    fun saveUserAlbums(exportifyUser: ExportifyUser): List<Album> {
        return SpotifyApiConnector(exportifyUser)
            .makePagingRequest(limit = 50) {spotifyApi -> spotifyApi.getCurrentUsersSavedAlbums() }
            .mapNotNull { album ->
                val artists = ArtistService.saveArtists(exportifyUser, *album.album.artists.map { it.id }.toTypedArray())
                val tracks = TrackService.saveAlbumTracks(exportifyUser, album.album.id)
                album.convert(tracks, artists.map { it.id })
            }
            .onEach { Database.ALBUM.upsert(it) }
    }

    fun saveAlbum(exportifyUser: ExportifyUser, albumId: String?): Album? {
        if (albumId == null) return null
        Database.ALBUM.findOneById(albumId)?.run { return this }
        return SpotifyApiConnector(exportifyUser)
            .makeRequest {spotifyApi -> spotifyApi.getAlbum(albumId) }
            ?.let { album ->
                val artists = ArtistService.saveArtists(exportifyUser, *album.artists.map { it.id }.toTypedArray())
                val tracks = TrackService.saveAlbumTracks(exportifyUser, album.id)
                album.convert(tracks, artists.map { it.id })
            }
            ?.also { Database.ALBUM.upsert(it) }
    }
}