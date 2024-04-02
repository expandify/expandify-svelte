package de.wittenbude.exportify.domain.context.credentials;


import de.wittenbude.exportify.domain.entities.ExportifyUser;
import de.wittenbude.exportify.domain.entities.SpotifyCredentials;
import de.wittenbude.exportify.domain.events.UserAuthenticatedEvent;
import de.wittenbude.exportify.infrastructure.spotify.clients.SpotifyAuthenticationClient;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import de.wittenbude.exportify.infrastructure.spotify.mappers.SpotifyCredentialsMapper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
class CredentialsService {

    private final CredentialsRepository credentialsRepository;
    private final SpotifyCredentialsMapper spotifyCredentialsMapper;
    private final SpotifyAuthenticationClient spotifyAuthenticationClient;

    public CredentialsService(CredentialsRepository credentialsRepository,
                              SpotifyCredentialsMapper spotifyCredentialsMapper,
                              SpotifyAuthenticationClient spotifyAuthenticationClient) {
        this.credentialsRepository = credentialsRepository;
        this.spotifyCredentialsMapper = spotifyCredentialsMapper;
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
    }

    @EventListener
    public SpotifyCredentials link(UserAuthenticatedEvent event) {

        SpotifyCredentials newCredentials = spotifyCredentialsMapper.toEntity(event.getTokenResponse());
        ExportifyUser user = event.getExportifyUser();
        UUID id = Optional.ofNullable(event.getOldCredentials())
                .map(SpotifyCredentials::getId)
                .orElse(null);
        return credentialsRepository
                .upsert(newCredentials
                        .setExportifyUser(user)
                        .setId(id));
    }


    SpotifyCredentials refreshCredentials(SpotifyCredentials credentials) {
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient.refresh(credentials.getRefreshToken());

        return this.link(new UserAuthenticatedEvent(tokenResponse, credentials.getExportifyUser(), credentials));
    }
}
