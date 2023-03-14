package de.wittenbude.expandify.services.spotifyapi;

import de.wittenbude.expandify.component.RequestAuthorization;
import de.wittenbude.expandify.models.SpotifyApiCredential;
import de.wittenbude.expandify.services.auth.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;

import java.time.Instant;
import java.util.Date;

@Service
@RequestScope // This needs to be request scoped, since the constructor requires the request Authorization (Otherwise an exception is thrown during instantiation)
public class AuthorizedSpotifyApiRequestService extends SpotifyApiRequest {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizedSpotifyApiRequestService.class);

    private final RequestAuthorization requestAuthorization;
    private final AuthenticationService authenticationService;

    public AuthorizedSpotifyApiRequestService(
            RequestAuthorization requestAuthorization,
            AuthenticationService authenticationService
    ) throws UnauthorizedException {
        super(requestAuthorization.spotifyApi());
        this.requestAuthorization = requestAuthorization;
        this.authenticationService = authenticationService;
    }

    @Override
    public void preMakeRequest() throws SpotifyWebApiException {
        SpotifyApiCredential spotifyApiCredential = requestAuthorization.spotifyApiCredential();
        Date expiresAt = spotifyApiCredential.getExpiresAt();
        Date now = Date.from(Instant.now());

        if (expiresAt.before(now)) {
            authenticationService.renewAuthentication(spotifyApiCredential.getId(), spotifyApiCredential.getRefreshToken());
            this.spotifyApi = requestAuthorization.spotifyApi();
        }
    }
}
