package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.PublicUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


public interface PublicUserRepository extends CrudRepository<PublicUser, UUID> {

    Optional<PublicUser> findBySpotifyID(String spotifyID);
}
