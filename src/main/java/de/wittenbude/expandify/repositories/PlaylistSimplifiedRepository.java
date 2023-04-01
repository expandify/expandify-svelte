package de.wittenbude.expandify.repositories;

import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaylistSimplifiedRepository extends MongoRepository<PlaylistSimplified, String> {

}
