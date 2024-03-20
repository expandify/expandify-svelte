package de.wittenbude.exportify.request;

import de.wittenbude.exportify.services.AuthenticationService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@RequestScope
@Component
public class CurrentUserID {
    private final UUID id;

    public CurrentUserID(AuthenticationService authenticationService) {
        this.id = authenticationService.getCurrentAuthenticatedUserID();
    }

    public UUID get() {
        return this.id;
    }
}
