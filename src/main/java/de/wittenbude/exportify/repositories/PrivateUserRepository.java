package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.PrivateUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


public interface PrivateUserRepository extends CrudRepository<PrivateUser, UUID> {

    Optional<PrivateUser> findByPublicUser_SpotifyID(String spotifyID);


    default PrivateUser upsert(PrivateUser privateUser) {
        String spotifyID = privateUser.getPublicUser().getSpotifyID();

        this.findByPublicUser_SpotifyID(spotifyID)
                .map(PrivateUser::getId)
                .ifPresent(privateUser::setId);

        return this.save(privateUser);
    }
}
