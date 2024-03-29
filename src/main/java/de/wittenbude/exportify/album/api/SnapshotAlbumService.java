package de.wittenbude.exportify.album.api;

import java.util.UUID;
import java.util.stream.Stream;

public interface SnapshotAlbumService {
    SavedAlbum get(UUID snapshot, String spotifyAlbumIDs);

    Stream<SavedAlbum> get(UUID snapshot);

}
