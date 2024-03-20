package de.wittenbude.exportify.services;

import de.wittenbude.exportify.db.entity.UserEntity;
import de.wittenbude.exportify.db.repositories.UserRepository;
import de.wittenbude.exportify.models.PrivateUser;
import de.wittenbude.exportify.spotify.clients.SpotifyUserClient;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final SpotifyUserClient spotifyUserClient;
    private final UserRepository userRepository;

    public UserService(SpotifyUserClient spotifyUserClient, UserRepository userRepository) {
        this.spotifyUserClient = spotifyUserClient;
        this.userRepository = userRepository;
    }

    public PrivateUser getMe() {
        PrivateUser privateUser = spotifyUserClient.getCurrentUser().convert();
        return upsert(privateUser);
    }

    public PrivateUser getOrLoad(String accessToken) {
        PrivateUser privateUser = spotifyUserClient.getCurrentUser("Bearer " + accessToken).convert();
        return upsert(privateUser);
    }

    private PrivateUser upsert(PrivateUser privateUser) {

        return userRepository.findBySpotifyID(privateUser.getSpotifyID())
                .orElseGet(() -> userRepository.save(UserEntity.from(privateUser)))
                .convert();
    }
}
