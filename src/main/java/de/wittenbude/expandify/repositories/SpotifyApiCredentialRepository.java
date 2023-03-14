package de.wittenbude.expandify.repositories;

import de.wittenbude.expandify.models.SpotifyApiCredential;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpotifyApiCredentialRepository extends MongoRepository<SpotifyApiCredential, String> {
}
