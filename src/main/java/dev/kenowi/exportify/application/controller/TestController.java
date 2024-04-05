package dev.kenowi.exportify.application.controller;

import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.entities.Playlist;
import dev.kenowi.exportify.domain.service.album.AlbumService;
import dev.kenowi.exportify.domain.service.artist.ArtistService;
import dev.kenowi.exportify.domain.service.playlist.PlaylistService;
import dev.kenowi.exportify.domain.service.track.TrackService;
import dev.kenowi.exportify.domain.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.valueobjects.SavedTrack;
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
