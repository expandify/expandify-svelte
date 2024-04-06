package dev.kenowi.exportify.domain.service.exportifyuser;

import dev.kenowi.exportify.domain.entities.ExportifyUser;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Slf4j
public class ExportifyUserCreatedEvent extends ApplicationEvent {

    private final SpotifyTokenResponse tokenResponse;
    private final ExportifyUser exportifyUser;

    ExportifyUserCreatedEvent(ExportifyUser exportifyUser, SpotifyTokenResponse tokenResponse) {
        super(AuthenticationService.class);
        this.exportifyUser = exportifyUser;
        this.tokenResponse = tokenResponse;
        log.info("Exportify User created {}", exportifyUser.getId());
    }
}
