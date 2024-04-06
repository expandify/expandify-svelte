package dev.kenowi.exportify.domain.service.snapshot;

import dev.kenowi.exportify.domain.entities.Snapshot;
import dev.kenowi.exportify.domain.entities.valueobjects.EventStatus;
import dev.kenowi.exportify.domain.exception.EntityNotFoundException;
import dev.kenowi.exportify.domain.exception.SnapshotNotReadyException;
import dev.kenowi.exportify.domain.service.exportifyuser.AuthenticatedUser;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
        eventPublisher.publishEvent(new SnapshotCreatedEvent(snapshot));

        return snapshotRepository.save(snapshot);
    }

    @Async
    @EventListener
    public void onSnapshotArtists(SnapshotCreatedEvent.SnapshotArtistsCreated event) {
        Snapshot snapshot = event
                .getSnapshot()
                .setArtists(event.getData())
                .setArtistsStatus(EventStatus.FINISHED);
        snapshotRepository.save(snapshot);
    }

    @Async
    @EventListener
    public void onSnapshotAlbums(SnapshotCreatedEvent.SnapshotAlbumsCreated event) {
        Snapshot snapshot = event
                .getSnapshot()
                .setSavedAlbums(event.getData())
                .setAlbumsStatus(EventStatus.FINISHED);
        snapshotRepository.save(snapshot);
    }

    @Async
    @EventListener
    public void onSnapshotTracks(SnapshotCreatedEvent.SnapshotTracksCreated event) {
        Snapshot snapshot = event
                .getSnapshot()
                .setSavedTracks(event.getData())
                .setTracksStatus(EventStatus.FINISHED);
        snapshotRepository.save(snapshot);
    }

    @Async
    @EventListener
    public void onSnapshotPlaylists(SnapshotCreatedEvent.SnapshotPlaylistsCreated event) {
        Snapshot snapshot = event
                .getSnapshot()
                .setPlaylists(event.getData())
                .setPlaylistsStatus(EventStatus.FINISHED);
        snapshotRepository.save(snapshot);
    }

    @Async
    @EventListener
    public void onSnapshotUser(SnapshotCreatedEvent.SnapshotUserCreated event) {
        Snapshot snapshot = event
                .getSnapshot()
                .setPrivateSpotifyUser(event.getData())
                .setUserStatus(EventStatus.FINISHED);
        snapshotRepository.save(snapshot);
    }

    public Snapshot get(UUID id) {
        Snapshot snapshot = snapshotRepository
                .findById(id)
                .filter(s -> s.getExportifyUser().getId().equals(AuthenticatedUser.getSecurityContext().getID()))
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(id)));

        if (snapshot.getAlbumsStatus() != EventStatus.FINISHED
                || snapshot.getTracksStatus() != EventStatus.FINISHED
                || snapshot.getArtistsStatus() != EventStatus.FINISHED
                || snapshot.getPlaylistsStatus() != EventStatus.FINISHED
                || snapshot.getUserStatus() != EventStatus.FINISHED) {
            throw new SnapshotNotReadyException("Snapshot not ready");
        }
        return snapshot;
    }
}
