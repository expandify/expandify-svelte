package de.wittenbude.expandify.repositories.libraries;

import de.wittenbude.expandify.models.db.Library;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface LibraryRepository extends MongoRepository<Library, String> {

    List<Library> findAllByOwner_Id(String id);

}
