package de.wittenbude.exportify.domain.context.playlist;

import de.wittenbude.exportify.domain.entities.Playlist;
import de.wittenbude.exportify.domain.events.PlaylistTrackLoadedEvent;
import de.wittenbude.exportify.domain.exception.EntityNotFoundException;
import de.wittenbude.exportify.domain.valueobjects.PlaylistTrack;
import de.wittenbude.exportify.infrastructure.spotify.clients.SpotifyPlaylistClient;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyPage;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyPlaylistSimplified;
import de.wittenbude.exportify.infrastructure.spotify.mappers.SpotifyPlaylistMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class PlaylistService {

    private final SpotifyPlaylistClient spotifyPlaylistClient;
    private final PlaylistRepository playlistRepository;
    private final ApplicationEventPublisher eventBus;
    private final SpotifyPlaylistMapper spotifyPlaylistMapper;

    public PlaylistService(SpotifyPlaylistClient spotifyPlaylistClient,
                           PlaylistRepository playlistRepository,
                           ApplicationEventPublisher eventBus,
                           SpotifyPlaylistMapper spotifyPlaylistMapper) {
        this.spotifyPlaylistClient = spotifyPlaylistClient;
        this.playlistRepository = playlistRepository;
        this.eventBus = eventBus;
        this.spotifyPlaylistMapper = spotifyPlaylistMapper;
    }


    public Set<Playlist> loadUserPlaylists() {
        return SpotifyPage
                .streamPagination(offset -> spotifyPlaylistClient.getUser(50, offset))
                .map(SpotifyPlaylistSimplified::getId)
                .map(spotifyPlaylistClient::get)
                .map(spotifyPlaylistMapper::toEntity)
                .peek(playlist -> log.info("Loaded playlist '{}'", playlist.getName()))
                .peek(this::loadPlaylistTracks)
                .peek(playlist -> log.info("{} tracks loaded for playlist '{}'", playlist.getTracks().size(), playlist.getName()))
                .map(playlistRepository::upsert)
                .collect(Collectors.toSet());
    }

    public Playlist get(UUID snapshot, String spotifyPlaylistID) {
        return playlistRepository
                .getFromSnapshot(snapshot, spotifyPlaylistID)
                .orElseThrow(() -> new EntityNotFoundException("playlist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyPlaylistID)));
    }

    public Stream<Playlist> get(UUID snapshot) {
        return playlistRepository
                .getFromSnapshot(snapshot)
                .stream();
    }


    private void loadPlaylistTracks(Playlist playlist) {
        List<PlaylistTrack> tracks = SpotifyPage
                .streamPagination(offset -> spotifyPlaylistClient.getTracks(playlist.getSpotifyID(), 50, offset))
                .peek(playlistTrack -> eventBus.publishEvent(new PlaylistTrackLoadedEvent(playlistTrack.getTrack())))
                .map(spotifyPlaylistMapper::map)
                .toList();
        playlist.setTracks(tracks);
    }

}
