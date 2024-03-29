package de.wittenbude.exportify.user;

import de.wittenbude.exportify.auth.api.ApiTokenService;
import de.wittenbude.exportify.exceptions.EntityNotFoundException;
import de.wittenbude.exportify.user.api.CurrentUser;
import de.wittenbude.exportify.user.api.ExportifyUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@RequestScope
@Component
class CurrentUserImpl implements CurrentUser {
    private final UUID id;
    private final ExportifyUserRepository exportifyUserRepository;

    public CurrentUserImpl(ApiTokenService apiTokenService,
                           ExportifyUserRepository exportifyUserRepository) {
        this.id = apiTokenService.getAuthenticatedUserId();
        this.exportifyUserRepository = exportifyUserRepository;
    }

    public UUID getID() {
        return this.id;
    }

    public ExportifyUser getUser() {
        return exportifyUserRepository
                .findById(this.id)
                .orElseThrow(() -> new EntityNotFoundException("current user not found"));
    }
}
