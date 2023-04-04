package de.wittenbude.expandify.services.spotify;

import de.wittenbude.expandify.models.db.Artist;
import de.wittenbude.expandify.repositories.artists.ArtistDao;
import de.wittenbude.expandify.services.api.SpotifyApiRequestService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class ArtistService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyArtistService.class);
    private final SpotifyApiRequestService spotifyApiRequest;
    private final ArtistDao artistDao;

    public ArtistService(
            SpotifyApiRequestService spotifyApiRequest,
            ArtistDao artistDao
    ) {
        this.artistDao = artistDao;
        this.spotifyApiRequest = spotifyApiRequest;
    }

    @Cacheable( value = "FollowedArtists", key = "#_userIdForCache")
    public List<Artist> getFollowed(String _userIdForCache) throws SpotifyWebApiException {
        return spotifyApiRequest
                .cursorRequest(api -> api.getUsersFollowedArtists(ModelObjectType.ARTIST).limit(50))
                .stream()
                .map(Artist::new)
                .map(artist -> artistDao.find(artist)
                        .orElse(artistDao.save(artist)))
                .toList();
    }

    public Artist get(String id) throws SpotifyWebApiException {
        return artistDao
                .find(id)
                .orElse(new Artist(spotifyApiRequest.makeRequest(api -> api.getArtist(id))));
    }
}
