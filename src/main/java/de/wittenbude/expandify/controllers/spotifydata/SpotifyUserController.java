package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyUser;
import de.wittenbude.expandify.services.spotifydata.SpotifyUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

@RestController
@RequestMapping("/user")
public class SpotifyUserController {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyUserController.class);
    private final SpotifyUserService spotifyUserService;

    public SpotifyUserController(SpotifyUserService spotifyUserService) {
        this.spotifyUserService = spotifyUserService;
    }

    @GetMapping()
    public SpotifyUser get() throws SpotifyWebApiException {
        return spotifyUserService.loadCurrent();
    }

}
