package dev.kenowi.exportify.snapshot.repositories;

import dev.kenowi.exportify.snapshot.entities.Snapshot;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SnapshotRepository extends CrudRepository<Snapshot, UUID> {

}