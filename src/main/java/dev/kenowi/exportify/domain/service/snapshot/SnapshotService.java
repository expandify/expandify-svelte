package dev.kenowi.exportify.domain.service.snapshot;

import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.entities.Playlist;
import dev.kenowi.exportify.domain.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.domain.entities.Snapshot;
import dev.kenowi.exportify.domain.exception.EntityNotFoundException;
import dev.kenowi.exportify.domain.service.exportifyuser.AuthenticatedUser;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedTrack;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final ApplicationEventPublisher eventPublisher;

    SnapshotService(SnapshotRepository snapshotRepository,
                    ApplicationEventPublisher eventPublisher) {
        this.snapshotRepository = snapshotRepository;
        this.eventPublisher = eventPublisher;
    }


    public Snapshot create() {

        Snapshot snapshot = new Snapshot()
                .setExportifyUser(AuthenticatedUser.getSecurityContext().getUser());
        eventPublisher.publishEvent(new SnapshotCreatedEvent(this, snapshot));

        return snapshotRepository.save(snapshot);
    }

    @Async
    @EventListener
    public void onSnapshotArtists(SnapshotDataCreatedEvent<Set<Artist>> event) {
        Snapshot snapshot = event.getSnapshot().setArtists(event.getData());
        snapshotRepository.save(snapshot);
    }

    @Async
    @EventListener
    public void onSnapshotAlbums(SnapshotDataCreatedEvent<Set<SavedAlbum>> event) {
        Snapshot snapshot = event.getSnapshot().setSavedAlbums(event.getData());
        snapshotRepository.save(snapshot);
    }

    @Async
    @EventListener
    public void onSnapshotTracks(SnapshotDataCreatedEvent<Set<SavedTrack>> event) {
        Snapshot snapshot = event.getSnapshot().setSavedTracks(event.getData());
        snapshotRepository.save(snapshot);
    }

    @Async
    @EventListener
    public void onSnapshotPlaylists(SnapshotDataCreatedEvent<Set<Playlist>> event) {
        Snapshot snapshot = event.getSnapshot().setPlaylists(event.getData());
        snapshotRepository.save(snapshot);
    }

    @Async
    @EventListener
    public void onSnapshotUser(SnapshotDataCreatedEvent<PrivateSpotifyUser> event) {
        Snapshot snapshot = event.getSnapshot().setPrivateSpotifyUser(event.getData());
        snapshotRepository.save(snapshot);
    }

    public Snapshot get(UUID id) {
        return snapshotRepository
                .findById(id)
                .filter(snapshot -> snapshot.getExportifyUser().getId() == AuthenticatedUser.getSecurityContext().getID())
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(id)));
    }
}
