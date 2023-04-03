package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Playlist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.PlaylistTrack;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
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


    public List<PlaylistSimplified> getSaved() throws SpotifyWebApiException {
        return spotifyApiRequest
                .pagingStreamRequest(spotifyApi -> spotifyApi.getListOfCurrentUsersPlaylists().limit(50))
                .map(p -> new PlaylistSimplified(p, null))
                .map(p -> persistenceService.find(p)
                        .orElse(persistenceService.save(p)))
                .toList();
    }

    public Playlist get(String id) throws SpotifyWebApiException {
        Playlist playlist = persistenceService
                .findPlaylist(id)
                .orElse(new Playlist(spotifyApiRequest.makeRequest(api -> api.getPlaylist(id)), null));

        if (playlist.getTracks() == null || playlist.getTracks().isEmpty()) {
            playlist.setTracks(getPlaylistTracks(playlist));
            return persistenceService.save(playlist);
        }
        return playlist;
    }

    private List<PlaylistTrack> getPlaylistTracks(Playlist playlist) throws SpotifyWebApiException {
        return spotifyApiRequest
                .pagingStreamRequest(api -> api.getPlaylistsItems(playlist.getId()).limit(100))
                .map(PlaylistTrack::new)
                .toList();
    }
}
