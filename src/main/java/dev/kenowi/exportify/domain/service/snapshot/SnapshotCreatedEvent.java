package dev.kenowi.exportify.domain.service.snapshot;

import dev.kenowi.exportify.domain.entities.Snapshot;
import org.springframework.context.ApplicationEvent;

public class SnapshotCreatedEvent extends ApplicationEvent {
    private final Snapshot snapshot;

    SnapshotCreatedEvent(SnapshotService source, Snapshot snapshot) {
        super(source);
        this.snapshot = snapshot;
    }

    public <T> SnapshotDataCreatedEvent<T> dataCreated(Object source, T data) {
        return new SnapshotDataCreatedEvent<>(source, snapshot, data);
    }
}
