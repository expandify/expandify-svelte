package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class ArtistService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyArtistService.class);
    private final SpotifyApiRequestService spotifyApiRequest;
    private final PersistenceService persistenceService;

    public ArtistService(
            SpotifyApiRequestService spotifyApiRequest,
            PersistenceService persistenceService
    ) {
        this.persistenceService = persistenceService;
        this.spotifyApiRequest = spotifyApiRequest;
    }

    public List<Artist> getFollowed() throws SpotifyWebApiException {
        return spotifyApiRequest
                .cursorRequest(api -> api.getUsersFollowedArtists(ModelObjectType.ARTIST).limit(50))
                .stream()
                .map(Artist::new)
                .map(artist -> persistenceService.find(artist)
                        .orElse(persistenceService.save(artist)))
                .toList();
    }

    public Artist get(String id) throws SpotifyWebApiException {
        return persistenceService
                .findArtist(id)
                .orElse(new Artist(spotifyApiRequest.makeRequest(api -> api.getArtist(id))));
    }
}
