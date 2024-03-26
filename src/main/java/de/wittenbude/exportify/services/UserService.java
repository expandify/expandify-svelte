package de.wittenbude.exportify.services;

import de.wittenbude.exportify.models.PrivateUser;
import de.wittenbude.exportify.models.PublicUser;
import de.wittenbude.exportify.repositories.PrivateUserRepository;
import de.wittenbude.exportify.repositories.PublicUserRepository;
import de.wittenbude.exportify.spotify.clients.SpotifyUserClient;
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
        PrivateUser privateUser = spotifyUserClient.getCurrentUser().convertPrivate();
        return this.upsertPrivate(privateUser);
    }

    public PrivateUser getOrLoad(String accessToken) {
        PrivateUser privateUser = spotifyUserClient.getCurrentUser("Bearer " + accessToken).convertPrivate();
        return this.upsertPrivate(privateUser);
    }

    public PrivateUser upsertPrivate(PrivateUser privateUser) {
        String spotifyID = privateUser.getPublicUser().getSpotifyID();

        privateUser.setPublicUser(this.upsertPublic(privateUser.getPublicUser()));
        privateUserRepository
                .findByPublicUser_SpotifyID(spotifyID)
                .map(PrivateUser::getId)
                .ifPresent(privateUser::setId);

        return privateUserRepository.save(privateUser);
    }

    public PublicUser upsertPublic(PublicUser publicUser) {
        publicUserRepository
                .findBySpotifyID(publicUser.getSpotifyID())
                .map(PublicUser::getId)
                .ifPresent(publicUser::setId);

        return publicUserRepository.save(publicUser);
    }
}