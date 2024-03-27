package de.wittenbude.exportify.services;

import de.wittenbude.exportify.exceptions.NoCredentialsException;
import de.wittenbude.exportify.models.Credentials;
import de.wittenbude.exportify.models.PrivateUser;
import de.wittenbude.exportify.models.converter.CredentialsConverter;
import de.wittenbude.exportify.repositories.CredentialsRepository;
import de.wittenbude.exportify.request.CurrentUserID;
import de.wittenbude.exportify.spotify.clients.SpotifyAuthenticationClient;
import de.wittenbude.exportify.spotify.data.SpotifyTokenResponse;
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

        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient
                .token(spotifyCode, redirectUri, "authorization_code");
        Credentials credentials = CredentialsConverter.from(tokenResponse);

        PrivateUser privateUser = userService.getOrLoad(credentials.getAccessToken());
        credentials.setUser(privateUser);
        return this.persist(credentials);
    }

    public Credentials refresh(Credentials currentCredentials) {
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient
                .refresh(currentCredentials.getRefreshToken(), "refresh_token");
        Credentials newCredentials = CredentialsConverter.from(tokenResponse);

        newCredentials.setId(currentCredentials.getId());
        newCredentials.setUser(currentCredentials.getUser());
        return this.persist(currentCredentials);
    }

    public Credentials persist(Credentials currentCredentials) {
        currentCredentials.setUser(userService.persistPrivateUser(currentCredentials.getUser()));
        return credentialsRepository.upsert(currentCredentials);
    }


}
