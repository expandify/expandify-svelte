package de.wittenbude.expandify.repositories;

import de.wittenbude.expandify.models.spotifydata.SpotifyTrack;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackRepository extends MongoRepository<SpotifyTrack, String> {
}
