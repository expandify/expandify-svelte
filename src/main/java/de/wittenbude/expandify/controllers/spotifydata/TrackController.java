package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Track;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.services.spotifydata.TrackService;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    // private static final Logger LOG = LoggerFactory.getLogger(TrackController.class);
    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/saved")
    public List<SavedTrack> loadSavedTracks(@RequestParam(required = false) Integer offset) throws SpotifyWebApiException {
        return trackService.loadSavedTracks(offset == null ? 0 : offset);
    }


    @GetMapping("/{id}")
    public Track getTrack(@PathVariable String id) {
        return null;
    }

}
