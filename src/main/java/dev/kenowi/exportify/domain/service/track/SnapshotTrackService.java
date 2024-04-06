package dev.kenowi.exportify.domain.service.track;

import dev.kenowi.exportify.domain.entities.valueobjects.SavedTrack;
import dev.kenowi.exportify.domain.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class SnapshotTrackService {

    private final TrackRepository trackRepository;

    SnapshotTrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public SavedTrack get(UUID snapshot, String spotifyTrackID) {
        return trackRepository
                .getFromSnapshot(snapshot, spotifyTrackID)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyTrackID)));
    }

    public Stream<SavedTrack> get(UUID snapshot) {
        return trackRepository
                .getFromSnapshot(snapshot)
                .stream();
    }
}
