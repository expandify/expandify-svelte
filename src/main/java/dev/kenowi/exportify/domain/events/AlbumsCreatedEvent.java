package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.domain.entities.Album;
import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.entities.Track;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;

@Getter
@Slf4j
public class AlbumsCreatedEvent extends ApplicationEvent {
    private final Collection<Album> albums;
    private final Collection<Track> tracks;
    private final Collection<Artist> artists;
    private final boolean artistsLoaded;
    private final boolean tracksLoaded;

    private AlbumsCreatedEvent(Object source,
                               Collection<Album> albums,
                               Collection<Track> tracks,
                               Collection<Artist> artists) {
        super(source);
        this.albums = albums;
        this.tracks = tracks;
        this.artists = artists;
        this.artistsLoaded = artists != null && !artists.isEmpty();
        this.tracksLoaded = tracks != null && !tracks.isEmpty();
        log.info("Albums created: {}", albums.size());
    }

    public static AlbumsCreatedEvent empty(Object source, Collection<Album> albums) {
        return new AlbumsCreatedEvent(source, albums, null, null);
    }

    public static AlbumsCreatedEvent withTracks(Object source, Collection<Album> albums, Collection<Track> tracks) {
        return new AlbumsCreatedEvent(source, albums, tracks, null);
    }

    public static AlbumsCreatedEvent withArtists(Object source, Collection<Album> albums, Collection<Artist> artists) {
        return new AlbumsCreatedEvent(source, albums, null, artists);
    }

    public static AlbumsCreatedEvent full(Object source, Collection<Album> albums, Collection<Track> tracks, Collection<Artist> artists) {
        return new AlbumsCreatedEvent(source, albums, tracks, artists);
    }
}
