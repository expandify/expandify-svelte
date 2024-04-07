package dev.kenowi.exportify.domain.services.album;

import dev.kenowi.exportify.domain.entities.Album;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.events.AlbumIDsLoaded;
import dev.kenowi.exportify.domain.events.ArtistIDsLoaded;
import dev.kenowi.exportify.domain.events.TrackIDsLoaded;
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
import java.util.concurrent.CompletableFuture;
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
    public CompletableFuture<Set<SavedAlbum>> loadSavedAlbums() {
        Set<SavedAlbum> savedAlbums = SpotifyPage
                .streamPagination(offset -> spotifyAlbumClient.getSaved(50, offset))
                .map(spotifyAlbumMapper::toEntity)
                .map(this::setAlbumTrackIDs)
                .map(savedAlbum -> savedAlbum.setAlbum(albumRepository.upsert(savedAlbum.getAlbum())))
                .collect(Collectors.toSet());

        eventPublisher.publishEvent(ArtistIDsLoaded.fromSavedAlbums(this, savedAlbums));
        eventPublisher.publishEvent(TrackIDsLoaded.fromSavedAlbums(this, savedAlbums));

        return CompletableFuture.completedFuture(savedAlbums);
    }

    @Async
    @EventListener
    protected void loadAlbumIDs(AlbumIDsLoaded event) {
        // TODO get multiple Albums at once

        List<Album> albums = event
                .getAlbumIDs()
                .stream()
                .distinct()
                .map(spotifyAlbumClient::get)
                .map(spotifyAlbumMapper::toEntity)
                .map(this::setAlbumTrackIDs)
                .map(albumRepository::upsert)
                .toList();

        eventPublisher.publishEvent(ArtistIDsLoaded.fromAlbums(this, albums));

        // Enabling this will load all Album Tracks, even if they are not directly in the users library
        //eventPublisher.publishEvent(TrackIDsLoaded.fromAlbums(this, albums));
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
