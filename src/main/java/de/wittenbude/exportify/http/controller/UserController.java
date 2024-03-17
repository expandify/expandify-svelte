package de.wittenbude.exportify.http.controller;

import de.wittenbude.exportify.db.entity.SpotifyUser;
import de.wittenbude.exportify.services.SpotifyUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final SpotifyUserService spotifyUserService;

    public UserController(SpotifyUserService spotifyUserService) {
        this.spotifyUserService = spotifyUserService;
    }

    @GetMapping
    public SpotifyUser me() {

        return spotifyUserService.getCurrentUser();
    }
}
