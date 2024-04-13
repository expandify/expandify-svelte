package dev.kenowi.exportify.authentication;

import dev.kenowi.exportify.authentication.entities.ExportifyUser;
import dev.kenowi.exportify.authentication.entities.SpotifyCredentials;
import dev.kenowi.exportify.authentication.repositories.ExportifyUserRepository;
import dev.kenowi.exportify.authentication.services.CredentialsService;
import dev.kenowi.exportify.shared.exceptions.EntityNotFoundException;
import io.smallrye.common.vertx.ContextLocals;

import java.time.Instant;
import java.util.UUID;


public class AuthenticatedUser {

    public static final String USER_ID_CLAIM = "userid";
    static final String USER_CONTEXT_KEY = "userid";

    private final CredentialsService credentialsService;
    private final ExportifyUserRepository exportifyUserRepository;


    private final UUID userID;
    private ExportifyUser exportifyUser;
    private SpotifyCredentials spotifyCredentials;

    AuthenticatedUser(ExportifyUserRepository exportifyUserRepository,
                      CredentialsService credentialsService,
                      UUID userID) {

        this.credentialsService = credentialsService;
        this.exportifyUserRepository = exportifyUserRepository;
        this.userID = userID;
    }

    public static AuthenticatedUser current() {
        return ContextLocals.<AuthenticatedUser>get(USER_CONTEXT_KEY)
                .orElseThrow(() -> new EntityNotFoundException("No authenticated user found"));
    }

    public UUID getID() {
        return userID;
    }

    public ExportifyUser getUser() {

        if (exportifyUser == null) {
            this.exportifyUser = exportifyUserRepository
                    .findByID(this.getID())
                    .orElseThrow(() -> new EntityNotFoundException("current user not found"));
        }
        return this.exportifyUser;
    }

    public SpotifyCredentials getCredentials() {

        if (spotifyCredentials == null) {
            this.spotifyCredentials = credentialsService.mustFindByUserID(getID());
        }

        if (Instant.now().isAfter(spotifyCredentials.getExpiresAt())) {
            this.spotifyCredentials = credentialsService.refreshCredentials(spotifyCredentials);
        }
        return this.spotifyCredentials;
    }
}
