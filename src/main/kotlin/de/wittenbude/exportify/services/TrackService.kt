package de.wittenbude.exportify.services

import de.wittenbude.exportify.connectors.Database
import de.wittenbude.exportify.connectors.SpotifyApiConnector
import de.wittenbude.exportify.connectors.upsert
import de.wittenbude.exportify.models.ExportifyUser
import de.wittenbude.exportify.models.Track
import de.wittenbude.exportify.models.convert
import org.litote.kmongo.findOneById

object TrackService {

    fun getCurrentUserTracks(exportifyUser: ExportifyUser): List<Track> {
        return SpotifyApiConnector(exportifyUser)
            .makePagingRequest(limit = 50) { spotifyApi -> spotifyApi.getUsersSavedTracks() }
            .mapNotNull { track ->
                track.convert(track.track.album.id, track.track.artists.mapNotNull { it.id })
            }
    }

    fun getPlaylistTracks(exportifyUser: ExportifyUser, playlistId: String, offset: Int = 0): List<Track> {
        return SpotifyApiConnector(exportifyUser)
            .makePagingRequest(offset, 50) { spotifyApi -> spotifyApi.getPlaylistsItems(playlistId) }
            .mapNotNull { it.convert() }
    }

    fun getAlbumTracks(exportifyUser: ExportifyUser, albumId: String): List<Track> {
        val tracks = Database.ALBUM.findOneById(albumId)?.tracks?.map { Database.TRACK.findOneById(it) }
        if (tracks != null && tracks.all { track -> track != null }) return tracks.requireNoNulls()

        return SpotifyApiConnector(exportifyUser)
            .makePagingRequest(limit = 50) { spotifyApi -> spotifyApi.getAlbumsTracks(albumId) }
            .mapNotNull { track ->
                track.convert(albumId, track.artists.mapNotNull { it.id })
            }
    }

    fun saveCurrentUserTracks(exportifyUser: ExportifyUser): List<Track> {
        return SpotifyApiConnector(exportifyUser)
            .makePagingRequest(limit = 50) { spotifyApi -> spotifyApi.getUsersSavedTracks() }
            .mapNotNull { track ->
                val artists = ArtistService.saveArtists(exportifyUser, *track.track.artists.map { it.id }.toTypedArray())
                //This would slow down the saving drastically. But would make sure, that alle Albums from the Tracks are present
                //val album = AlbumService.saveAlbum(exportifyUser, track.track?.album?.id)
                //track.convert(album?.id, artists.map { it.id })
                track.convert(track.track?.album?.id, artists.map { it.id })
            }
            .onEach { Database.TRACK.upsert(it) }
    }

    fun savePlaylistTracks(exportifyUser: ExportifyUser, playlistId: String, offset: Int = 0): List<Track> {
        return getPlaylistTracks(exportifyUser, playlistId, offset)
            .onEach { Database.TRACK.upsert(it) }
    }

    fun saveAlbumTracks(exportifyUser: ExportifyUser, albumId: String): List<Track> {
        val tracks = Database.ALBUM.findOneById(albumId)?.tracks?.map { Database.TRACK.findOneById(it) }
        if (tracks != null && tracks.all { track -> track != null }) return tracks.requireNoNulls()
        return SpotifyApiConnector(exportifyUser)
            .makePagingRequest(limit = 50) { spotifyApi -> spotifyApi.getAlbumsTracks(albumId) }
            .mapNotNull { track ->
                val artists = ArtistService.saveArtists(exportifyUser, *track.artists.map { it.id }.toTypedArray())
                track.convert(albumId, artists.map { it.id })
            }
            .onEach { Database.TRACK.upsert(it) }
    }


}