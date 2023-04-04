package de.wittenbude.expandify.repositories.users;

import de.wittenbude.expandify.models.db.SpotifyUserPrivate;
import org.springframework.data.mongodb.repository.MongoRepository;

interface SpotifyUserPrivateRepository extends MongoRepository<SpotifyUserPrivate, String> {
}
