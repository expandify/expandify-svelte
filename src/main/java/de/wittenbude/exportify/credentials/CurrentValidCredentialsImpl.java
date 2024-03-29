package de.wittenbude.exportify.credentials;

import de.wittenbude.exportify.auth.api.AuthenticationService;
import de.wittenbude.exportify.credentials.api.CurrentValidCredentials;
import de.wittenbude.exportify.credentials.api.SpotifyCredentials;
import de.wittenbude.exportify.exceptions.NoCredentialsException;
import de.wittenbude.exportify.user.api.CurrentUser;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;

@RequestScope
@Component
@Getter
class CurrentValidCredentialsImpl implements CurrentValidCredentials {

    private final AuthenticationService authenticationService;

    private SpotifyCredentials credentials;

    public CurrentValidCredentialsImpl(AuthenticationService authenticationService,
                                       CredentialsRepository credentialsRepository,
                                       CurrentUser currentUser) {
        this.authenticationService = authenticationService;
        this.credentials = credentialsRepository
                .findByUserID(currentUser.getID())
                .orElseThrow(() -> new NoCredentialsException("Current User does not have any Credentials"));
    }

    public SpotifyCredentials get() {
        if (Instant.now().isAfter(credentials.getExpiresAt())) {
            this.credentials = authenticationService.refreshToken(credentials);
        }
        return this.credentials;
    }
}
