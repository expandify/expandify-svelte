package de.wittenbude.exportify.db.repositories;

import de.wittenbude.exportify.db.entity.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


public interface ArtistRepository extends CrudRepository<Artist, UUID> {


    Optional<Artist> findBySpotifyID(String id);

}
