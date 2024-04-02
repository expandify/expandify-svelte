package de.wittenbude.exportify.domain.context.snapshot;

import de.wittenbude.exportify.domain.entities.Snapshot;

import java.util.UUID;

public interface SnapshotService {

    Snapshot create();

    Snapshot get(UUID id);
}
