package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.entities.Playlist;
import dev.kenowi.exportify.domain.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedTrack;
import dev.kenowi.exportify.domain.services.snapshot.SnapshotService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Set;
import java.util.UUID;

@Getter
@Slf4j
public class SnapshotCreatedEvent extends ApplicationEvent {
    private final UUID snapshotID;

    public SnapshotCreatedEvent(UUID snapshotID) {
        super(SnapshotService.class);
        this.snapshotID = snapshotID;
    }

    public SnapshotAlbumsCreated albumsCreated(Object source, Set<SavedAlbum> data) {
        log.info("Creating albums for snapshot {}", data.size());
        return new SnapshotAlbumsCreated(source, snapshotID, data);
    }

    public SnapshotArtistsCreated artistsCreated(Object source, Set<Artist> data) {
        log.info("Creating artists for snapshot {}", data.size());
        return new SnapshotArtistsCreated(source, snapshotID, data);
    }

    public SnapshotPlaylistsCreated playlistsCreated(Object source, Set<Playlist> data) {
        log.info("Creating playlists for snapshot {}", data.size());
        return new SnapshotPlaylistsCreated(source, snapshotID, data);
    }

    public SnapshotTracksCreated tracksCreated(Object source, Set<SavedTrack> data) {
        log.info("Creating tracks for snapshot {}", data.size());
        return new SnapshotTracksCreated(source, snapshotID, data);
    }

    public SnapshotUserCreated userCreated(Object source, PrivateSpotifyUser data) {
        log.info("Creating user for snapshot {}", data.getPublicSpotifyUserID());
        return new SnapshotUserCreated(source, snapshotID, data);
    }


    @Getter
    private static class SnapshotDataCreatedEvent<T> extends ApplicationEvent {

        private final UUID snapshotID;
        private final T data;

        SnapshotDataCreatedEvent(Object source, UUID snapshotID, T data) {
            super(source);
            this.snapshotID = snapshotID;
            this.data = data;
        }
    }

    public static class SnapshotAlbumsCreated extends SnapshotDataCreatedEvent<Set<SavedAlbum>> {
        SnapshotAlbumsCreated(Object source, UUID snapshotID, Set<SavedAlbum> data) {
            super(source, snapshotID, data);
        }
    }

    public static class SnapshotArtistsCreated extends SnapshotDataCreatedEvent<Set<Artist>> {
        SnapshotArtistsCreated(Object source, UUID snapshotID, Set<Artist> data) {
            super(source, snapshotID, data);
        }
    }

    public static class SnapshotPlaylistsCreated extends SnapshotDataCreatedEvent<Set<Playlist>> {
        SnapshotPlaylistsCreated(Object source, UUID snapshotID, Set<Playlist> data) {
            super(source, snapshotID, data);
        }
    }

    public static class SnapshotTracksCreated extends SnapshotDataCreatedEvent<Set<SavedTrack>> {
        SnapshotTracksCreated(Object source, UUID snapshotID, Set<SavedTrack> data) {
            super(source, snapshotID, data);
        }
    }

    public static class SnapshotUserCreated extends SnapshotDataCreatedEvent<PrivateSpotifyUser> {
        SnapshotUserCreated(Object source, UUID snapshotID, PrivateSpotifyUser data) {
            super(source, snapshotID, data);
        }
    }
}
