package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.PublicSpotifyUser;
import de.wittenbude.exportify.models.comparators.UserEquality;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


public interface PublicSpotifyUserRepository extends CrudRepository<PublicSpotifyUser, UUID> {

    Optional<PublicSpotifyUser> findFirstBySpotifyIDOrderByVersionTimestampDesc(String spotifyID);

    default PublicSpotifyUser upsert(PublicSpotifyUser publicSpotifyUser) {
        return this.findFirstBySpotifyIDOrderByVersionTimestampDesc(publicSpotifyUser.getSpotifyID())
                .filter(u -> UserEquality.equals(u, publicSpotifyUser))
                .orElseGet(() -> this.save(publicSpotifyUser));
    }
}
