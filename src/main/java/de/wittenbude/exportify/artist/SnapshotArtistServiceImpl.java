package de.wittenbude.exportify.artist;

import de.wittenbude.exportify.artist.api.Artist;
import de.wittenbude.exportify.artist.api.SnapshotArtistService;
import de.wittenbude.exportify.exceptions.EntityNotFoundException;
import de.wittenbude.exportify.snapshot.api.SnapshotService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Service
class SnapshotArtistServiceImpl implements SnapshotArtistService {
    private final ArtistRepository artistRepository;
    private final SnapshotService snapshotService;

    public SnapshotArtistServiceImpl(ArtistRepository artistRepository,
                                     SnapshotService snapshotService) {
        this.artistRepository = artistRepository;
        this.snapshotService = snapshotService;
    }


    public Artist get(UUID snapshot, String spotifyArtistID) {
        snapshotService.mustExist(snapshot);

        return artistRepository
                .getFromSnapshot(snapshot, spotifyArtistID)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyArtistID)));
    }

    public Stream<Artist> get(UUID snapshot) {
        snapshotService.mustExist(snapshot);

        return artistRepository
                .getFromSnapshot(snapshot)
                .stream();
    }
}
