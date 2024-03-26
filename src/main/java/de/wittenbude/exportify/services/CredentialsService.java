package de.wittenbude.exportify.services;

import de.wittenbude.exportify.exceptions.NoCredentialsException;
import de.wittenbude.exportify.models.Credentials;
import de.wittenbude.exportify.models.PrivateUser;
import de.wittenbude.exportify.repositories.CredentialsRepository;
import de.wittenbude.exportify.request.CurrentUserID;
import de.wittenbude.exportify.spotify.clients.SpotifyAuthenticationClient;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

    private final SpotifyAuthenticationClient spotifyAuthenticationClient;
    private final CredentialsRepository credentialsRepository;
    private final CurrentUserID currentUserID;
    private final UserService userService;

    public CredentialsService(SpotifyAuthenticationClient spotifyAuthenticationClient,
                              CredentialsRepository credentialsRepository,
                              CurrentUserID currentUserID,
                              UserService userService) {
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
        this.credentialsRepository = credentialsRepository;
        this.currentUserID = currentUserID;
        this.userService = userService;
    }

    public Credentials getCurrentUserCredentials() {
        return credentialsRepository
                .findByUser_Id(currentUserID.get())
                .orElseThrow(() -> new NoCredentialsException("Current User does not have any Credentials"));
    }

    public Credentials load(String spotifyCode, String redirectUri) {

        Credentials credentials = spotifyAuthenticationClient
                .token(spotifyCode, redirectUri, "authorization_code")
                .convert();
        PrivateUser privateUser = userService.getOrLoad(credentials.getAccessToken());

        credentials.setUser(privateUser);
        return this.upsert(credentials);
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
