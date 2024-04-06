package dev.kenowi.exportify.domain.service.exportifyuser;

import dev.kenowi.exportify.domain.entities.ExportifyUser;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ExportifyUserCreatedEvent extends ApplicationEvent {

    private final SpotifyTokenResponse tokenResponse;
    private final ExportifyUser exportifyUser;

    private ExportifyUserCreatedEvent(Object source, ExportifyUser exportifyUser, SpotifyTokenResponse tokenResponse) {
        super(source);
        this.exportifyUser = exportifyUser;
        this.tokenResponse = tokenResponse;
    }

    ExportifyUserCreatedEvent(ExportifyUser exportifyUser, SpotifyTokenResponse tokenResponse) {
        this(AuthenticationService.class, exportifyUser, tokenResponse);
    }
}
