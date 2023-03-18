package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.services.spotifydata.SpotifyTrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/track")
public class TrackController {

    private static final Logger LOG = LoggerFactory.getLogger(TrackController.class);
    private final SpotifyTrackService trackService;

    public TrackController(SpotifyTrackService trackService) {
        this.trackService = trackService;
    }

}
