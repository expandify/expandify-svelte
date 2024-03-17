package de.wittenbude.exportify.request;

import de.wittenbude.exportify.db.entity.SpotifyUser;
import de.wittenbude.exportify.db.repositories.SpotifyUserRepository;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
@Getter
public class CurrentSpotifyUser {

    private final SpotifyUser spotifyUser;

    public CurrentSpotifyUser(SpotifyUserRepository spotifyUserRepository,
                              CurrentAuthenticatedUser currentAuthenticatedUser) {
        this.spotifyUser = spotifyUserRepository.findById(currentAuthenticatedUser.getUserID()).orElse(null);
    }
}
