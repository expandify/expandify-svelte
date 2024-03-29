package de.wittenbude.exportify.snapshot;

import de.wittenbude.exportify.snapshot.api.Snapshot;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface SnapshotRepository extends CrudRepository<Snapshot, UUID> {

}