package de.wittenbude.exportify.domain.context.snapshot;

import de.wittenbude.exportify.domain.entities.Snapshot;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SnapshotRepository extends CrudRepository<Snapshot, UUID> {

}