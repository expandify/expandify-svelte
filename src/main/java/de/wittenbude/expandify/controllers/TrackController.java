package de.wittenbude.expandify.controllers;

import de.wittenbude.expandify.models.db.Track;
import de.wittenbude.expandify.models.pojos.SavedTrack;
import de.wittenbude.expandify.services.auth.AuthenticatedUserService;
import de.wittenbude.expandify.services.spotify.TrackService;
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
    private final AuthenticatedUserService authenticatedUserService;

    public TrackController(
            TrackService trackService,
            AuthenticatedUserService authenticatedUserService) {
        this.trackService = trackService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping("/saved")
    public List<SavedTrack> getSaved() throws SpotifyWebApiException {
        return trackService.getLatest(authenticatedUserService.getUserId());
    }

    @GetMapping("/{id}")
    public Track get(@PathVariable String id) throws SpotifyWebApiException {
        return trackService.get(id);
    }

}
