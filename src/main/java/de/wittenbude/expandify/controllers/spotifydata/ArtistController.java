package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.services.spotifydata.ArtistService;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    // private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);
    private final ArtistService artistService;

    public ArtistController(
            ArtistService artistService
    ) {
        this.artistService = artistService;
    }

    @GetMapping("/followed")
    public List<Artist> getFollowed() throws SpotifyWebApiException {
        return artistService.getFollowed();
    }

    @GetMapping("/{id}")
    public Artist get(@PathVariable String id) throws SpotifyWebApiException {
        return artistService.get(id);
    }

}
