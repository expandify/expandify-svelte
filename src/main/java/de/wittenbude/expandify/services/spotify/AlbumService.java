package de.wittenbude.expandify.services.spotify;

import de.wittenbude.expandify.models.db.Album;
import de.wittenbude.expandify.models.db.TrackInfo;
import de.wittenbude.expandify.models.pojos.SavedAlbum;
import de.wittenbude.expandify.repositories.albums.AlbumDao;
import de.wittenbude.expandify.repositories.albums.SavedAlbumDao;
import de.wittenbude.expandify.services.api.SpotifyApiRequestService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class AlbumService {

    private static final Logger LOG = LoggerFactory.getLogger(AlbumService.class);
    private final SpotifyApiRequestService spotifyApiRequest;
    private final AlbumDao albumDao;
    private final SavedAlbumDao savedAlbumDao;

    public AlbumService(
            SpotifyApiRequestService spotifyApiRequest,
            AlbumDao albumDao,
            SavedAlbumDao savedAlbumDao) {
        this.spotifyApiRequest = spotifyApiRequest;
        this.albumDao = albumDao;
        this.savedAlbumDao = savedAlbumDao;
    }

    @Cacheable( value = "SavedAlbums", key = "#_userIdForCache")
    public List<SavedAlbum> getSaved(String _userIdForCache) throws SpotifyWebApiException {
        LOG.debug("No cached albums found. Loading from Spotify.");
        return spotifyApiRequest
                .pagingRequest(spotifyApi -> spotifyApi.getCurrentUsersSavedAlbums().limit(50))
                .stream()
                .map(a -> new SavedAlbum(a, null))
                .map(savedAlbum -> savedAlbumDao.find(savedAlbum)
                        .orElse(savedAlbumDao.save(savedAlbum)))
                .toList();
    }

    @SneakyThrows
    public Album get(String id) {
        Album album = albumDao
                .find(id)
                .orElse(new Album(spotifyApiRequest.makeRequest(api -> api.getAlbum(id)), null));

        if (album.getTracks() == null || album.getTracks().isEmpty()) {
            album.setTracks(getTracks(album.getId()));
            return albumDao.save(album);
        }
        return album;
    }

    @SneakyThrows
    public List<TrackInfo> getTracks(String id) {
        return spotifyApiRequest
                .pagingRequest(api -> api.getAlbumsTracks(id).limit(50))
                .stream()
                .map(TrackInfo::new)
                .toList();
    }

}


