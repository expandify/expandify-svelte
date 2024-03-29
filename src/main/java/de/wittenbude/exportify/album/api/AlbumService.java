package de.wittenbude.exportify.album.api;

import java.util.stream.Stream;

public interface AlbumService {

    Stream<SavedAlbum> loadSavedAlbums();

}
