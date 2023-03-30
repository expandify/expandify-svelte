package de.wittenbude.expandify.repositories;

import de.wittenbude.expandify.models.spotifydata.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtistRepository extends MongoRepository<Artist, String> {
}
