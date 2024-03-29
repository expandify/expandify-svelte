package de.wittenbude.exportify.api.controller;

import de.wittenbude.exportify.api.schema.PrivateUserSchema;
import de.wittenbude.exportify.user.api.PrivateSpotifyUser;
import de.wittenbude.exportify.user.api.PublicSpotifyUser;
import de.wittenbude.exportify.user.api.SpotifyUserService;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
class UserController {

    private final SpotifyUserService spotifyUserService;

    public UserController(SpotifyUserService spotifyUserService) {
        this.spotifyUserService = spotifyUserService;
    }

    @GetMapping
    public PrivateUserSchema me() {
        Pair<PrivateSpotifyUser, PublicSpotifyUser> user = spotifyUserService.loadSpotifyUser();
        return PrivateUserSchema.from(user.getFirst(), user.getSecond());
    }
}
