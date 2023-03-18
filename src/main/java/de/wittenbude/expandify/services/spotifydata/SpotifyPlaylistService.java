package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyPlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifyPlaylistTrack;
import de.wittenbude.expandify.repositories.PlaylistRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.Arrays;
import java.util.List;

@Service
public class SpotifyPlaylistService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyPlaylistService.class);
    private final PlaylistRepository playlistRepository;
    private final SpotifyApiRequestService spotifyApiRequest;
    private final SpotifyLibraryService libraryService;

    public SpotifyPlaylistService(
            PlaylistRepository playlistRepository,
            SpotifyApiRequestService spotifyApiRequest,
            SpotifyLibraryService libraryService
    ) {
        this.playlistRepository = playlistRepository;
        this.spotifyApiRequest = spotifyApiRequest;
        this.libraryService = libraryService;
    }

    public List<SpotifyPlaylistSimplified> loadPlaylists(int offset) throws SpotifyWebApiException {
        List<SpotifyPlaylistSimplified> playlists = Arrays
                .stream(spotifyApiRequest
                        .makeRequest(api -> api.getListOfCurrentUsersPlaylists().offset(offset))
                        .getItems())
                .map(this::saveWithTracks)
                .toList();

        return libraryService.savePlaylistToLatest(playlists);
    }


    private SpotifyPlaylistSimplified saveWithTracks(PlaylistSimplified playlist) {
        SpotifyPlaylistSimplified spotifyPlaylist = playlistRepository
                .findById(playlist.getId())
                .orElse(new SpotifyPlaylistSimplified(playlist, loadPlaylistTracks(playlist.getId())));

        return playlistRepository.save(spotifyPlaylist);
    }

    @SneakyThrows
    private List<SpotifyPlaylistTrack> loadPlaylistTracks(String playlistId) {
        return spotifyApiRequest
                .pagingRequest(api -> api.getPlaylistsItems(playlistId))
                .stream()
                .map(SpotifyPlaylistTrack::new)
                .toList();
    }
}
