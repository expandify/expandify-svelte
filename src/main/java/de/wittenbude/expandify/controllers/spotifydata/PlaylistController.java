package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.services.CacheService;
import de.wittenbude.expandify.services.spotifydata.PlaylistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    // private static final Logger LOG = LoggerFactory.getLogger(PlaylistController.class);
    private final PlaylistService playlistService;
    private final CacheService cacheService;

    public PlaylistController(
            PlaylistService playlistService,
            CacheService cacheService
    ) {
        this.playlistService = playlistService;
        this.cacheService = cacheService;
    }

    @GetMapping("/latest")
    public List<PlaylistSimplified> getOrLoadLatest() throws SpotifyWebApiException {
        List<PlaylistSimplified> cached = cacheService.get().getPlaylists();

        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        List<PlaylistSimplified> playlists = playlistService.getLatest();
        return cacheService.setPlaylists(playlists);
    }

}
