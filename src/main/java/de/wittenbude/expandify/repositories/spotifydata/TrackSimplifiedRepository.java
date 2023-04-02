package de.wittenbude.expandify.repositories.spotifydata;

import de.wittenbude.expandify.models.spotifydata.TrackSimplified;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackSimplifiedRepository extends MongoRepository<TrackSimplified, String> {
}
