package dev.kenowi.exportify.domain.service.track;

import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.entities.valueobjects.PlaylistTrack;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedTrack;
import dev.kenowi.exportify.domain.entities.valueobjects.SpotifyObjectType;
import dev.kenowi.exportify.domain.service.playlist.PlaylistCreatedEvent;
import dev.kenowi.exportify.domain.service.album.AlbumCreatedEvent;
import dev.kenowi.exportify.domain.service.snapshot.SnapshotCreatedEvent;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyTrackClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPage;
import dev.kenowi.exportify.infrastructure.spotify.mappers.SpotifyTrackMapper;
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
class TrackEventService {

    private final TrackRepository trackRepository;
    private final SpotifyTrackClient spotifyTrackClient;
    private final ApplicationEventPublisher eventBus;
    private final SpotifyTrackMapper spotifyTrackMapper;
    private final ApplicationEventPublisher eventPublisher;

    TrackEventService(TrackRepository trackRepository,
                      SpotifyTrackClient spotifyTrackClient,
                      ApplicationEventPublisher eventBus,
                      SpotifyTrackMapper spotifyTrackMapper,
                      ApplicationEventPublisher eventPublisher) {
        this.trackRepository = trackRepository;
        this.spotifyTrackClient = spotifyTrackClient;
        this.eventBus = eventBus;
        this.spotifyTrackMapper = spotifyTrackMapper;
        this.eventPublisher = eventPublisher;
    }

    @Async
    @EventListener
    public void loadSavedTracks(SnapshotCreatedEvent event) {
        Set<SavedTrack> savedTracks = SpotifyPage
                .streamPagination(offset -> spotifyTrackClient.getSaved(50, offset))
                .map(spotifyTrackMapper::toEntity)
                .map(savedTrack -> savedTrack.setTrack(trackRepository.upsert(savedTrack.getTrack())))
                .collect(Collectors.toSet());

        List<Track> tracks = savedTracks.stream().map(SavedTrack::getTrack).toList();
        eventPublisher.publishEvent(new TracksCreatedEvent(tracks));
        eventPublisher.publishEvent(event.dataCreated(this, savedTracks));
    }

    @Async
    @EventListener
    public void loadAlbumTracks(AlbumCreatedEvent event) {
        List<Track> tracks = event.getAlbum()
                .getSpotifyTrackIDs()
                .stream()
                .map(spotifyTrackClient::get)
                .map(spotifyTrackMapper::toEntity)
                .map(trackRepository::upsert)
                .toList();

        eventBus.publishEvent(new TracksCreatedEvent(tracks, true));
    }

    @Async
    @EventListener
    public void loadPlaylistTracks(PlaylistCreatedEvent event) {
        List<Track> tracks = event.getPlaylist()
                .getTracks()
                .stream()
                .filter(p -> SpotifyObjectType.TRACK.equals(p.getSpotifyObjectType()))
                .map(PlaylistTrack::getTrackSpotifyID)
                .map(spotifyTrackClient::get)
                .map(spotifyTrackMapper::toEntity)
                .map(trackRepository::upsert)
                .toList();

        eventBus.publishEvent(new TracksCreatedEvent(tracks));
    }
}
