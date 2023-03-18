package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyTrack;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifySavedTrack;
import de.wittenbude.expandify.repositories.TrackRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Arrays;
import java.util.List;

@Service
public class SpotifyTrackService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyTrackService.class);
    private final TrackRepository trackRepository;
    private final SpotifyApiRequestService spotifyApiRequest;
    private final SpotifyLibraryService libraryService;

    public SpotifyTrackService(
            TrackRepository trackRepository,
            SpotifyApiRequestService spotifyApiRequest,
            SpotifyLibraryService libraryService
    ) {
        this.trackRepository = trackRepository;
        this.spotifyApiRequest = spotifyApiRequest;
        this.libraryService = libraryService;
    }

    public List<SpotifySavedTrack> loadSavedTracks(int offset) throws SpotifyWebApiException {
        List<SpotifySavedTrack> tracks = Arrays
                .stream(spotifyApiRequest
                        .makeRequest(api -> api.getUsersSavedTracks().offset(offset))
                        .getItems())
                .map(track -> new SpotifySavedTrack(save(track.getTrack()), track.getAddedAt()))
                .toList();

        return libraryService.saveTracksToLatest(tracks);
    }

    private SpotifyTrack save(Track track) {
        SpotifyTrack spotifyTrack = trackRepository
                .findById(track.getId())
                .orElseGet(() -> new SpotifyTrack(track));

        return trackRepository.save(spotifyTrack);
    }

}
