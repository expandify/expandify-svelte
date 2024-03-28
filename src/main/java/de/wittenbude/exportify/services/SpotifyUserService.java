package de.wittenbude.exportify.services;

import de.wittenbude.exportify.models.PrivateSpotifyUser;
import de.wittenbude.exportify.models.PublicSpotifyUser;
import de.wittenbude.exportify.models.converter.UserConverter;
import de.wittenbude.exportify.repositories.PrivateUserRepository;
import de.wittenbude.exportify.repositories.PublicSpotifyUserRepository;
import de.wittenbude.exportify.spotify.clients.SpotifyUserClient;
import de.wittenbude.exportify.spotify.data.SpotifyPrivateUser;
import de.wittenbude.exportify.spotify.data.SpotifyPublicUser;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class SpotifyUserService {


    private final SpotifyUserClient spotifyUserClient;
    private final PublicSpotifyUserRepository publicSpotifyUserRepository;
    private final PrivateUserRepository privateUserRepository;

    public SpotifyUserService(SpotifyUserClient spotifyUserClient,
                              PublicSpotifyUserRepository publicSpotifyUserRepository,
                              PrivateUserRepository privateUserRepository) {
        this.spotifyUserClient = spotifyUserClient;
        this.publicSpotifyUserRepository = publicSpotifyUserRepository;
        this.privateUserRepository = privateUserRepository;
    }

    public PrivateSpotifyUser loadSpotifyUser(String accessToken) {
        return doLoad("Bearer " + accessToken).getFirst();
    }

    public Pair<PrivateSpotifyUser, PublicSpotifyUser> loadSpotifyUser() {
        return doLoad(null);
    }

    private Pair<PrivateSpotifyUser, PublicSpotifyUser> doLoad(String accessToken) {
        SpotifyPrivateUser user = spotifyUserClient.getCurrentUser(accessToken);
        PublicSpotifyUser publicUser = publicSpotifyUserRepository.upsert(UserConverter.from((SpotifyPublicUser) user));
        PrivateSpotifyUser privateUser = privateUserRepository.upsert(UserConverter.from(user));

        return Pair.of(privateUser, publicUser);
    }
}
