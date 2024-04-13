package dev.kenowi.exportify.authentication;

import dev.kenowi.exportify.authentication.repositories.ExportifyUserRepository;
import dev.kenowi.exportify.authentication.services.CredentialsService;
import io.smallrye.common.vertx.ContextLocals;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import static dev.kenowi.exportify.authentication.AuthenticatedUser.USER_ID_CLAIM;
import static dev.kenowi.exportify.authentication.AuthenticatedUser.USER_CONTEXT_KEY;

@Provider
public class AuthContextProvider implements ContainerRequestFilter {

    private final ExportifyUserRepository exportifyUserRepository;
    private final CredentialsService credentialsService;

    public AuthContextProvider(ExportifyUserRepository exportifyUserRepository, CredentialsService credentialsService) {
        this.exportifyUserRepository = exportifyUserRepository;
        this.credentialsService = credentialsService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Principal principal = requestContext.getSecurityContext().getUserPrincipal();
        if (!(principal instanceof JsonWebToken jwt)) {
            return;
        }
        Optional<String> userIdString = jwt.claim(USER_ID_CLAIM);
        if (userIdString.isEmpty()) {
            return;
        }
        UUID userID = UUID.fromString(userIdString.get());
        AuthenticatedUser user = new AuthenticatedUser(exportifyUserRepository, credentialsService, userID);
        ContextLocals.put(USER_CONTEXT_KEY, user);
    }
}
