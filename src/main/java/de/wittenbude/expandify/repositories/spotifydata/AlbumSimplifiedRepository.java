package de.wittenbude.expandify.repositories.spotifydata;

import de.wittenbude.expandify.models.spotifydata.AlbumSimplified;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlbumSimplifiedRepository extends MongoRepository<AlbumSimplified, String> {
}
