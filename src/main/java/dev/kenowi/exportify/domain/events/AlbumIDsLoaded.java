package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedTrack;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;
import java.util.List;

@Getter
@Slf4j
public class AlbumIDsLoaded extends ApplicationEvent {
    private final Collection<String> albumIDs;

    private AlbumIDsLoaded(Object source, Collection<String> albumIDs) {
        super(source);
        this.albumIDs = albumIDs;
    }

    public static AlbumIDsLoaded fromTracks(Object source, Collection<Track> tracks) {
        List<String> trackIDs = tracks
                .stream()
                .map(Track::getSpotifyAlbumID)
                .toList();
        return new AlbumIDsLoaded(source, trackIDs);
    }

    public static AlbumIDsLoaded fromSavedTracks(Object source, Collection<SavedTrack> savedTracks) {
        List<String> trackIDs = savedTracks
                .stream()
                .map(SavedTrack::getTrack)
                .map(Track::getSpotifyAlbumID)
                .toList();
        return new AlbumIDsLoaded(source, trackIDs);
    }
}
