package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.Cache;
import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.repositories.ArtistRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class ArtistService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyArtistService.class);
    private final ArtistRepository artistRepository;
    private final SpotifyApiRequestService spotifyApiRequest;
    private final CacheService cacheService;

    public ArtistService(
            ArtistRepository artistRepository,
            SpotifyApiRequestService spotifyApiRequest,
            CacheService cacheService
    ) {
        this.artistRepository = artistRepository;
        this.spotifyApiRequest = spotifyApiRequest;
        this.cacheService = cacheService;
    }

    public List<Artist> getOrLoadLatest() throws SpotifyWebApiException {
        List<Artist> artists = cacheService.get().getFollowedArtists();

        if (artists != null && !artists.isEmpty()) {
            return artists;
        }

        artists = spotifyApiRequest
                .cursorStreamRequest(api -> api.getUsersFollowedArtists(ModelObjectType.ARTIST))
                .map(Artist::new)
                .map(artistRepository::save)
                .toList();

        return cacheService.setArtists(artists);
    }
}
