package de.wittenbude.expandify.services.spotify;

import de.wittenbude.expandify.models.db.Track;
import de.wittenbude.expandify.models.pojos.SavedTrack;
import de.wittenbude.expandify.repositories.tracks.SavedTrackDao;
import de.wittenbude.expandify.repositories.tracks.TrackDao;
import de.wittenbude.expandify.services.api.SpotifyApiRequestService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class TrackService {

    // private static final Logger LOG = LoggerFactory.getLogger(TrackService.class);
    private final SpotifyApiRequestService spotifyApiRequest;
    private final SavedTrackDao savedTrackDao;
    private final TrackDao trackDao;

    public TrackService(
            SpotifyApiRequestService spotifyApiRequest,
            SavedTrackDao savedTrackDao, TrackDao trackDao) {
        this.spotifyApiRequest = spotifyApiRequest;
        this.savedTrackDao = savedTrackDao;
        this.trackDao = trackDao;
    }



    @Cacheable( value = "SavedTrack", key = "#_userIdForCache")
    public List<SavedTrack> getLatest(String _userIdForCache) throws SpotifyWebApiException {
        return spotifyApiRequest
                .pagingRequest(spotifyApi -> spotifyApi.getUsersSavedTracks().limit(50))
                .stream()
                .map(SavedTrack::new)
                .map(savedTrackDao::save)
                .toList();
    }


    public Track get(String id) throws SpotifyWebApiException {
        return trackDao
                .find(id)
                .orElse(new Track(spotifyApiRequest.makeRequest(api -> api.getTrack(id))));
    }
}
