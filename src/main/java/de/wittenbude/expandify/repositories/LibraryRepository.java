package de.wittenbude.expandify.repositories;

import de.wittenbude.expandify.models.Library;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends MongoRepository<Library, String> {

    List<Library> findAllByOwner_Id(String id);

    Optional<Library> findAllByOwner_IdAndLatestTrue(String id);
}
