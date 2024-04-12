package dev.kenowi.exportify.snapshot.services;

import dev.kenowi.exportify.snapshot.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.snapshot.mappers.SpotifyUserMapper;
import dev.kenowi.exportify.snapshot.repositories.PrivateSpotifyUserRepository;
import dev.kenowi.exportify.snapshot.repositories.PublicSpotifyUserRepository;
import dev.kenowi.exportify.spotify.clients.SpotifyUserClient;
import dev.kenowi.exportify.spotify.dao.SpotifyPrivateUser;
import dev.kenowi.exportify.spotify.dao.SpotifyPublicUser;
import org.springframework.stereotype.Service;


@Service
public class SpotifyUserService {

    private final SpotifyUserClient spotifyUserClient;
    private final SpotifyUserMapper spotifyUserMapper;
    private final PrivateSpotifyUserRepository privateSpotifyUserRepository;
    private final PublicSpotifyUserRepository publicSpotifyUserRepository;

    SpotifyUserService(SpotifyUserClient spotifyUserClient,
                       SpotifyUserMapper spotifyUserMapper,
                       PrivateSpotifyUserRepository privateSpotifyUserRepository,
                       PublicSpotifyUserRepository publicSpotifyUserRepository) {
        this.spotifyUserClient = spotifyUserClient;
        this.spotifyUserMapper = spotifyUserMapper;
        this.privateSpotifyUserRepository = privateSpotifyUserRepository;
        this.publicSpotifyUserRepository = publicSpotifyUserRepository;
    }


    public PrivateSpotifyUser loadCurrentSpotifyUser() {

        SpotifyPrivateUser spotifyPrivateUser = spotifyUserClient.getCurrentUser();

        publicSpotifyUserRepository.upsert(spotifyUserMapper.toEntity((SpotifyPublicUser) spotifyPrivateUser));

        return privateSpotifyUserRepository.upsert(spotifyUserMapper.toEntity(spotifyPrivateUser));
    }

}
