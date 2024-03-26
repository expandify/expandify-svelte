package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.Track;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TrackRepository extends CrudRepository<Track, UUID> {
}
