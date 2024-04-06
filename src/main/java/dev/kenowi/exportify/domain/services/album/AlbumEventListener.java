package dev.kenowi.exportify.domain.services.album;

import dev.kenowi.exportify.domain.entities.Album;
import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.entities.valueobjects.EventStatus;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.events.*;
import dev.kenowi.exportify.domain.utils.StreamHelper;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlbumEventListener {

    private final SpotifyAlbumClient spotifyAlbumClient;
    private final AlbumRepository albumRepository;
    private final SpotifyAlbumMapper spotifyAlbumMapper;
    private final ApplicationEventPublisher eventPublisher;

    AlbumEventListener(SpotifyAlbumClient spotifyAlbumClient,
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
    public void loadSavedAlbums(SnapshotEvent.Created event) {
        SpotifyPage
                .streamPagination(offset -> spotifyAlbumClient.getSaved(50, offset))
                .map(spotifyAlbumMapper::toEntity)
                .map(this::setAlbumTrackIDs)
                .map(savedAlbum -> savedAlbum.setAlbum(albumRepository.upsert(savedAlbum.getAlbum())))
                .map(SavedAlbum::getAlbum)
                .collect(StreamHelper.chunkedSet(50))
                .map(albumsChunk -> AlbumsCreatedEvent.empty(this, albumsChunk))
                .forEach(eventPublisher::publishEvent);

        // The saved Albums are chunked, so that the albums artists and tracks can be processed asynchronously.
    }

    @Async
    @EventListener(condition = "!#event.albumsLoaded")
    public void loadTrackAlbums(TracksCreatedEvent event) {
        // TODO get multiple Albums at once

        List<Album> albums = event
                .getTracks()
                .stream()
                .map(Track::getSpotifyAlbumID)
                .distinct()
                .map(spotifyAlbumClient::get)
                .map(spotifyAlbumMapper::toEntity)
                .map(this::setAlbumTrackIDs)
                .map(albumRepository::upsert)
                .toList();

        eventPublisher.publishEvent(AlbumsCreatedEvent.empty(this, albums));
    }

    @Async
    @EventListener(condition = "#event.albumsLoaded")
    public void setAlbumTracksStatus(TracksCreatedEvent event) {
        // TODO check if loaded Tracks match the album tracks
        event.getAlbums()
                .stream()
                .map(album -> album.setTracksStatus(EventStatus.FINISHED))
                .forEach(albumRepository::save);
    }

    @Async
    @EventListener
    public void setAlbumArtistsStatus(AlbumsArtistsCreatedEvent event) {
        // TODO check if loaded Artists match the album artists
        event.getAlbums()
                .stream()
                .map(album -> album.setArtistsStatus(EventStatus.FINISHED))
                .forEach(albumRepository::save);
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
