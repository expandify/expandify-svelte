package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Album;
import de.wittenbude.expandify.models.spotifydata.TrackSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.repositories.AlbumRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class AlbumService {

     private static final Logger LOG = LoggerFactory.getLogger(AlbumService.class);
    private final AlbumRepository albumRepository;
    private final SpotifyApiRequestService spotifyApiRequest;
    private final CacheService cacheService;

    public AlbumService(
            AlbumRepository albumRepository,
            SpotifyApiRequestService spotifyApiRequest,
            CacheService cacheService
    ) {
        this.albumRepository = albumRepository;
        this.spotifyApiRequest = spotifyApiRequest;
        this.cacheService = cacheService;
    }


    public List<SavedAlbum> getOrLoadLatest() throws SpotifyWebApiException {
        List<SavedAlbum> savedAlbums = cacheService.get().getSavedAlbums();

        if (savedAlbums != null && !savedAlbums.isEmpty()) {
            LOG.debug("Cache found. Returning cached albums.");
            return savedAlbums;
        }

        LOG.debug("No Cache found. Loading data from spotify.");
        savedAlbums = spotifyApiRequest
                .pagingRequest(SpotifyApi::getCurrentUsersSavedAlbums)
                .stream()
                .map(SavedAlbum::new)
                .map(this::loadTracks)
                .map(this::saveAlbum)
                .toList();

        return cacheService.setAlbums(savedAlbums);
    }

    @SneakyThrows
    private SavedAlbum loadTracks(SavedAlbum savedAlbum) {
        List<TrackSimplified> tracks = spotifyApiRequest
                .pagingRequest(api -> api.getAlbumsTracks(savedAlbum.getAlbum().getId()).limit(50))
                .stream()
                .map(TrackSimplified::new)
                .toList();

        Album album = savedAlbum.getAlbum();
        album.setTracksSimplified(tracks);
        return savedAlbum;
    }

    private SavedAlbum saveAlbum(SavedAlbum savedAlbum) {
        Album album = albumRepository.save(savedAlbum.getAlbum());
        return new SavedAlbum(album, savedAlbum.getAddedAt());
    }

}


