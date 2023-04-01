package de.wittenbude.expandify.repositories;

import de.wittenbude.expandify.models.Cache;
import de.wittenbude.expandify.models.Library;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CacheRepository extends MongoRepository<Cache, String> {

}
