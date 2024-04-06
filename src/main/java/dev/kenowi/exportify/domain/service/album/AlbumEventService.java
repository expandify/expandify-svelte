package dev.kenowi.exportify.domain.service.album;

import dev.kenowi.exportify.domain.entities.Album;
import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.service.snapshot.SnapshotCreatedEvent;
import dev.kenowi.exportify.domain.service.track.TracksCreatedEvent;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyAlbumClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyIdProjection;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPage;
import dev.kenowi.exportify.infrastructure.spotify.mappers.SpotifyAlbumMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
class AlbumEventService {

    private final SpotifyAlbumClient spotifyAlbumClient;
    private final AlbumRepository albumRepository;
    private final SpotifyAlbumMapper spotifyAlbumMapper;
    private final ApplicationEventPublisher eventPublisher;

    AlbumEventService(SpotifyAlbumClient spotifyAlbumClient,
                      AlbumRepository albumRepository,
                      SpotifyAlbumMapper spotifyAlbumMapper,
                      ApplicationEventPublisher eventPublisher) {
        this.spotifyAlbumClient = spotifyAlbumClient;
        this.albumRepository = albumRepository;
        this.spotifyAlbumMapper = spotifyAlbumMapper;
        this.eventPublisher = eventPublisher;
    }


    @Async
    @EventListener
    public void loadSavedAlbums(SnapshotCreatedEvent event) {
        Set<SavedAlbum> savedAlbums = SpotifyPage
                .streamPagination(offset -> spotifyAlbumClient.getSaved(50, offset))
                .map(spotifyAlbumMapper::toEntity)
                .map(this::setAlbumTrackIDs)
                .map(savedAlbum -> savedAlbum.setAlbum(albumRepository.upsert(savedAlbum.getAlbum())))
                .collect(Collectors.toSet());

        savedAlbums.stream()
                .map(SavedAlbum::getAlbum)
                .map(AlbumCreatedEvent::new)
                .forEach(eventPublisher::publishEvent);

        eventPublisher.publishEvent(event.dataCreated(this, savedAlbums));
    }

    @Async
    @EventListener
    public void loadAlbum(TracksCreatedEvent event) {
        // TODO get multiple Albums at once
        event.getTracks()
                .stream()
                .filter(Predicate.not(TracksCreatedEvent.TrackData::albumLoaded))
                .map(TracksCreatedEvent.TrackData::track)
                .map(Track::getSpotifyAlbumID)
                .distinct()
                .map(spotifyAlbumClient::get)
                .map(spotifyAlbumMapper::toEntity)
                .map(this::setAlbumTrackIDs)
                .map(albumRepository::upsert)
                .map(AlbumCreatedEvent::new)
                .forEach(eventPublisher::publishEvent);
    }

    private Album setAlbumTrackIDs(Album album) {
        List<String> trackIDs = SpotifyPage
                .streamPagination(offset -> spotifyAlbumClient.getAlbumTrackIDs(album.getSpotifyID(), 50, offset))
                .map(SpotifyIdProjection::getId)
                .toList();
        return album.setSpotifyTrackIDs(trackIDs);
    }

    private SavedAlbum setAlbumTrackIDs(SavedAlbum savedAlbum) {
        return savedAlbum.setAlbum(this.setAlbumTrackIDs(savedAlbum.getAlbum()));
    }

}
