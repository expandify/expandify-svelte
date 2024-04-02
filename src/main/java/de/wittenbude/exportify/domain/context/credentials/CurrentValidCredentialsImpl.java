package de.wittenbude.exportify.domain.context.credentials;

import de.wittenbude.exportify.domain.context.exportifyuser.CurrentUser;
import de.wittenbude.exportify.domain.entities.SpotifyCredentials;
import de.wittenbude.exportify.domain.exception.NoCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;

@Component
@RequestScope
class CurrentValidCredentialsImpl implements CurrentValidCredentials {

    private final CredentialsService credentialsService;
    private SpotifyCredentials credentials;

    public CurrentValidCredentialsImpl(CredentialsRepository credentialsRepository,
                                       CredentialsService credentialsService,
                                       CurrentUser currentUser) {
        this.credentialsService = credentialsService;
        this.credentials = credentialsRepository
                .findByUserID(currentUser.getID())
                .orElseThrow(() -> new NoCredentialsException("Current User does not have any Credentials"));
    }

    public SpotifyCredentials get() {
        if (Instant.now().isAfter(credentials.getExpiresAt())) {
            this.credentials = credentialsService.refreshCredentials(credentials);
        }
        return this.credentials;
    }
}
