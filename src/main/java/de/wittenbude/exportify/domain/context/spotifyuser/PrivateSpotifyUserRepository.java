package de.wittenbude.exportify.domain.context.spotifyuser;


import de.wittenbude.exportify.domain.entities.PrivateSpotifyUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


interface PrivateSpotifyUserRepository extends CrudRepository<PrivateSpotifyUser, UUID> {

    @Query("select a from PrivateSpotifyUser a where a.spotifyID = ?1 order by a.versionTimestamp desc limit 1")
    Optional<PrivateSpotifyUser> findLatest(String spotifyID);


    default PrivateSpotifyUser upsert(PrivateSpotifyUser privateSpotifyUser) {
        return this.findLatest(privateSpotifyUser.getSpotifyID())
                .filter(u -> UserEquality.equals(u, privateSpotifyUser))
                .orElseGet(() -> this.save(privateSpotifyUser));
    }
}
