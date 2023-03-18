package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.repositories.PlaylistRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SpotifyPlaylistService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyPlaylistService.class);
    private final PlaylistRepository playlistRepository;
    private final SpotifyApiRequestService spotifyApiRequest;

    public SpotifyPlaylistService(PlaylistRepository playlistRepository, SpotifyApiRequestService spotifyApiRequest) {
        this.playlistRepository = playlistRepository;
        this.spotifyApiRequest = spotifyApiRequest;
    }


}
