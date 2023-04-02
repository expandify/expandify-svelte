package de.wittenbude.expandify.services.auth;

import de.wittenbude.expandify.models.SpotifyApiCredential;
import de.wittenbude.expandify.repositories.SpotifyApiCredentialRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.enums.AuthorizationScope;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.net.URI;
import java.util.List;

@Service
public class AuthenticationService {

    // private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);

    private final SpotifyApiCredentialRepository spotifyApiCredentialRepository;
    private final SpotifyApiRequestService spotifyApiRequest;

    public AuthenticationService(
            SpotifyApiCredentialRepository spotifyApiCredentialRepository,
            SpotifyApiRequestService spotifyApiRequest
    ) {
        this.spotifyApiCredentialRepository = spotifyApiCredentialRepository;
        this.spotifyApiRequest = spotifyApiRequest;
    }


    public URI spotifyAuthenticationUri(String redirectUrl) throws SpotifyWebApiException {
        List<AuthorizationScope> scopes = List.of(
                // Playlists
                AuthorizationScope.PLAYLIST_READ_PRIVATE,
                AuthorizationScope.PLAYLIST_READ_COLLABORATIVE,
                // User
                AuthorizationScope.USER_LIBRARY_READ,
                AuthorizationScope.USER_FOLLOW_READ,
                AuthorizationScope.USER_READ_PRIVATE,
                AuthorizationScope.USER_READ_EMAIL,
                AuthorizationScope.USER_READ_RECENTLY_PLAYED,
                AuthorizationScope.USER_TOP_READ,
                // Playback Control
                AuthorizationScope.APP_REMOTE_CONTROL,
                AuthorizationScope.STREAMING,
                AuthorizationScope.USER_READ_PLAYBACK_STATE,
                AuthorizationScope.USER_MODIFY_PLAYBACK_STATE,
                AuthorizationScope.USER_READ_CURRENTLY_PLAYING,
                AuthorizationScope.USER_READ_PLAYBACK_POSITION

        );

        return spotifyApiRequest
                .makeRequest(api -> api
                        .authorizationCodeUri()
                        .scope(scopes.toArray(AuthorizationScope[]::new))
                        .state(redirectUrl)
                        .show_dialog(true)
                );
    }

    public String authenticateUser(String code) throws  SpotifyWebApiException {
        AuthorizationCodeCredentials credentials = spotifyApiRequest.makeRequest(api -> api.authorizationCode(code));

        User user = spotifyApiRequest.makeRequest(api -> {
            api.setAccessToken(credentials.getAccessToken());
            api.setRefreshToken(credentials.getRefreshToken());
            return api.getCurrentUsersProfile();
        });
        spotifyApiCredentialRepository.save(new SpotifyApiCredential(credentials, user.getId()));
        return user.getId();
    }


}
