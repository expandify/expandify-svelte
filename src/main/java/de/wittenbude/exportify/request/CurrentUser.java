package de.wittenbude.exportify.request;

import de.wittenbude.exportify.exceptions.EntityNotFoundException;
import de.wittenbude.exportify.models.ExportifyUser;
import de.wittenbude.exportify.repositories.ExportifyUserRepository;
import de.wittenbude.exportify.services.ApiTokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@RequestScope
@Component
public class CurrentUser {
    private final UUID id;
    private final ExportifyUserRepository exportifyUserRepository;

    public CurrentUser(ApiTokenService apiTokenService,
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
