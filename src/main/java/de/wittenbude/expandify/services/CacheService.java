package de.wittenbude.expandify.services;

import de.wittenbude.expandify.models.Cache;
import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.repositories.CacheRepository;
import de.wittenbude.expandify.requestscope.AuthenticatedUserData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyLibraryService.class);
    private final CacheRepository cacheRepository;
    private final AuthenticatedUserData authenticatedUserData;


    public CacheService(
            CacheRepository cacheRepository,
            AuthenticatedUserData authenticatedUserData
    ) {
        this.cacheRepository = cacheRepository;
        this.authenticatedUserData = authenticatedUserData;
    }

    public Cache get() {
        return cacheRepository
                .findById(authenticatedUserData.getSpotifyUserId())
                .orElse(createNew());
    }

    public List<SavedAlbum> setAlbums(List<SavedAlbum> albums) {
        Cache cache = this.get();
        cache.setSavedAlbums(albums);
        return cacheRepository.save(cache).getSavedAlbums();
    }

    public List<SavedTrack> setTracks(List<SavedTrack> tracks) {
        Cache cache = this.get();
        cache.setSavedTracks(tracks);
        return cacheRepository.save(cache).getSavedTracks();
    }

    public List<Artist> setArtists(List<Artist> artists) {
        Cache cache = this.get();
        cache.setFollowedArtists(artists);
        return cacheRepository.save(cache).getFollowedArtists();
    }

    public List<PlaylistSimplified> setPlaylists(List<PlaylistSimplified> playlists) {
        Cache cache = this.get();
        cache.setPlaylists(playlists);
        return cacheRepository.save(cache).getPlaylists().stream().toList();
    }

    private Cache createNew() {
        Cache cache = new Cache();
        cache.setId(authenticatedUserData.getSpotifyUserId());
        return cache;
    }
}
