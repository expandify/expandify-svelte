package de.wittenbude.exportify.domain.context.auth;

import de.wittenbude.exportify.domain.context.credentials.CredentialsRepository;
import de.wittenbude.exportify.domain.context.credentials.CredentialsService;
import de.wittenbude.exportify.domain.context.exportifyuser.ExportifyUserRepository;
import de.wittenbude.exportify.domain.entities.ExportifyUser;
import de.wittenbude.exportify.domain.entities.SpotifyCredentials;
import de.wittenbude.exportify.domain.exception.EntityNotFoundException;
import de.wittenbude.exportify.domain.exception.NoCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.UUID;


public class AuthenticatedUser extends JwtAuthenticationToken {

    public static final String USER_ID_CLAIM = OAuth2TokenIntrospectionClaimNames.SUB;

    private final CredentialsRepository credentialsRepository;
    private final CredentialsService credentialsService;
    private final ExportifyUserRepository exportifyUserRepository;

    private final UUID userID;
    private ExportifyUser user;
    private SpotifyCredentials spotifyCredentials;


    public static AuthenticatedUser getSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AuthenticatedUser authenticatedUser) {
            return authenticatedUser;
        }
        throw new ProviderNotFoundException("Current Authenticated User is not of type JwtAuthenticationToken");
    }

    public AuthenticatedUser(Jwt jwt,
                             CredentialsRepository credentialsRepository,
                             CredentialsService credentialsService,
                             ExportifyUserRepository exportifyUserRepository) {
        super(jwt);
        this.credentialsRepository = credentialsRepository;
        this.credentialsService = credentialsService;
        this.exportifyUserRepository = exportifyUserRepository;
        this.setAuthenticated(true);
        this.userID = UUID.fromString(jwt.getClaim(USER_ID_CLAIM));
        this.spotifyCredentials = null;
        this.user = null;
    }

    public UUID getID() {
        return userID;
    }

    public ExportifyUser getUser() {
        if (this.user == null) {
            this.user = exportifyUserRepository
                    .findById(this.userID)
                    .orElseThrow(() -> new EntityNotFoundException("current user not found"));
        }
        return this.user;
    }

    public SpotifyCredentials getCredentials() {
        if (spotifyCredentials == null) {
            this.spotifyCredentials = credentialsRepository
                    .findByUserID(userID)
                    .orElseThrow(() -> new NoCredentialsException("Current User does not have any Credentials"));
        }

        if (Instant.now().isAfter(spotifyCredentials.getExpiresAt())) {
            this.spotifyCredentials = credentialsService.refreshCredentials(spotifyCredentials);
        }
        return this.spotifyCredentials;
    }
}
