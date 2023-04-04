package de.wittenbude.expandify.repositories.artists;

import de.wittenbude.expandify.models.db.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ArtistRepository extends MongoRepository<Artist, String> {
}
