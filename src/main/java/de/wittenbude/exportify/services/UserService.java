package de.wittenbude.exportify.services;

import de.wittenbude.exportify.models.PrivateUser;
import de.wittenbude.exportify.models.converter.UserConverter;
import de.wittenbude.exportify.repositories.PrivateUserRepository;
import de.wittenbude.exportify.repositories.PublicUserRepository;
import de.wittenbude.exportify.spotify.clients.SpotifyUserClient;
import de.wittenbude.exportify.spotify.data.SpotifyPrivateUser;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final SpotifyUserClient spotifyUserClient;
    private final PublicUserRepository publicUserRepository;
    private final PrivateUserRepository privateUserRepository;

    public UserService(SpotifyUserClient spotifyUserClient,
                       PublicUserRepository publicUserRepository,
                       PrivateUserRepository privateUserRepository) {
        this.spotifyUserClient = spotifyUserClient;
        this.publicUserRepository = publicUserRepository;
        this.privateUserRepository = privateUserRepository;
    }

    public PrivateUser getMe() {
        PrivateUser privateUser = UserConverter.from(spotifyUserClient.getCurrentUser());
        return this.persistPrivateUser(privateUser);
    }

    public PrivateUser getOrLoad(String accessToken) {
        SpotifyPrivateUser user = spotifyUserClient.getCurrentUser("Bearer " + accessToken);
        return this.persistPrivateUser(UserConverter.from(user));
    }

    public PrivateUser persistPrivateUser(PrivateUser privateUser) {
        privateUser.setPublicUser(publicUserRepository.upsert(privateUser.getPublicUser()));
        //privateUser.setCountry(countryRepository.upsert(privateUser.getCountry()));
        return privateUserRepository.upsert(privateUser);
    }

}
