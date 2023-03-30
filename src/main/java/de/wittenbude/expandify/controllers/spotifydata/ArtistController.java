package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.services.spotifydata.ArtistService;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    // private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/saved")
    public List<Artist> loadFollowedArtist(@RequestParam(required = false) String after) throws SpotifyWebApiException {
        return artistService.loadFollowedArtists(after);
    }


    @GetMapping("/{id}")
    public Artist getArtist(@PathVariable String id) {
        return null;
    }

}
