package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyAlbum;
import de.wittenbude.expandify.models.spotifydata.SpotifyTrackSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifySavedAlbum;
import de.wittenbude.expandify.repositories.AlbumRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.SavedAlbum;

import java.util.Arrays;
import java.util.List;

@Service
public class SpotifyAlbumService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyAlbumService.class);
    private final AlbumRepository albumRepository;
    private final SpotifyApiRequestService spotifyApiRequest;
    private final SpotifyLibraryService libraryService;

    public SpotifyAlbumService(
            AlbumRepository albumRepository,
            SpotifyApiRequestService spotifyApiRequest,
            SpotifyLibraryService libraryService
    ) {
        this.albumRepository = albumRepository;
        this.spotifyApiRequest = spotifyApiRequest;
        this.libraryService = libraryService;
    }

    public List<SpotifySavedAlbum> loadSavedAlbums(int offset) throws SpotifyWebApiException {
        List<SpotifySavedAlbum> albums = Arrays
                .stream(spotifyApiRequest
                .makeRequest(api -> api.getCurrentUsersSavedAlbums().offset(offset))
                .getItems())
                .map(album -> new SpotifySavedAlbum(saveWithTracks(album.getAlbum()), album.getAddedAt()))
                .toList();

        return libraryService.saveAlbumsToLatest(albums);
    }


    @SneakyThrows
    public List<SpotifyTrackSimplified> loadAlbumTracks(String albumId) {
        return spotifyApiRequest
                .pagingRequest(api -> api.getAlbumsTracks(albumId))
                .stream()
                .map(SpotifyTrackSimplified::new)
                .toList();
    }

    public SpotifyAlbum saveWithTracks(Album album) {
        SpotifyAlbum spotifyAlbum = albumRepository
                .findById(album.getId())
                .orElseGet(() -> new SpotifyAlbum(album, loadAlbumTracks(album.getId())));

        return albumRepository.save(spotifyAlbum);
    }


}


