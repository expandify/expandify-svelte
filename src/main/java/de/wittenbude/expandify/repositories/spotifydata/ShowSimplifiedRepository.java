package de.wittenbude.expandify.repositories.spotifydata;

import de.wittenbude.expandify.models.spotifydata.ShowSimplified;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShowSimplifiedRepository extends MongoRepository<ShowSimplified, String> {
}
