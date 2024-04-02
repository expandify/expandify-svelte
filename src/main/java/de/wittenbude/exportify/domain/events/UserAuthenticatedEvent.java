package de.wittenbude.exportify.domain.events;

import de.wittenbude.exportify.domain.entities.ExportifyUser;
import de.wittenbude.exportify.domain.entities.SpotifyCredentials;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAuthenticatedEvent {

    private SpotifyTokenResponse tokenResponse;
    private ExportifyUser exportifyUser;
    private SpotifyCredentials oldCredentials;
}
