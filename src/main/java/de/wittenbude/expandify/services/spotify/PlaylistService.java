package de.wittenbude.expandify.services.spotify;

import de.wittenbude.expandify.models.db.Playlist;
import de.wittenbude.expandify.models.db.PlaylistInfo;
import de.wittenbude.expandify.models.pojos.PlaylistTrack;
import de.wittenbude.expandify.repositories.playlists.PlaylistDao;
import de.wittenbude.expandify.repositories.playlists.PlaylistInfoDao;
import de.wittenbude.expandify.services.api.SpotifyApiRequestService;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class PlaylistService {

    // private static final Logger LOG = LoggerFactory.getLogger(PlaylistService.class);
    private final SpotifyApiRequestService spotifyApiRequest;
    private final PlaylistDao playlistDao;
    private final PlaylistInfoDao playlistInfoDao;

    public PlaylistService(
            SpotifyApiRequestService spotifyApiRequest,
            PlaylistDao playlistDao,
            PlaylistInfoDao playlistInfoDao) {
        this.playlistDao = playlistDao;
        this.spotifyApiRequest = spotifyApiRequest;
        this.playlistInfoDao = playlistInfoDao;
    }


    @Cacheable( value = "UserPlaylists", key = "#_userIdForCache")
    public List<PlaylistInfo> getSaved(String _userIdForCache) throws SpotifyWebApiException {
        return spotifyApiRequest
                .pagingRequest(spotifyApi -> spotifyApi.getListOfCurrentUsersPlaylists().limit(50))
                .stream()
                .map(PlaylistInfo::new)
                .map(playlistInfoDao::save)
                .toList();
    }

    @SneakyThrows
    public Playlist get(String id) {
        Playlist playlist = playlistDao
                .find(id)
                .orElse(new Playlist(spotifyApiRequest.makeRequest(api -> api.getPlaylist(id)), null));

        if (playlist.getTracks() == null || playlist.getTracks().isEmpty()) {
            playlist.setTracks(getTracks(playlist.getId()));
            return playlistDao.save(playlist);
        }
        return playlist;
    }

    @SneakyThrows
    public List<PlaylistTrack> getTracks(String id) {
        return spotifyApiRequest
                .pagingRequest(api -> api.getPlaylistsItems(id).limit(100))
                .stream()
                .map(PlaylistTrack::new)
                .toList();
    }
}
