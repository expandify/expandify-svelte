package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Track;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class TrackService {

    // private static final Logger LOG = LoggerFactory.getLogger(TrackService.class);
    private final SpotifyApiRequestService spotifyApiRequest;
    private final PersistenceService persistenceService;

    public TrackService(
            SpotifyApiRequestService spotifyApiRequest,
            PersistenceService persistenceService
    ) {
        this.spotifyApiRequest = spotifyApiRequest;
        this.persistenceService = persistenceService;
    }


    public List<SavedTrack> getLatest() throws SpotifyWebApiException {
        return spotifyApiRequest
                .pagingStreamRequest(spotifyApi -> spotifyApi.getUsersSavedTracks().limit(50))
                .map(SavedTrack::new)
                .map(savedTrack -> persistenceService.find(savedTrack)
                        .orElse(persistenceService.save(savedTrack)))
                .toList();
    }

    public Track get(String id) throws SpotifyWebApiException {
        return persistenceService
                .findTrack(id)
                .orElse(new Track(spotifyApiRequest.makeRequest(api -> api.getTrack(id))));
    }
}
