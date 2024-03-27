package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.PublicUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


public interface PublicUserRepository extends CrudRepository<PublicUser, UUID> {

    Optional<PublicUser> findBySpotifyID(String spotifyID);

    default PublicUser upsert(PublicUser publicUser) {
        this.findBySpotifyID(publicUser.getSpotifyID())
                .map(PublicUser::getId)
                .ifPresent(publicUser::setId);

        return this.save(publicUser);
    }
}
