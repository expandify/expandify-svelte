package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.Cache;
import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.repositories.CacheRepository;
import de.wittenbude.expandify.requestscope.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CacheService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyLibraryService.class);
    private final CacheRepository cacheRepository;
    private final CurrentUser currentUser;


    public CacheService(
            CacheRepository cacheRepository,
            CurrentUser currentUser
    ) {
        this.cacheRepository = cacheRepository;
        this.currentUser = currentUser;
    }

    public Cache get() {
        return cacheRepository
                .findById(currentUser.getSpotifyUserId())
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
        cache.setId(currentUser.getSpotifyUserId());
        return cache;
    }
}
