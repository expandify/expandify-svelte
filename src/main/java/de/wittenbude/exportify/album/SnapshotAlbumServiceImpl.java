package de.wittenbude.exportify.album;

import de.wittenbude.exportify.album.api.SavedAlbum;
import de.wittenbude.exportify.album.api.SnapshotAlbumService;
import de.wittenbude.exportify.exceptions.EntityNotFoundException;
import de.wittenbude.exportify.snapshot.api.SnapshotService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Service
class SnapshotAlbumServiceImpl implements SnapshotAlbumService {
    private final AlbumRepository albumRepository;
    private final SnapshotService snapshotService;

    public SnapshotAlbumServiceImpl(AlbumRepository albumRepository,
                                    SnapshotService snapshotService) {
        this.albumRepository = albumRepository;
        this.snapshotService = snapshotService;
    }


    @Override
    public SavedAlbum get(UUID snapshot, String spotifyAlbumIDs) {
        snapshotService.mustExist(snapshot);

        return albumRepository
                .getFromSnapshot(snapshot, spotifyAlbumIDs)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyAlbumIDs)));
    }

    @Override
    public Stream<SavedAlbum> get(UUID snapshot) {
        snapshotService.mustExist(snapshot);

        return albumRepository
                .getFromSnapshot(snapshot)
                .stream();
    }

}
