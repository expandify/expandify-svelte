package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Album;
import de.wittenbude.expandify.models.spotifydata.TrackSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class AlbumService {

    private final SpotifyApiRequestService spotifyApiRequest;
    private final PersistenceService persistenceService;

    public AlbumService(
            SpotifyApiRequestService spotifyApiRequest,
            PersistenceService persistenceService
    ) {
        this.spotifyApiRequest = spotifyApiRequest;
        this.persistenceService = persistenceService;
    }

    public List<SavedAlbum> getLatest() throws SpotifyWebApiException {
        return spotifyApiRequest
                .pagingStreamRequest(spotifyApi -> spotifyApi.getCurrentUsersSavedAlbums().limit(50))
                .map(a -> new SavedAlbum(a, null))
                .map(savedAlbum -> persistenceService.find(savedAlbum)
                        .orElse(saveNewAlbum(savedAlbum)))
                .toList();
    }


    private SavedAlbum saveNewAlbum(SavedAlbum album) {
        return new SavedAlbum(saveNewAlbum(album.getAlbum()), album.getAddedAt());
    }

    private Album saveNewAlbum(Album album) {
        album.setTracks(getAlbumTracks(album));
        return persistenceService.save(album);
    }

    @SneakyThrows
    private List<TrackSimplified> getAlbumTracks(Album album) {
        return spotifyApiRequest
                .pagingStreamRequest(api -> api.getAlbumsTracks(album.getId()).limit(50))
                .map(TrackSimplified::new)
                .toList();
    }

}


