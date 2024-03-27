package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


public interface AlbumRepository extends CrudRepository<Album, UUID> {

    Optional<Album> findBySpotifyID(String id);

}
