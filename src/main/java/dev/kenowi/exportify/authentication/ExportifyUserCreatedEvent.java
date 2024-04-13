package dev.kenowi.exportify.authentication;

import dev.kenowi.exportify.authentication.entities.ExportifyUser;
import dev.kenowi.exportify.spotify.dao.SpotifyTokenResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ExportifyUserCreatedEvent {
    public static final String NAME = "login-with-spotify-event";

    private final SpotifyTokenResponse tokenResponse;
    private final ExportifyUser exportifyUser;

    public ExportifyUserCreatedEvent(ExportifyUser exportifyUser, SpotifyTokenResponse tokenResponse) {
        this.exportifyUser = exportifyUser;
        this.tokenResponse = tokenResponse;
        log.info("Exportify User created {}", exportifyUser.getId());
    }
}
