package de.wittenbude.expandify.repositories.episodes;

import de.wittenbude.expandify.models.db.Episode;
import org.springframework.data.mongodb.repository.MongoRepository;

interface EpisodeRepository extends MongoRepository<Episode, String> {
}
