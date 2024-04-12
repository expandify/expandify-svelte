package dev.kenowi.exportify.authentication.services;


import dev.kenowi.exportify.authentication.ExportifyUserCreatedEvent;
import dev.kenowi.exportify.authentication.SpotifyCredentialsMapper;
import dev.kenowi.exportify.authentication.entities.ExportifyUser;
import dev.kenowi.exportify.authentication.entities.SpotifyCredentials;
import dev.kenowi.exportify.authentication.repositories.CredentialsRepository;
import dev.kenowi.exportify.shared.exceptions.NoCredentialsException;
import dev.kenowi.exportify.spotify.clients.SpotifyAuthenticationClient;
import dev.kenowi.exportify.spotify.dao.SpotifyTokenResponse;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    private void linkCredentials(ExportifyUserCreatedEvent event) {

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
