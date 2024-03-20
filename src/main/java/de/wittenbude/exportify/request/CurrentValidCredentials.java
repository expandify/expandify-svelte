package de.wittenbude.exportify.request;

import de.wittenbude.exportify.models.Credentials;
import de.wittenbude.exportify.services.CredentialsService;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;

@RequestScope
@Component
@Getter
public class CurrentValidCredentials {

    private final CredentialsService credentialsService;

    private Credentials credentials;

    public CurrentValidCredentials(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
        this.credentials = credentialsService.getCurrentUserCredentials();
    }

    public Credentials get() {
        if (Instant.now().isAfter(credentials.getExpiresAt())) {
            this.credentials = credentialsService.refresh(credentials);
        }
        return this.credentials;
    }
}
