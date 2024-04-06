package dev.kenowi.exportify.domain.service.album;

import dev.kenowi.exportify.domain.exception.EntityNotFoundException;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class SnapshotAlbumService {

    private final AlbumRepository albumRepository;

    SnapshotAlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
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


}
