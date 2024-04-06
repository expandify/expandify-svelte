package dev.kenowi.exportify.domain.services.spotifyuser;

import dev.kenowi.exportify.domain.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.domain.events.SnapshotCreatedEvent;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyUserClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPrivateUser;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPublicUser;
import dev.kenowi.exportify.infrastructure.spotify.mappers.SpotifyUserMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
class SpotifyUserEventListener {

    private final SpotifyUserClient spotifyUserClient;
    private final SpotifyUserMapper spotifyUserMapper;
    private final PrivateSpotifyUserRepository privateSpotifyUserRepository;
    private final PublicSpotifyUserRepository publicSpotifyUserRepository;
    private final ApplicationEventPublisher eventPublisher;

    SpotifyUserEventListener(SpotifyUserClient spotifyUserClient,
                             SpotifyUserMapper spotifyUserMapper,
                             PrivateSpotifyUserRepository privateSpotifyUserRepository,
                             PublicSpotifyUserRepository publicSpotifyUserRepository, ApplicationEventPublisher eventPublisher) {
        this.spotifyUserClient = spotifyUserClient;
        this.spotifyUserMapper = spotifyUserMapper;
        this.privateSpotifyUserRepository = privateSpotifyUserRepository;
        this.publicSpotifyUserRepository = publicSpotifyUserRepository;
        this.eventPublisher = eventPublisher;
    }


    @Async
    @EventListener
    public void loadCurrentSpotifyUser(SnapshotCreatedEvent event) {

        SpotifyPrivateUser spotifyPrivateUser = spotifyUserClient.getCurrentUser();

        publicSpotifyUserRepository.upsert(spotifyUserMapper.toEntity((SpotifyPublicUser) spotifyPrivateUser));

        PrivateSpotifyUser privateSpotifyUser = privateSpotifyUserRepository
                .upsert(spotifyUserMapper.toEntity(spotifyPrivateUser));

        eventPublisher.publishEvent(event.userCreated(this, privateSpotifyUser));
    }

}
