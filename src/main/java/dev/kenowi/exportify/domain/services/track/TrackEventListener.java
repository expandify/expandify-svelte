package dev.kenowi.exportify.domain.services.track;

import dev.kenowi.exportify.domain.entities.Album;
import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.entities.valueobjects.EventStatus;
import dev.kenowi.exportify.domain.entities.valueobjects.PlaylistTrack;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.entities.valueobjects.SpotifyObjectType;
import dev.kenowi.exportify.domain.events.*;
import dev.kenowi.exportify.domain.utils.StreamHelper;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyTrackClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPage;
import dev.kenowi.exportify.infrastructure.spotify.mappers.SpotifyTrackMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TrackEventListener {

    private final TrackRepository trackRepository;
    private final SpotifyTrackClient spotifyTrackClient;
    private final ApplicationEventPublisher eventBus;
    private final SpotifyTrackMapper spotifyTrackMapper;
    private final ApplicationEventPublisher eventPublisher;

    TrackEventListener(TrackRepository trackRepository,
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
        SpotifyPage
                .streamPagination(offset -> spotifyTrackClient.getSaved(50, offset))
                .map(spotifyTrackMapper::toEntity)
                .map(savedTrack -> savedTrack.setTrack(trackRepository.upsert(savedTrack.getTrack())))
                .collect(StreamHelper.chunkedSet(100))
                .map(savedTracksChunk -> new SnapshotTracksCreatedEvent(this, savedTracksChunk, event.getSnapshot()))
                .forEach(eventPublisher::publishEvent);

        // The saved Tracks are chunked, so that the tracks artists and albums can be processed asynchronously.
    }


    @Async
    @EventListener(condition = "!#event.tracksLoaded")
    public void loadAlbumTracks(AlbumsCreatedEvent event) {
        List<Track> tracks = event
                .getAlbums()
                .stream()
                .map(Album::getSpotifyTrackIDs)
                .flatMap(List::stream)
                .distinct()
                .collect(StreamHelper.chunked(50))
                .map(ids -> String.join(",", ids))
                .map(spotifyTrackClient::getTracks)
                .map(response -> response.get("tracks"))
                .flatMap(List::stream)
                .map(spotifyTrackMapper::toEntity)
                .map(track -> track.setAlbumStatus(EventStatus.FINISHED))
                .map(trackRepository::upsert)
                .toList();


        eventPublisher.publishEvent(TracksCreatedEvent.withAlbums(this, tracks, event.getAlbums()));
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

        eventBus.publishEvent(TracksCreatedEvent.empty(this, tracks));
    }

    @Async
    @EventListener
    public void setTrackAlbum(TracksArtistsCreatedEvent event) {
        event.getTracks()
                .stream()
                .map(track -> track.setArtistsStatus(EventStatus.FINISHED))
                .forEach(trackRepository::save);
    }
}
