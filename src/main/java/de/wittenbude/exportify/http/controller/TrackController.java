package de.wittenbude.exportify.http.controller;

import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.services.TrackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/saved")
    public List<Artist> saved() {
        return trackService.loadSavedTracks();
    }
}
