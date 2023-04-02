package de.wittenbude.expandify.repositories.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpotifyUserRepository extends MongoRepository<SpotifyUser, String> {
}
