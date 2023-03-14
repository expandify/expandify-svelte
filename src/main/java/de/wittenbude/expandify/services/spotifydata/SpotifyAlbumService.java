package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyAlbum;
import de.wittenbude.expandify.repositories.AlbumRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class SpotifyAlbumService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyAlbumService.class);
    private final AlbumRepository albumRepository;
    private final SpotifyApiRequestService spotifyApiRequest;

    public SpotifyAlbumService(AlbumRepository albumRepository, SpotifyApiRequestService spotifyApiRequest) {
        this.albumRepository = albumRepository;
        this.spotifyApiRequest = spotifyApiRequest;
    }

    public void doNothing() {
        LOG.debug("Nothing");
    }

    public List<SpotifyAlbum> getUserAlbums() throws SpotifyWebApiException {
        return spotifyApiRequest
                .pagingRequest(SpotifyApi::getCurrentUsersSavedAlbums)
                .stream().map(SpotifyAlbum::new)
                .toList();
    }
}
