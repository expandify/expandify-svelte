package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.ExportifyUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExportifyUserRepository extends CrudRepository<ExportifyUser, UUID> {
    @Query("select e from ExportifyUser e where e.spotifyUserID = :spotifyUserID")
    Optional<ExportifyUser> findBySpotifyID(String spotifyUserID);


}