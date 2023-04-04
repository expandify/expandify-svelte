package de.wittenbude.expandify.controllers;

import de.wittenbude.expandify.models.db.Artist;
import de.wittenbude.expandify.services.auth.AuthenticatedUserService;
import de.wittenbude.expandify.services.spotify.ArtistService;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    // private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);
    private final ArtistService artistService;
    private final AuthenticatedUserService authenticatedUserService;

    public ArtistController(
            ArtistService artistService,
            AuthenticatedUserService authenticatedUserService) {
        this.artistService = artistService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping("/followed")
    public List<Artist> getFollowed() throws SpotifyWebApiException {
        return artistService.getFollowed(authenticatedUserService.getUserId());
    }

    @GetMapping("/{id}")
    public Artist get(@PathVariable String id) throws SpotifyWebApiException {
        return artistService.get(id);
    }

}
