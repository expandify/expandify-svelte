package dev.kenowi.exportify.domain.service.credentials;


import dev.kenowi.exportify.domain.entities.ExportifyUser;
import dev.kenowi.exportify.domain.entities.SpotifyCredentials;
import dev.kenowi.exportify.domain.events.UserAuthenticatedEvent;
import dev.kenowi.exportify.domain.exception.NoCredentialsException;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyAuthenticationClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import dev.kenowi.exportify.infrastructure.spotify.mappers.SpotifyCredentialsMapper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CredentialsService {

    private final CredentialsRepository credentialsRepository;
    private final SpotifyCredentialsMapper spotifyCredentialsMapper;
    private final SpotifyAuthenticationClient spotifyAuthenticationClient;

    CredentialsService(CredentialsRepository credentialsRepository,
                              SpotifyCredentialsMapper spotifyCredentialsMapper,
                              SpotifyAuthenticationClient spotifyAuthenticationClient) {
        this.credentialsRepository = credentialsRepository;
        this.spotifyCredentialsMapper = spotifyCredentialsMapper;
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
    }

    @EventListener
    private CompletableFuture<SpotifyCredentials> link(UserAuthenticatedEvent event) {

        SpotifyCredentials newCredentials = spotifyCredentialsMapper.toEntity(event.getTokenResponse());
        ExportifyUser user = event.getExportifyUser();
        UUID id = Optional.ofNullable(event.getOldCredentials())
                .map(SpotifyCredentials::getId)
                .orElse(null);
        return CompletableFuture.completedFuture(credentialsRepository
                .upsert(newCredentials
                        .setExportifyUser(user)
                        .setId(id)));
    }


    public SpotifyCredentials refreshCredentials(SpotifyCredentials credentials) {
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient.refresh(credentials.getRefreshToken());

        try {
            return this.link(new UserAuthenticatedEvent(tokenResponse, credentials.getExportifyUser(), credentials)).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SpotifyCredentials mustFindByUserID(UUID userID) {
        return credentialsRepository
                .findByUserID(userID)
                .orElseThrow(() -> new NoCredentialsException("Current User does not have any Credentials"));
    }
}
