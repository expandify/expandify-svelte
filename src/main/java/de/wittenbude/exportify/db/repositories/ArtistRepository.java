package de.wittenbude.exportify.db.repositories;

import de.wittenbude.exportify.db.entity.ArtistEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


public interface ArtistRepository extends CrudRepository<ArtistEntity, UUID> {


    Optional<ArtistEntity> findBySpotifyID(String id);

}
