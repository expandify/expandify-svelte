package de.wittenbude.exportify.track.api;

import java.util.Set;
import java.util.stream.Stream;

public interface TrackService {

    Set<SavedTrack> loadSavedTracks();

    Stream<Track> loadAlbumTracks(String albumID);
}
