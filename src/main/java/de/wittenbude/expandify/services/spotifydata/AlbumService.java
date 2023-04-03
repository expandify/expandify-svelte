package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Album;
import de.wittenbude.expandify.models.spotifydata.TrackSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
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

    public List<SavedAlbum> getSaved() throws SpotifyWebApiException {
        return spotifyApiRequest
                .pagingStreamRequest(spotifyApi -> spotifyApi.getCurrentUsersSavedAlbums().limit(50))
                .map(a -> new SavedAlbum(a, null))
                .map(savedAlbum -> persistenceService.find(savedAlbum)
                        .orElse(persistenceService.save(savedAlbum)))
                .toList();
    }

    public Album get(String id) throws SpotifyWebApiException {
        Album album = persistenceService
                .findAlbum(id)
                .orElse(new Album(spotifyApiRequest.makeRequest(api -> api.getAlbum(id)), null));

        if (album.getTracks() == null || album.getTracks().isEmpty()) {
            album.setTracks(getAlbumTracks(album));
            return persistenceService.save(album);
        }
        return album;
    }


    private List<TrackSimplified> getAlbumTracks(Album album) throws SpotifyWebApiException {
        return spotifyApiRequest
                .pagingStreamRequest(api -> api.getAlbumsTracks(album.getId()).limit(50))
                .map(TrackSimplified::new)
                .toList();
    }

}


