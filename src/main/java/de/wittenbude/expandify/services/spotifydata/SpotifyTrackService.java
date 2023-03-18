package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.repositories.TrackRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SpotifyTrackService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyTrackService.class);
    private final TrackRepository trackRepository;
    private final SpotifyApiRequestService spotifyApiRequest;

    public SpotifyTrackService(TrackRepository trackRepository, SpotifyApiRequestService spotifyApiRequest) {
        this.trackRepository = trackRepository;
        this.spotifyApiRequest = spotifyApiRequest;
    }


}
