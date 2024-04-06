package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.domain.entities.Album;
import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.entities.Track;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;
import java.util.Collections;

@Getter
@Slf4j
public class TracksCreatedEvent extends ApplicationEvent {
    private final Collection<Track> tracks;
    private final Collection<Album> albums;
    private final Collection<Artist> artists;
    private final boolean artistsLoaded;
    private final boolean albumsLoaded;

    public TracksCreatedEvent(Object source,
                              Collection<Track> tracks,
                              Collection<Album> albums,
                              Collection<Artist> artists) {
        super(source);
        this.tracks = Collections.unmodifiableCollection(tracks);
        this.albums = albums;
        this.artists = artists;
        this.artistsLoaded = artists != null && !artists.isEmpty();
        this.albumsLoaded = albums != null && !albums.isEmpty();
        log.info("Tracks created {}", tracks.size());
    }

    public static TracksCreatedEvent empty(Object source, Collection<Track> tracks) {
        return new TracksCreatedEvent(source, tracks, null, null);
    }

    public static TracksCreatedEvent withAlbums(Object source, Collection<Track> tracks, Collection<Album> albums) {
        return new TracksCreatedEvent(source, tracks, albums, null);
    }

}
