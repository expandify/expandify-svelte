package de.wittenbude.expandify.repositories.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Episode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EpisodeRepository extends MongoRepository<Episode, String> {
}
