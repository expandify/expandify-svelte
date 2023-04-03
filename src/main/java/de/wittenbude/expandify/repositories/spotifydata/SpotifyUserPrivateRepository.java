package de.wittenbude.expandify.repositories.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyUserPrivate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpotifyUserPrivateRepository extends MongoRepository<SpotifyUserPrivate, String> {
}
