package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.repositories.AlbumRepository;
import de.wittenbude.expandify.services.spotifyapi.AuthorizedSpotifyApiRequestService;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequest;
import org.springframework.stereotype.Service;

@Service
public class SpotifyAlbumService {

    private final AlbumRepository albumRepository;
    private final SpotifyApiRequest spotifyApiRequest;

    public SpotifyAlbumService(AlbumRepository albumRepository, AuthorizedSpotifyApiRequestService spotifyApiRequest) {
        this.albumRepository = albumRepository;
        this.spotifyApiRequest = spotifyApiRequest;
    }
}
