package de.wittenbude.exportify.db.repositories;

import de.wittenbude.exportify.db.entity.TrackEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrackRepository extends CrudRepository<TrackEntity, UUID> {
}
