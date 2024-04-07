package dev.kenowi.exportify.domain.services.spotifyuser;

import dev.kenowi.exportify.domain.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyUserClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPrivateUser;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPublicUser;
import dev.kenowi.exportify.infrastructure.spotify.mappers.SpotifyUserMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
public class SpotifyUserEventListener {

    private final SpotifyUserClient spotifyUserClient;
    private final SpotifyUserMapper spotifyUserMapper;
    private final PrivateSpotifyUserRepository privateSpotifyUserRepository;
    private final PublicSpotifyUserRepository publicSpotifyUserRepository;

    SpotifyUserEventListener(SpotifyUserClient spotifyUserClient,
                             SpotifyUserMapper spotifyUserMapper,
                             PrivateSpotifyUserRepository privateSpotifyUserRepository,
                             PublicSpotifyUserRepository publicSpotifyUserRepository) {
        this.spotifyUserClient = spotifyUserClient;
        this.spotifyUserMapper = spotifyUserMapper;
        this.privateSpotifyUserRepository = privateSpotifyUserRepository;
        this.publicSpotifyUserRepository = publicSpotifyUserRepository;
    }


    @Async
    public CompletableFuture<PrivateSpotifyUser> loadCurrentSpotifyUser() {

        SpotifyPrivateUser spotifyPrivateUser = spotifyUserClient.getCurrentUser();

        publicSpotifyUserRepository.upsert(spotifyUserMapper.toEntity((SpotifyPublicUser) spotifyPrivateUser));

        PrivateSpotifyUser privateSpotifyUser = privateSpotifyUserRepository
                .upsert(spotifyUserMapper.toEntity(spotifyPrivateUser));

        return CompletableFuture.completedFuture(privateSpotifyUser);
    }

}
