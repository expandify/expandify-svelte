package de.wittenbude.expandify.repositories.users;

import de.wittenbude.expandify.models.db.SpotifyApiCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;

interface SpotifyApiCredentialRepository extends MongoRepository<SpotifyApiCredentials, String> {
}
