package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.Snapshot;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SnapshotRepository extends CrudRepository<Snapshot, UUID> {

}