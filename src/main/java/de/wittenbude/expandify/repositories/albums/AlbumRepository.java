package de.wittenbude.expandify.repositories.albums;

import de.wittenbude.expandify.models.db.Album;
import org.springframework.data.mongodb.repository.MongoRepository;

interface AlbumRepository extends MongoRepository<Album, String> {
}
