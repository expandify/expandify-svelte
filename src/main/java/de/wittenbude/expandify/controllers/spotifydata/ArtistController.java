package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.services.spotifydata.ArtistService;
import de.wittenbude.expandify.services.CacheService;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    // private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);
    private final ArtistService artistService;
    private final CacheService cacheService;

    public ArtistController(
            ArtistService artistService,
            CacheService cacheService
    ) {
        this.artistService = artistService;
        this.cacheService = cacheService;
    }

    @GetMapping("/latest")
    public List<Artist> getOrLoadLatest() throws SpotifyWebApiException {
        List<Artist> cached = cacheService.get().getFollowedArtists();

        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        List<Artist> artists = artistService.getLatest();
        return cacheService.setArtists(artists);
    }

}
