package de.wittenbude.expandify.repositories.spotifydata;

import de.wittenbude.expandify.models.spotifydata.ArtistSimplified;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtistSimplifiedRepository extends MongoRepository<ArtistSimplified, String> {
}
