package de.wittenbude.exportify.request;

import de.wittenbude.exportify.exceptions.NoCredentialsException;
import de.wittenbude.exportify.models.SpotifyCredentials;
import de.wittenbude.exportify.repositories.CredentialsRepository;
import de.wittenbude.exportify.services.AuthenticationService;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;

@RequestScope
@Component
@Getter
public class CurrentValidCredentials {

    private final AuthenticationService authenticationService;

    private SpotifyCredentials credentials;

    public CurrentValidCredentials(AuthenticationService authenticationService,
                                   CredentialsRepository credentialsRepository,
                                   CurrentUser currentUser) {
        this.authenticationService = authenticationService;
        this.credentials = credentialsRepository
                .findByUserID(currentUser.getID())
                .orElseThrow(() -> new NoCredentialsException("Current User does not have any Credentials"));
    }

    public SpotifyCredentials get() {
        if (Instant.now().isAfter(credentials.getExpiresAt())) {
            this.credentials = authenticationService.refresh(credentials);
        }
        return this.credentials;
    }
}
