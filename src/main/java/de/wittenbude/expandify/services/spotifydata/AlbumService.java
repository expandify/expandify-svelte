package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Album;
import de.wittenbude.expandify.models.spotifydata.TrackSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.repositories.AlbumRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.Arrays;
import java.util.List;

@Service
public class AlbumService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyAlbumService.class);
    private final AlbumRepository albumRepository;
    private final SpotifyApiRequestService spotifyApiRequest;

    public AlbumService(
            AlbumRepository albumRepository,
            SpotifyApiRequestService spotifyApiRequest
    ) {
        this.albumRepository = albumRepository;
        this.spotifyApiRequest = spotifyApiRequest;
    }

    public List<SavedAlbum> loadSavedAlbums(int offset) throws SpotifyWebApiException {
        return Arrays
                .stream(spotifyApiRequest
                        .makeRequest(api -> api.getCurrentUsersSavedAlbums().offset(offset))
                        .getItems())
                .map(album -> new SavedAlbum(saveWithTracks(album.getAlbum()), album.getAddedAt()))
                .toList();
    }

    private Album saveWithTracks(se.michaelthelin.spotify.model_objects.specification.Album album) {
        return albumRepository
                .findById(album.getId())
                .orElseGet(() -> albumRepository.save(loadAlbumWithTracks(album)));
    }

    @SneakyThrows
    private Album loadAlbumWithTracks(se.michaelthelin.spotify.model_objects.specification.Album album) {
        List<TrackSimplified> tracks = spotifyApiRequest
                .pagingRequest(api -> api.getAlbumsTracks(album.getId()).limit(50))
                .stream()
                .map(TrackSimplified::new)
                .toList();

        return new Album(album, tracks);
    }
}


