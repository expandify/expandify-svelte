package de.wittenbude.exportify.services;

import de.wittenbude.exportify.exceptions.NoCredentialsException;
import de.wittenbude.exportify.models.Credentials;
import de.wittenbude.exportify.properties.AuthenticationProperties;
import de.wittenbude.exportify.repositories.CredentialsRepository;
import de.wittenbude.exportify.request.CurrentUserID;
import de.wittenbude.exportify.spotify.clients.SpotifyAuthenticationClient;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

    private final SpotifyAuthenticationClient spotifyAuthenticationClient;
    private final AuthenticationProperties authenticationProperties;
    private final CredentialsRepository credentialsRepository;
    private final CurrentUserID currentUserID;
    private final UserService userService;

    public CredentialsService(SpotifyAuthenticationClient spotifyAuthenticationClient,
                              AuthenticationProperties authenticationProperties,
                              CredentialsRepository credentialsRepository,
                              CurrentUserID currentUserID,
                              UserService userService) {
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
        this.authenticationProperties = authenticationProperties;
        this.credentialsRepository = credentialsRepository;
        this.currentUserID = currentUserID;
        this.userService = userService;
    }

    public Credentials exchange(String spotifyCode) {
        return spotifyAuthenticationClient
                .token(spotifyCode, authenticationProperties.getRedirectUri(), "authorization_code")
                .convert();
    }

    public Credentials getCurrentUserCredentials() {
        return credentialsRepository
                .findByUser_Id(currentUserID.get())
                .orElseThrow(() -> new NoCredentialsException("Current User does not have any Credentials"));
    }

    public Credentials refresh(Credentials currentCredentials) {
        Credentials newCredentials = spotifyAuthenticationClient
                .refresh(currentCredentials.getRefreshToken(), "refresh_token")
                .convert();

        newCredentials.setId(currentCredentials.getId());
        newCredentials.setUser(currentCredentials.getUser());
        return this.upsert(currentCredentials);
    }

    public Credentials upsert(Credentials currentCredentials) {
        currentCredentials.setUser(userService.upsertPrivate(currentCredentials.getUser()));
        credentialsRepository
                .findByUser_Id(currentCredentials.getUser().getId())
                .map(Credentials::getId)
                .ifPresent(currentCredentials::setId);

        return credentialsRepository.save(currentCredentials);

    }


}
