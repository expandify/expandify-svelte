package de.wittenbude.exportify.track.api;

import java.util.UUID;
import java.util.stream.Stream;

public interface SnapshotTrackService {
    SavedTrack get(UUID snapshot, String spotifyTrackID);

    Stream<SavedTrack> get(UUID snapshot);

}
