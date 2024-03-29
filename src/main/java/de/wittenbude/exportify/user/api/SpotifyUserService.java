package de.wittenbude.exportify.user.api;

import org.springframework.data.util.Pair;

public interface SpotifyUserService {
    PrivateSpotifyUser loadSpotifyUser(String accessToken);

    Pair<PrivateSpotifyUser, PublicSpotifyUser> loadSpotifyUser();
}
