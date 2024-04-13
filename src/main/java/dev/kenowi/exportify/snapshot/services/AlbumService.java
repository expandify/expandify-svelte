package dev.kenowi.exportify.snapshot.services;

import dev.kenowi.exportify.shared.exceptions.EntityNotFoundException;
import dev.kenowi.exportify.snapshot.entities.Album;
import dev.kenowi.exportify.snapshot.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.snapshot.mappers.SpotifyAlbumMapper;
import dev.kenowi.exportify.snapshot.repositories.AlbumRepository;
import dev.kenowi.exportify.spotify.clients.SpotifyAlbumClient;
import dev.kenowi.exportify.spotify.dao.SpotifyIdProjection;
import dev.kenowi.exportify.spotify.dao.SpotifyPage;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@Slf4j
public class AlbumService {

    private final SpotifyAlbumClient spotifyAlbumClient;
    private final AlbumRepository albumRepository;
    private final SpotifyAlbumMapper spotifyAlbumMapper;

    AlbumService(@RestClient SpotifyAlbumClient spotifyAlbumClient,
                 AlbumRepository albumRepository,
                 SpotifyAlbumMapper spotifyAlbumMapper) {
        this.spotifyAlbumClient = spotifyAlbumClient;
        this.albumRepository = albumRepository;
        this.spotifyAlbumMapper = spotifyAlbumMapper;
    }


    public Stream<SavedAlbum> loadSavedAlbums() {

        return SpotifyPage
                .streamPagination(offset -> spotifyAlbumClient.getSaved(50, offset))
                .map(spotifyAlbumMapper::toEntity)
                .map(this::setAlbumTrackIDs)
                .map(savedAlbum -> savedAlbum.setAlbum(albumRepository.upsert(savedAlbum.getAlbum())));
    }

    public List<Album> loadAlbumIDs(List<String> albumIDs) {
        // TODO get multiple Albums at once

        return albumIDs.stream()
                .distinct()
                .map(spotifyAlbumClient::get)
                .map(spotifyAlbumMapper::toEntity)
                .map(this::setAlbumTrackIDs)
                .map(albumRepository::upsert)
                .toList();
    }

    public SavedAlbum get(UUID snapshot, String spotifyAlbumID) {
        return albumRepository
                .getFromSnapshot(snapshot, spotifyAlbumID)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyAlbumID)));
    }

    public Stream<SavedAlbum> get(UUID snapshot) {
        return albumRepository
                .getFromSnapshot(snapshot)
                .stream();
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
