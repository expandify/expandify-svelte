package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.domain.entities.Album;
import dev.kenowi.exportify.domain.entities.Playlist;
import dev.kenowi.exportify.domain.entities.valueobjects.PlaylistTrack;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.entities.valueobjects.SpotifyObjectType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;
import java.util.List;

@Getter
@Slf4j
public class TrackIDsLoaded extends ApplicationEvent {
    private final Collection<String> trackIDs;
    private final boolean albumLoaded;

    private TrackIDsLoaded(Object source, Collection<String> trackIDs, boolean albumLoaded) {
        super(source);
        this.trackIDs = trackIDs;
        this.albumLoaded = albumLoaded;
    }

    public static TrackIDsLoaded fromAlbums(Object source, Collection<Album> albums) {
        List<String> trackIDs = albums
                .stream()
                .map(Album::getSpotifyTrackIDs)
                .flatMap(List::stream)
                .toList();
        return new TrackIDsLoaded(source, trackIDs, true);
    }

    public static TrackIDsLoaded fromSavedAlbums(Object source, Collection<SavedAlbum> savedAlbums) {
        List<String> trackIDs = savedAlbums
                .stream()
                .map(SavedAlbum::getAlbum)
                .map(Album::getSpotifyTrackIDs)
                .flatMap(List::stream)
                .toList();
        return new TrackIDsLoaded(source, trackIDs, false);
    }

    public static TrackIDsLoaded fromPlaylists(Object source, Collection<Playlist> playlists) {
        List<String> trackIDs = playlists
                .stream()
                .map(Playlist::getTracks)
                .flatMap(List::stream)
                .filter(t -> SpotifyObjectType.TRACK.equals(t.getSpotifyObjectType()))
                .map(PlaylistTrack::getSpotifyTrackID)
                .toList();
        return new TrackIDsLoaded(source, trackIDs, false);
    }
}
