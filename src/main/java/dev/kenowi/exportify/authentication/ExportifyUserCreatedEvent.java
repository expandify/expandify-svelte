package dev.kenowi.exportify.authentication;

import dev.kenowi.exportify.authentication.entities.ExportifyUser;
import dev.kenowi.exportify.spotify.dao.SpotifyTokenResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Slf4j
public class ExportifyUserCreatedEvent extends ApplicationEvent {

    private final SpotifyTokenResponse tokenResponse;
    private final ExportifyUser exportifyUser;

    public ExportifyUserCreatedEvent(Object source, ExportifyUser exportifyUser, SpotifyTokenResponse tokenResponse) {
        super(source);
        this.exportifyUser = exportifyUser;
        this.tokenResponse = tokenResponse;
        log.info("Exportify User created {}", exportifyUser.getId());
    }
}
