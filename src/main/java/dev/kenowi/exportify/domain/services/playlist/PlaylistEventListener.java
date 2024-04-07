package dev.kenowi.exportify.domain.services.playlist;

import dev.kenowi.exportify.domain.entities.Playlist;
import dev.kenowi.exportify.domain.entities.valueobjects.PlaylistTrack;
import dev.kenowi.exportify.domain.entities.valueobjects.SpotifyObjectType;
import dev.kenowi.exportify.domain.events.EpisodeIDsLoaded;
import dev.kenowi.exportify.domain.events.SnapshotCreatedEvent;
import dev.kenowi.exportify.domain.events.TrackIDsLoaded;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyPlaylistClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyIdProjection;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPage;
import dev.kenowi.exportify.infrastructure.spotify.mappers.SpotifyPlaylistMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
class PlaylistEventListener {

    private final SpotifyPlaylistClient spotifyPlaylistClient;
    private final PlaylistRepository playlistRepository;
    private final SpotifyPlaylistMapper spotifyPlaylistMapper;
    private final ApplicationEventPublisher eventPublisher;

    PlaylistEventListener(SpotifyPlaylistClient spotifyPlaylistClient,
                          PlaylistRepository playlistRepository,
                          SpotifyPlaylistMapper spotifyPlaylistMapper,
                          ApplicationEventPublisher eventPublisher) {
        this.spotifyPlaylistClient = spotifyPlaylistClient;
        this.playlistRepository = playlistRepository;
        this.spotifyPlaylistMapper = spotifyPlaylistMapper;
        this.eventPublisher = eventPublisher;
    }

    @Async
    @EventListener
    public void loadUserPlaylists(SnapshotCreatedEvent event) {
        Set<Playlist> playlists = SpotifyPage
                .streamPagination(offset -> spotifyPlaylistClient.getUserPlaylistIDs(50, offset))
                .map(SpotifyIdProjection::getId)
                .map(spotifyPlaylistClient::get)
                .map(spotifyPlaylistMapper::toEntity)
                .map(this::loadPlaylistTracks)
                .map(playlistRepository::upsert)
                .collect(Collectors.toSet());

        List<PlaylistTrack> playlistTracks = playlists
                .stream()
                .map(Playlist::getTracks)
                .flatMap(List::stream)
                .toList();

        List<String> trackIDs = playlistTracks
                .stream()
                .filter(t -> SpotifyObjectType.TRACK.equals(t.getSpotifyObjectType()))
                .map(PlaylistTrack::getTrackSpotifyID)
                .toList();
        List<String> episodeIDs = playlistTracks
                .stream()
                .filter(t -> SpotifyObjectType.EPISODE.equals(t.getSpotifyObjectType()))
                .map(PlaylistTrack::getTrackSpotifyID)
                .toList();

        eventPublisher.publishEvent(new TrackIDsLoaded(this, trackIDs));
        eventPublisher.publishEvent(new EpisodeIDsLoaded(this, episodeIDs));

        eventPublisher.publishEvent(event.playlistsCreated(this, playlists));
    }


    private Playlist loadPlaylistTracks(Playlist playlist) {
        List<PlaylistTrack> tracks = SpotifyPage
                .streamPagination(offset -> spotifyPlaylistClient.getPlaylistItemIDs(playlist.getSpotifyID(), 50, offset))
                .map(spotifyPlaylistMapper::map)
                .toList();
        return playlist.setTracks(tracks);
    }

}
