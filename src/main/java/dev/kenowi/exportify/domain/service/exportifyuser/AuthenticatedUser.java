package dev.kenowi.exportify.domain.service.exportifyuser;

import dev.kenowi.exportify.domain.entities.ExportifyUser;
import dev.kenowi.exportify.domain.entities.SpotifyCredentials;
import dev.kenowi.exportify.domain.exception.EntityNotFoundException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


public class AuthenticatedUser extends JwtAuthenticationToken {

    public static final String USER_ID_CLAIM = OAuth2TokenIntrospectionClaimNames.SUB;

    private final CredentialsService credentialsService;

    private final UUID userID;
    private ExportifyUser user;
    private SpotifyCredentials spotifyCredentials;
    private final ExportifyUserRepository exportifyUserRepository;


    AuthenticatedUser(Jwt jwt,
                      CredentialsService credentialsService,
                      ExportifyUserRepository exportifyUserRepository) {
        super(jwt);
        this.credentialsService = credentialsService;
        this.exportifyUserRepository = exportifyUserRepository;

        this.setAuthenticated(true);
        this.userID = UUID.fromString(jwt.getClaim(USER_ID_CLAIM));
        this.spotifyCredentials = null;
        this.user = null;
    }

    public static AuthenticatedUser getSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AuthenticatedUser authenticatedUser) {
            return authenticatedUser;
        }
        throw new ProviderNotFoundException("Current Authenticated User is not of type JwtAuthenticationToken");
    }

    public UUID getID() {
        return userID;
    }

    public ExportifyUser getUser() {
        if (this.user == null) {
            this.user = exportifyUserRepository
                    .findById(userID)
                    .orElseThrow(() -> new EntityNotFoundException("current user not found"));
        }
        return this.user;
    }

    public SpotifyCredentials getCredentials() {

        this.spotifyCredentials = Optional
                .ofNullable(spotifyCredentials)
                .orElseGet(() -> credentialsService.mustFindByUserID(userID));

        if (Instant.now().isAfter(spotifyCredentials.getExpiresAt())) {
            this.spotifyCredentials = credentialsService.refreshCredentials(spotifyCredentials);
        }
        return this.spotifyCredentials;
    }
}
