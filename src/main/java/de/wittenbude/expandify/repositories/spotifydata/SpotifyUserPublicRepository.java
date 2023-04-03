package de.wittenbude.expandify.repositories.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyUserPublic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpotifyUserPublicRepository extends MongoRepository<SpotifyUserPublic, String> {
}
