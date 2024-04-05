package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.domain.entities.ExportifyUser;
import dev.kenowi.exportify.domain.entities.SpotifyCredentials;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAuthenticatedEvent {

    private SpotifyTokenResponse tokenResponse;
    private ExportifyUser exportifyUser;
    private SpotifyCredentials oldCredentials;
}
