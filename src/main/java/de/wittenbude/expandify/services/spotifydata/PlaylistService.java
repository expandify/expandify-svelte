package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.PlaylistTrack;
import de.wittenbude.expandify.repositories.PlaylistSimplifiedRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.Arrays;
import java.util.List;

@Service
public class PlaylistService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyPlaylistService.class);
    private final PlaylistSimplifiedRepository playlistRepository;
    private final SpotifyApiRequestService spotifyApiRequest;

    public PlaylistService(
            PlaylistSimplifiedRepository playlistRepository,
            SpotifyApiRequestService spotifyApiRequest
    ) {
        this.playlistRepository = playlistRepository;
        this.spotifyApiRequest = spotifyApiRequest;
    }

    public List<PlaylistSimplified> loadPlaylists(int offset) throws SpotifyWebApiException {
        return Arrays.stream(spotifyApiRequest
                        .makeRequest(api -> api.getListOfCurrentUsersPlaylists().offset(offset))
                        .getItems())
                .map(this::saveWithTracks)
                .toList();
    }


    private PlaylistSimplified saveWithTracks(se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified playlist) {
        return playlistRepository
                .findById(new PlaylistSimplified.CompositeId(playlist.getId(), playlist.getSnapshotId()))
                .orElse(playlistRepository.save(loadPlaylistWithTracks(playlist)));
    }

    @SneakyThrows
    private PlaylistSimplified loadPlaylistWithTracks(se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified playlist) {
        List<PlaylistTrack> tracks = spotifyApiRequest
                .pagingRequest(api -> api.getPlaylistsItems(playlist.getId()))
                .stream()
                .map(PlaylistTrack::new)
                .toList();

        return new PlaylistSimplified(playlist, tracks);
    }
}
