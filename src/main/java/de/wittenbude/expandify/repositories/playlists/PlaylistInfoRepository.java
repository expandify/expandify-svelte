package de.wittenbude.expandify.repositories.playlists;

import de.wittenbude.expandify.models.db.PlaylistInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

interface PlaylistInfoRepository extends MongoRepository<PlaylistInfo, String> {

}
