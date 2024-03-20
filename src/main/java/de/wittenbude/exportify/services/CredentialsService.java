package de.wittenbude.exportify.services;

import de.wittenbude.exportify.db.entity.CredentialsEntity;
import de.wittenbude.exportify.db.repositories.CredentialsRepository;
import de.wittenbude.exportify.exceptions.NoCredentialsException;
import de.wittenbude.exportify.models.Credentials;
import de.wittenbude.exportify.properties.AuthenticationProperties;
import de.wittenbude.exportify.request.CurrentUserID;
import de.wittenbude.exportify.spotify.clients.SpotifyAuthenticationClient;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

    private final SpotifyAuthenticationClient spotifyAuthenticationClient;
    private final AuthenticationProperties authenticationProperties;
    private final CredentialsRepository credentialsRepository;
    private final CurrentUserID currentUserID;

    public CredentialsService(SpotifyAuthenticationClient spotifyAuthenticationClient,
                              AuthenticationProperties authenticationProperties,
                              CredentialsRepository credentialsRepository,
                              CurrentUserID currentUserID) {
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
        this.authenticationProperties = authenticationProperties;
        this.credentialsRepository = credentialsRepository;
        this.currentUserID = currentUserID;
    }

    public Credentials exchange(String spotifyCode) {
        return spotifyAuthenticationClient
                .token(spotifyCode, authenticationProperties.getRedirectUri(), "authorization_code")
                .convert();
    }

    public Credentials getCurrentUserCredentials() {
        return credentialsRepository
                .findByUserEntity_Id(currentUserID.get())
                .map(CredentialsEntity::convert)
                .orElseThrow(() -> new NoCredentialsException("Current User does not have any Credentials"));
    }

    public Credentials refresh(Credentials currentCredentials) {
        Credentials newCredentials = spotifyAuthenticationClient
                .refresh(currentCredentials.getRefreshToken(), "refresh_token")
                .convert();

        newCredentials.setId(currentCredentials.getId());
        newCredentials.setSpotifyPrivateUser(currentCredentials.getSpotifyPrivateUser());
        return credentialsRepository.save(CredentialsEntity.from(currentCredentials)).convert();
    }

    public void upsert(Credentials currentCredentials) {
        credentialsRepository
                .findByUserEntity_Id(currentCredentials.getSpotifyPrivateUser().getId())
                .ifPresent(credentialsEntity -> currentCredentials.setId(credentialsEntity.getId()));

        credentialsRepository.save(CredentialsEntity.from(currentCredentials)).convert();
    }


}
