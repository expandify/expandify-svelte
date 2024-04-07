package dev.kenowi.exportify.domain.services.exportifyuser;


import dev.kenowi.exportify.domain.entities.ExportifyUser;
import dev.kenowi.exportify.domain.entities.SpotifyCredentials;
import dev.kenowi.exportify.domain.events.ExportifyUserCreated;
import dev.kenowi.exportify.domain.exceptions.NoCredentialsException;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyAuthenticationClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import dev.kenowi.exportify.infrastructure.spotify.mappers.SpotifyCredentialsMapper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class CredentialsService {

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
    private void linkCredentials(ExportifyUserCreated event) {

        SpotifyCredentials credentials = spotifyCredentialsMapper.toEntity(event.getTokenResponse());
        ExportifyUser user = event.getExportifyUser();
        credentialsRepository.upsert(credentials.setExportifyUser(user));
    }


    public SpotifyCredentials refreshCredentials(SpotifyCredentials credentials) {
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient.refresh(credentials.getRefreshToken());

        SpotifyCredentials newCredentials = spotifyCredentialsMapper.toEntity(tokenResponse);
        ExportifyUser user = credentials.getExportifyUser();
        return credentialsRepository.upsert(newCredentials.setExportifyUser(user));
    }

    public SpotifyCredentials mustFindByUserID(UUID userID) {
        return credentialsRepository
                .findByUserID(userID)
                .orElseThrow(() -> new NoCredentialsException("Current User does not have any Credentials"));
    }
}
