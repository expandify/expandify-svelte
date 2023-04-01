package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.Cache;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.PlaylistTrack;
import de.wittenbude.expandify.repositories.PlaylistSimplifiedRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    private static final Logger LOG = LoggerFactory.getLogger(PlaylistService.class);
    private final PlaylistSimplifiedRepository playlistRepository;
    private final SpotifyApiRequestService spotifyApiRequest;
    private final CacheService cacheService;

    public PlaylistService(
            PlaylistSimplifiedRepository playlistRepository,
            SpotifyApiRequestService spotifyApiRequest,
            CacheService cacheService
    ) {
        this.playlistRepository = playlistRepository;
        this.spotifyApiRequest = spotifyApiRequest;
        this.cacheService = cacheService;
    }


    public List<PlaylistSimplified> getOrLoadLatest() throws SpotifyWebApiException {
        List<PlaylistSimplified> playlists = cacheService.get().getPlaylists();

        if (playlists != null && !playlists.isEmpty()) {
            LOG.debug("Cache found. Returning cached albums.");
            return playlists;
        }

        LOG.debug("No Cache found. Loading data from spotify.");
        playlists = spotifyApiRequest
                .pagingStreamRequest(SpotifyApi::getListOfCurrentUsersPlaylists)
                .map(PlaylistSimplified::new)
                .map(this::loadTracks)
                .map(playlistRepository::save)
                .peek(playlistSimplified -> LOG.debug("Playlist '{}' loaded with {} tracks.", playlistSimplified.getName(), playlistSimplified.getTracks().size()))
                .toList();

        return cacheService.setPlaylists(playlists);
    }

    @SneakyThrows
    private PlaylistSimplified loadTracks(PlaylistSimplified playlist) {
        Optional<PlaylistSimplified> p = playlistRepository.findById(playlist.getId());

        if (p.isPresent()) {
            return p.get();
        }

        List<PlaylistTrack> tracks = spotifyApiRequest
                .pagingRequest(api -> api.getPlaylistsItems(playlist.getSpotifyId()).limit(100))
                .stream()
                .map(PlaylistTrack::new)
                .toList();

        playlist.setTracks(tracks);
        return playlist;
    }

}
