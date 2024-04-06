package dev.kenowi.exportify.domain.service.snapshot;

import dev.kenowi.exportify.domain.entities.Snapshot;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SnapshotDataCreatedEvent<T> extends ApplicationEvent {

    private final Snapshot snapshot;
    private final T data;

    SnapshotDataCreatedEvent(Object source, Snapshot snapshot, T data) {
        super(source);
        this.snapshot = snapshot;
        this.data = data;
    }
}
