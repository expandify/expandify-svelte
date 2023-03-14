package de.wittenbude.expandify.services.spotifyapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;

@Service
public class UnauthorizedSpotifyApiRequestService extends SpotifyApiRequest{

    private static final Logger LOG = LoggerFactory.getLogger(UnauthorizedSpotifyApiRequestService.class);

    public UnauthorizedSpotifyApiRequestService(SpotifyApi registeredSpotifyApi) {
        super(registeredSpotifyApi);
    }
}
