package de.wittenbude.expandify.controllers;

import de.wittenbude.expandify.models.Library;
import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.services.spotifydata.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    private static final Logger LOG = LoggerFactory.getLogger(LibraryController.class);
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }


    @GetMapping("/save")
    public Library saveAsLibrary(
            @RequestBody List<SavedAlbum> savedAlbums,
            @RequestBody List<SavedTrack> savedTracks,
            @RequestBody List<Artist> followedArtists,
            @RequestBody List<PlaylistSimplified> playlists,
            @RequestBody boolean latest
    ) throws SpotifyWebApiException {
        return libraryService.createLibrary(savedAlbums, savedTracks, followedArtists, playlists, latest);
    }


    @GetMapping("/latest/save")
    public Library saveLatest(
            @RequestBody List<SavedAlbum> savedAlbums,
            @RequestBody List<SavedTrack> savedTracks,
            @RequestBody List<Artist> followedArtists,
            @RequestBody List<PlaylistSimplified> playlists
    ) throws SpotifyWebApiException {
        return libraryService.createLibrary(savedAlbums, savedTracks, followedArtists, playlists, true);
    }

    @GetMapping("/save")
    public Library save(
            @RequestBody List<SavedAlbum> savedAlbums,
            @RequestBody List<SavedTrack> savedTracks,
            @RequestBody List<Artist> followedArtists,
            @RequestBody List<PlaylistSimplified> playlists
    ) throws SpotifyWebApiException {
        return libraryService.createLibrary(savedAlbums, savedTracks, followedArtists, playlists, false);
    }

    @GetMapping("/{id}")
    public Library getById(@PathVariable String id) {
        return null;
    }

    @GetMapping("/{id}")
    public Library getLatest() {
        return null;
    }

}
