package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Album;
import de.wittenbude.expandify.models.spotifydata.TrackSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class AlbumService {

    private static final Logger LOG = LoggerFactory.getLogger(AlbumService.class);
    private final SpotifyApiRequestService spotifyApiRequest;
    private final PersistenceService persistenceService;

    public AlbumService(
            SpotifyApiRequestService spotifyApiRequest,
            PersistenceService persistenceService
    ) {
        this.spotifyApiRequest = spotifyApiRequest;
        this.persistenceService = persistenceService;
    }

    public List<SavedAlbum> getSaved() throws SpotifyWebApiException {
        return spotifyApiRequest
                .pagingRequest(spotifyApi -> spotifyApi.getCurrentUsersSavedAlbums().limit(50))
                .stream()
                .map(a -> new SavedAlbum(a, null))
                .map(savedAlbum -> persistenceService.find(savedAlbum)
                        .orElse(persistenceService.save(savedAlbum)))
                .toList();
    }

    @SneakyThrows
    public Album get(String id) {
        Album album = persistenceService
                .findAlbum(id)
                .orElse(new Album(spotifyApiRequest.makeRequest(api -> api.getAlbum(id)), null));

        if (album.getTracks() == null || album.getTracks().isEmpty()) {
            album.setTracks(getTracks(album.getId()));
            return persistenceService.save(album);
        }
        return album;
    }

    @SneakyThrows
    public List<TrackSimplified> getTracks(String id) {
        return spotifyApiRequest
                .pagingRequest(api -> api.getAlbumsTracks(id).limit(50))
                .stream()
                .map(TrackSimplified::new)
                .toList();
    }

}


