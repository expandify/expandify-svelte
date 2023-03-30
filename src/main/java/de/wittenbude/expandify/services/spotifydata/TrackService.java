package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Track;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.repositories.TrackRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.Arrays;
import java.util.List;

@Service
public class TrackService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyTrackService.class);
    private final TrackRepository trackRepository;
    private final SpotifyApiRequestService spotifyApiRequest;

    public TrackService(
            TrackRepository trackRepository,
            SpotifyApiRequestService spotifyApiRequest
    ) {
        this.trackRepository = trackRepository;
        this.spotifyApiRequest = spotifyApiRequest;
    }

    public List<SavedTrack> loadSavedTracks(int offset) throws SpotifyWebApiException {
        return Arrays
                .stream(spotifyApiRequest
                        .makeRequest(api -> api.getUsersSavedTracks().offset(offset))
                        .getItems())
                .map(track -> new SavedTrack(save(track.getTrack()), track.getAddedAt()))
                .toList();
    }

    private Track save(se.michaelthelin.spotify.model_objects.specification.Track track) {
        return trackRepository
                .findById(track.getId())
                .orElse(trackRepository.save(new Track(track)));
    }

}
