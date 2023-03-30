package de.wittenbude.expandify.repositories;

import de.wittenbude.expandify.models.spotifydata.Track;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackRepository extends MongoRepository<Track, String> {
}
