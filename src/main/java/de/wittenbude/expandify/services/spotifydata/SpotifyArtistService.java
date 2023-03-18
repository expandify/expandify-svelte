package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.repositories.ArtistRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SpotifyArtistService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyArtistService.class);
    private final ArtistRepository artistRepository;
    private final SpotifyApiRequestService spotifyApiRequest;

    public SpotifyArtistService(ArtistRepository artistRepository, SpotifyApiRequestService spotifyApiRequest) {
        this.artistRepository = artistRepository;
        this.spotifyApiRequest = spotifyApiRequest;
    }

}
