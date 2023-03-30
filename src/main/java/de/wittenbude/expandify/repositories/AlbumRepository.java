package de.wittenbude.expandify.repositories;

import de.wittenbude.expandify.models.spotifydata.Album;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  AlbumRepository extends MongoRepository<Album, String> {
}
