package dev.kenowi.exportify.domain.service.snapshot;

import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.entities.Playlist;
import dev.kenowi.exportify.domain.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.domain.entities.Snapshot;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedTrack;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

@Getter
@Slf4j
public class SnapshotCreatedEvent extends ApplicationEvent {
    private final Snapshot snapshot;

    SnapshotCreatedEvent(Snapshot snapshot) {
        super(SnapshotService.class);
        this.snapshot = snapshot;
    }

    public SnapshotAlbumsCreated albumsCreated(Object source, Set<SavedAlbum> data) {
        log.info("Creating albums for snapshot {}", data.size());
        return new SnapshotAlbumsCreated(source, snapshot, data);
    }

    public SnapshotArtistsCreated artistsCreated(Object source, Set<Artist> data) {
        log.info("Creating artists for snapshot {}", data.size());
        return new SnapshotArtistsCreated(source, snapshot, data);
    }

    public SnapshotPlaylistsCreated playlistsCreated(Object source, Set<Playlist> data) {
        log.info("Creating playlists for snapshot {}", data.size());
        return new SnapshotPlaylistsCreated(source, snapshot, data);
    }

    public SnapshotTracksCreated tracksCreated(Object source, Set<SavedTrack> data) {
        log.info("Creating tracks for snapshot {}", data.size());
        return new SnapshotTracksCreated(source, snapshot, data);
    }

    public SnapshotUserCreated userCreated(Object source, PrivateSpotifyUser data) {
        log.info("Creating user for snapshot {}", data.getPublicSpotifyUserID());
        return new SnapshotUserCreated(source, snapshot, data);
    }


    @Getter
    private static class SnapshotDataCreatedEvent<T> extends ApplicationEvent {

        private final Snapshot snapshot;
        private final T data;

        SnapshotDataCreatedEvent(Object source, Snapshot snapshot, T data) {
            super(source);
            this.snapshot = snapshot;
            this.data = data;
        }
    }

    public static class SnapshotAlbumsCreated extends SnapshotDataCreatedEvent<Set<SavedAlbum>> {
        SnapshotAlbumsCreated(Object source, Snapshot snapshot, Set<SavedAlbum> data) {
            super(source, snapshot, data);
        }
    }

    public static class SnapshotArtistsCreated extends SnapshotDataCreatedEvent<Set<Artist>> {
        SnapshotArtistsCreated(Object source, Snapshot snapshot, Set<Artist> data) {
            super(source, snapshot, data);
        }
    }

    public static class SnapshotPlaylistsCreated extends SnapshotDataCreatedEvent<Set<Playlist>> {
        SnapshotPlaylistsCreated(Object source, Snapshot snapshot, Set<Playlist> data) {
            super(source, snapshot, data);
        }
    }

    public static class SnapshotTracksCreated extends SnapshotDataCreatedEvent<Set<SavedTrack>> {
        SnapshotTracksCreated(Object source, Snapshot snapshot, Set<SavedTrack> data) {
            super(source, snapshot, data);
        }
    }

    public static class SnapshotUserCreated extends SnapshotDataCreatedEvent<PrivateSpotifyUser> {
        SnapshotUserCreated(Object source, Snapshot snapshot, PrivateSpotifyUser data) {
            super(source, snapshot, data);
        }
    }
}
