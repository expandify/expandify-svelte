package dev.kenowi.exportify.domain.service.spotifyuser;

import dev.kenowi.exportify.domain.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.domain.entities.PublicSpotifyUser;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyUserClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPrivateUser;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPublicUser;
import dev.kenowi.exportify.infrastructure.spotify.mappers.SpotifyUserMapper;
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


    public String loadSpotifyUserID(String accessToken) {

        SpotifyPrivateUser spotifyPrivateUser = spotifyUserClient.getCurrentUser("Bearer " + accessToken);
        PrivateSpotifyUser privateSpotifyUser = spotifyUserMapper.toEntity(spotifyPrivateUser);
        PublicSpotifyUser publicSpotifyUser = spotifyUserMapper.toEntity((SpotifyPublicUser) spotifyPrivateUser);

        publicSpotifyUserRepository.upsert(publicSpotifyUser);
        privateSpotifyUserRepository.upsert(privateSpotifyUser);

        return privateSpotifyUser.getSpotifyID();
    }

}
