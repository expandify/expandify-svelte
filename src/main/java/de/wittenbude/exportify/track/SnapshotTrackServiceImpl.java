package de.wittenbude.exportify.track;

import de.wittenbude.exportify.exceptions.EntityNotFoundException;
import de.wittenbude.exportify.snapshot.api.SnapshotService;
import de.wittenbude.exportify.track.api.SavedTrack;
import de.wittenbude.exportify.track.api.SnapshotTrackService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Service
class SnapshotTrackServiceImpl implements SnapshotTrackService {
    private final TrackRepository trackRepository;
    private final SnapshotService snapshotService;

    public SnapshotTrackServiceImpl(TrackRepository trackRepository,
                                    SnapshotService snapshotService) {
        this.trackRepository = trackRepository;
        this.snapshotService = snapshotService;
    }


    @Override
    public SavedTrack get(UUID snapshot, String spotifyAlbumIDs) {
        snapshotService.mustExist(snapshot);

        return trackRepository
                .getFromSnapshot(snapshot, spotifyAlbumIDs)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyAlbumIDs)));
    }

    @Override
    public Stream<SavedTrack> get(UUID snapshot) {
        snapshotService.mustExist(snapshot);

        return trackRepository
                .getFromSnapshot(snapshot)
                .stream();
    }

}
