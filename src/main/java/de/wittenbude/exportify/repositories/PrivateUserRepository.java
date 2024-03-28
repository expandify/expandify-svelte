package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.PrivateSpotifyUser;
import de.wittenbude.exportify.models.comparators.UserEquality;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


public interface PrivateUserRepository extends CrudRepository<PrivateSpotifyUser, UUID> {

    Optional<PrivateSpotifyUser> findFirstBySpotifyIDOrderByVersionTimestampDesc(String spotifyID);


    default PrivateSpotifyUser upsert(PrivateSpotifyUser privateSpotifyUser) {
        return this.findFirstBySpotifyIDOrderByVersionTimestampDesc(privateSpotifyUser.getSpotifyID())
                .filter(u -> UserEquality.equals(u, privateSpotifyUser))
                .orElseGet(() -> this.save(privateSpotifyUser));
    }
}
