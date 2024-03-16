package de.wittenbude.exportify.http.controller;

import de.wittenbude.exportify.db.entity.SpotifyUser;
import de.wittenbude.exportify.request.CurrentSpotifyUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final CurrentSpotifyUser currentSpotifyUser;

    public UserController(CurrentSpotifyUser currentSpotifyUser) {
        this.currentSpotifyUser = currentSpotifyUser;
    }

    @GetMapping
    public SpotifyUser me() {

        return currentSpotifyUser.getSpotifyUser();
    }
}
