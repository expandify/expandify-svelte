package de.wittenbude.exportify.controllers;

import de.wittenbude.exportify.dto.PrivateUserSchema;
import de.wittenbude.exportify.models.PrivateSpotifyUser;
import de.wittenbude.exportify.models.PublicSpotifyUser;
import de.wittenbude.exportify.models.converter.UserConverter;
import de.wittenbude.exportify.services.SpotifyUserService;
import org.springframework.data.util.Pair;
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
    public PrivateUserSchema me() {
        Pair<PrivateSpotifyUser, PublicSpotifyUser> user = spotifyUserService.loadSpotifyUser();
        return UserConverter.toDTO(user.getFirst(), user.getSecond());
    }
}
