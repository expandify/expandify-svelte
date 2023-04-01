package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.services.spotifydata.ArtistService;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    // private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/latest")
    public List<Artist> getOrLoadLatest() throws SpotifyWebApiException {
        return artistService.getOrLoadLatest();
    }


    @GetMapping("/{id}")
    public Artist getArtist(@PathVariable String id) {
        return null;
    }

}
