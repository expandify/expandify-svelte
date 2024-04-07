package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.domain.entities.Album;
import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedTrack;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;
import java.util.List;

@Getter
@Slf4j
public class ArtistIDsLoaded extends ApplicationEvent {
    private final Collection<String> artistsIDs;

    private ArtistIDsLoaded(Object source, Collection<String> artistsIDs) {
        super(source);
        this.artistsIDs = artistsIDs;
    }

    public static ArtistIDsLoaded fromTracks(Object source, Collection<Track> tracks) {
        List<String> artistsIDs = tracks
                .stream()
                .map(Track::getSpotifyArtistIDs)
                .flatMap(List::stream)
                .toList();
        return new ArtistIDsLoaded(source, artistsIDs);
    }

    public static ArtistIDsLoaded fromSavedTracks(Object source, Collection<SavedTrack> savedTracks) {
        List<String> artistsIDs = savedTracks
                .stream()
                .map(SavedTrack::getTrack)
                .map(Track::getSpotifyArtistIDs)
                .flatMap(List::stream)
                .toList();
        return new ArtistIDsLoaded(source, artistsIDs);
    }

    public static ArtistIDsLoaded fromAlbums(Object source, Collection<Album> albums) {
        List<String> artistsIDs = albums
                .stream()
                .map(Album::getSpotifyArtistIDs)
                .flatMap(List::stream)
                .toList();
        return new ArtistIDsLoaded(source, artistsIDs);
    }

    public static ArtistIDsLoaded fromSavedAlbums(Object source, Collection<SavedAlbum> savedAlbums) {
        List<String> artistsIDs = savedAlbums
                .stream()
                .map(SavedAlbum::getAlbum)
                .map(Album::getSpotifyArtistIDs)
                .flatMap(List::stream)
                .toList();
        return new ArtistIDsLoaded(source, artistsIDs);
    }
}
