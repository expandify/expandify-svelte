package de.wittenbude.expandify.repositories.albums;

import de.wittenbude.expandify.models.db.AlbumInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

interface AlbumInfoRepository extends MongoRepository<AlbumInfo, String> {
}
