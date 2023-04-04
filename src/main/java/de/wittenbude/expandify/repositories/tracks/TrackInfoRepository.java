package de.wittenbude.expandify.repositories.tracks;

import de.wittenbude.expandify.models.db.TrackInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

interface TrackInfoRepository extends MongoRepository<TrackInfo, String> {
}
