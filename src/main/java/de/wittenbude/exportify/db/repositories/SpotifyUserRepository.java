package de.wittenbude.exportify.db.repositories;

import de.wittenbude.exportify.db.entity.SpotifyUser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface SpotifyUserRepository extends CrudRepository<SpotifyUser, UUID> {

}
