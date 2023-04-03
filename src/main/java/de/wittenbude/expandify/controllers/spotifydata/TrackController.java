package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Track;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.services.spotifydata.TrackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<SavedTrack> getSaved() throws SpotifyWebApiException {
        return trackService.getLatest();
    }

    @GetMapping("/{id}")
    public Track get(@PathVariable String id) throws SpotifyWebApiException {
        return trackService.get(id);
    }

}
