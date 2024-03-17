package de.wittenbude.exportify.db.repositories;

import de.wittenbude.exportify.db.entity.SpotifyCredentials;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpotifyCredentialsRepository extends CrudRepository<SpotifyCredentials, UUID> {
}
