package de.wittenbude.exportify.domain.context.album;

import de.wittenbude.exportify.domain.entities.SavedAlbum;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public interface AlbumService {

    Set<SavedAlbum> loadSavedAlbums();

    SavedAlbum get(UUID snapshot, String spotifyAlbumID);

    Stream<SavedAlbum> get(UUID snapshot);
}
