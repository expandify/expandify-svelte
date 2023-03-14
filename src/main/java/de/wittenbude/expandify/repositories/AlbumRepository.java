package de.wittenbude.expandify.repositories;

import de.wittenbude.expandify.models.spotifydata.SpotifyAlbum;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  AlbumRepository extends MongoRepository<SpotifyAlbum, String> {
}
