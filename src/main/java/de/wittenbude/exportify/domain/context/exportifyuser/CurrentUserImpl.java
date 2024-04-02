package de.wittenbude.exportify.domain.context.exportifyuser;

import de.wittenbude.exportify.domain.context.auth.AuthenticationService;
import de.wittenbude.exportify.domain.entities.ExportifyUser;
import de.wittenbude.exportify.domain.exception.EntityNotFoundException;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@Component
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
class CurrentUserImpl implements CurrentUser {

    private final UUID id;
    private final ExportifyUserRepository exportifyUserRepository;

    public CurrentUserImpl(ExportifyUserRepository exportifyUserRepository) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken token)) {
            throw new ProviderNotFoundException("Current Authenticated User is not of type JwtAuthenticationToken");
        }
        this.id = UUID.fromString(token.getToken().getClaim(AuthenticationService.USER_ID_CLAIM));
        this.exportifyUserRepository = exportifyUserRepository;
    }

    @Override
    public UUID getID() {
        return this.id;
    }

    @Override
    public ExportifyUser getUser() {
        return exportifyUserRepository
                .findById(this.id)
                .orElseThrow(() -> new EntityNotFoundException("current user not found"));
    }
}
