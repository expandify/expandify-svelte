package de.wittenbude.expandify.repositories.playlists;

import de.wittenbude.expandify.models.db.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;

interface PlaylistRepository extends MongoRepository<Playlist, String> {

}
