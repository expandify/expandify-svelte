package de.wittenbude.exportify.application.controller;

import de.wittenbude.exportify.domain.context.album.AlbumService;
import de.wittenbude.exportify.domain.context.artist.ArtistService;
import de.wittenbude.exportify.domain.context.playlist.PlaylistService;
import de.wittenbude.exportify.domain.context.track.TrackService;
import de.wittenbude.exportify.domain.entities.Artist;
import de.wittenbude.exportify.domain.entities.Playlist;
import de.wittenbude.exportify.domain.valueobjects.SavedAlbum;
import de.wittenbude.exportify.domain.valueobjects.SavedTrack;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/test")
class TestController {

    private final PlaylistService playlistService;
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final TrackService trackService;

    public TestController(PlaylistService playlistService, ArtistService artistService, AlbumService albumService, TrackService trackService) {
        this.playlistService = playlistService;
        this.artistService = artistService;
        this.albumService = albumService;
        this.trackService = trackService;
    }

    @GetMapping("/artists")
    public Set<Artist> artists() {
        return artistService.loadFollowedArtists();
    }

    @GetMapping("/albums")
    public Set<SavedAlbum> albums() {
        return albumService.loadSavedAlbums();
    }

    @GetMapping("/playlists")
    public Set<Playlist> playlists() {
        return playlistService.loadUserPlaylists();
    }

    @GetMapping("/tracks")
    public Set<SavedTrack> tracks() {
        return trackService.loadSavedTracks();
    }

}
