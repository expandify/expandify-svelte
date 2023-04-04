package de.wittenbude.expandify.repositories.shows;

import de.wittenbude.expandify.models.db.ShowInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ShowInfoRepository extends MongoRepository<ShowInfo, String> {
}
