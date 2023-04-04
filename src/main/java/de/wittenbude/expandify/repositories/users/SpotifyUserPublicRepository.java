package de.wittenbude.expandify.repositories.users;

import de.wittenbude.expandify.models.db.SpotifyUserPublic;
import org.springframework.data.mongodb.repository.MongoRepository;

interface SpotifyUserPublicRepository extends MongoRepository<SpotifyUserPublic, String> {
}
