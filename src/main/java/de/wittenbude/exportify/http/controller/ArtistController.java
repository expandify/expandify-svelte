package de.wittenbude.exportify.http.controller;

import de.wittenbude.exportify.services.SpotifyUserService;
import de.wittenbude.exportify.spotify.data.Artist;
import de.wittenbude.exportify.spotify.data.CursorPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    private final SpotifyUserService spotifyUserService;

    public ArtistController(SpotifyUserService spotifyUserService) {
        this.spotifyUserService = spotifyUserService;
    }

    @GetMapping("/followed")
    public CursorPage<Artist> followed() {
        return spotifyUserService.getCurrentUserFollowedArtists();
    }
}
