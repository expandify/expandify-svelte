package de.wittenbude.expandify.repositories;

import de.wittenbude.expandify.models.spotifydata.SpotifyLibrary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends MongoRepository<SpotifyLibrary, String> {

    List<SpotifyLibrary> findAllByOwner_Id(String id);

    Optional<SpotifyLibrary> findAllByOwner_IdAndLatestTrue(String id);
}
