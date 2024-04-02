package de.wittenbude.exportify.domain.context.track;

import de.wittenbude.exportify.domain.entities.SavedTrack;
import de.wittenbude.exportify.domain.events.AlbumLoadedEvent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public interface TrackService {

    Set<SavedTrack> loadSavedTracks();

    SavedTrack get(UUID snapshot, String spotifyTrackID);

    Stream<SavedTrack> get(UUID snapshot);

    // TODO this needs to be public because of the event (not cool)
    void loadAlbumTracks(AlbumLoadedEvent event);
}
