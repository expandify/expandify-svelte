package de.wittenbude.exportify.credentials.api;

import de.wittenbude.exportify.spotify.data.SpotifyTokenResponse;
import de.wittenbude.exportify.user.api.ExportifyUser;

public interface SpotifyCredentialsService {

    SpotifyCredentials link(SpotifyTokenResponse tokenResponse, ExportifyUser user);

    SpotifyCredentials refreshLink(SpotifyTokenResponse newToken, SpotifyCredentials oldCredentials);

}
