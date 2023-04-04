package de.wittenbude.expandify.repositories.tracks;

import de.wittenbude.expandify.models.db.Track;
import org.springframework.data.mongodb.repository.MongoRepository;

interface TrackRepository extends MongoRepository<Track, String> {
}
