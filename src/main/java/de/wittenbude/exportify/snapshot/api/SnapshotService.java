package de.wittenbude.exportify.snapshot.api;

import java.util.UUID;

public interface SnapshotService {
    Snapshot create();

    void mustExist(UUID snapshot);
}
