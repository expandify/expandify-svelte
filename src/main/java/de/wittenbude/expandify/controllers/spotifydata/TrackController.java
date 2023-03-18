package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.helper.SpotifySavedTrack;
import de.wittenbude.expandify.services.spotifydata.SpotifyTrackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/track")
public class TrackController {

    // private static final Logger LOG = LoggerFactory.getLogger(TrackController.class);
    private final SpotifyTrackService trackService;

    public TrackController(SpotifyTrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/saved")
    public List<SpotifySavedTrack> loadSavedTracks(@RequestParam(required = false) Integer offset) throws SpotifyWebApiException {
        return trackService.loadSavedTracks(offset == null ? 0 : offset);
    }

    /*
    @GetMapping("/{id}")
    public SpotifyTrack getTrack(@PathVariable String id) {
        return null;
    }
    */
}
