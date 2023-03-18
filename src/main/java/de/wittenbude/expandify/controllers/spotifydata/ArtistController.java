package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyArtist;
import de.wittenbude.expandify.services.spotifydata.SpotifyArtistService;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    // private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);
    private final SpotifyArtistService artistService;

    public ArtistController(SpotifyArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/saved")
    public List<SpotifyArtist> loadFollowedArtist(@RequestParam(required = false) String after) throws SpotifyWebApiException {
        return artistService.loadFollowedArtists(after);
    }

    /*
    @GetMapping("/{id}")
    public SpotifyArtist getArtist(@PathVariable String id) {
        return null;
    }
     */
}
