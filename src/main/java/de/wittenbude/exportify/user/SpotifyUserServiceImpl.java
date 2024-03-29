package de.wittenbude.exportify.user;

import de.wittenbude.exportify.spotify.clients.SpotifyUserClient;
import de.wittenbude.exportify.spotify.data.SpotifyPrivateUser;
import de.wittenbude.exportify.user.api.PrivateSpotifyUser;
import de.wittenbude.exportify.user.api.PublicSpotifyUser;
import de.wittenbude.exportify.user.api.SpotifyUserService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
class SpotifyUserServiceImpl implements SpotifyUserService {

    private final SpotifyUserClient spotifyUserClient;
    private final PublicSpotifyUserRepository publicSpotifyUserRepository;
    private final PrivateUserRepository privateUserRepository;

    public SpotifyUserServiceImpl(SpotifyUserClient spotifyUserClient,
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
        PublicSpotifyUser publicUser = publicSpotifyUserRepository.upsert(user.convertPublic());
        PrivateSpotifyUser privateUser = privateUserRepository.upsert(user.convertPrivate());

        return Pair.of(privateUser, publicUser);
    }
}
