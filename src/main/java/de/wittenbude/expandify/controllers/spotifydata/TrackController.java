package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.services.CacheService;
import de.wittenbude.expandify.services.spotifydata.TrackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    // private static final Logger LOG = LoggerFactory.getLogger(TrackController.class);
    private final TrackService trackService;
    private final CacheService cacheService;

    public TrackController(TrackService trackService, CacheService cacheService) {
        this.trackService = trackService;
        this.cacheService = cacheService;
    }

    @GetMapping("/latest")
    public List<SavedTrack> getOrLoadLatest() throws SpotifyWebApiException {
        List<SavedTrack> cached = cacheService.get().getSavedTracks();

        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        List<SavedTrack> tracks = trackService.getLatest();
        return cacheService.setTracks(tracks);
    }

}
