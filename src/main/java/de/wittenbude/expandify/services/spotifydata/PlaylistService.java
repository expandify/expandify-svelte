package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.PlaylistTrack;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class PlaylistService {

    // private static final Logger LOG = LoggerFactory.getLogger(PlaylistService.class);
    private final SpotifyApiRequestService spotifyApiRequest;
    private final PersistenceService persistenceService;

    public PlaylistService(
            SpotifyApiRequestService spotifyApiRequest,
            PersistenceService persistenceService
    ) {
        this.persistenceService = persistenceService;
        this.spotifyApiRequest = spotifyApiRequest;
    }


    public List<PlaylistSimplified> getLatest() throws SpotifyWebApiException {
        return spotifyApiRequest
                .pagingStreamRequest(spotifyApi -> spotifyApi.getListOfCurrentUsersPlaylists().limit(50))
                .map(p -> new PlaylistSimplified(p, null))
                .map(p -> persistenceService.find(p)
                        .orElse(saveNewPlaylist(p)))
                .toList();
    }

    private PlaylistSimplified saveNewPlaylist(PlaylistSimplified playlist) {
        List<PlaylistTrack> tracks = getPlaylistTracks(playlist.getId());
        playlist.setTracks(tracks);
        return persistenceService.save(playlist);
    }

    @SneakyThrows
    public List<PlaylistTrack> getPlaylistTracks(String playlistId) {
        return spotifyApiRequest
                .pagingStreamRequest(api -> api.getPlaylistsItems(playlistId).limit(100))
                .map(PlaylistTrack::new)
                .toList();
    }

}
