package de.wittenbude.expandify.repositories.artists;

import de.wittenbude.expandify.models.db.ArtistInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ArtistInfoRepository extends MongoRepository<ArtistInfo, String> {
}
