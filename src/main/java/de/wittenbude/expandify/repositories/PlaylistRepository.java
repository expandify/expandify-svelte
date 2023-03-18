package de.wittenbude.expandify.repositories;

import de.wittenbude.expandify.models.spotifydata.SpotifyPlaylistSimplified;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaylistRepository extends MongoRepository<SpotifyPlaylistSimplified, String> {
}
