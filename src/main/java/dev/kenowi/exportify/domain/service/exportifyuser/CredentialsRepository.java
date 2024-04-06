package dev.kenowi.exportify.domain.service.exportifyuser;

import dev.kenowi.exportify.domain.entities.SpotifyCredentials;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

interface CredentialsRepository extends CrudRepository<SpotifyCredentials, UUID> {

    @Query("select s from SpotifyCredentials s where s.exportifyUser.id = :userID")
    Optional<SpotifyCredentials> findByUserID(UUID userID);


    default SpotifyCredentials upsert(SpotifyCredentials spotifyCredentials) {
        this.findByUserID(spotifyCredentials.getExportifyUser().getId())
                .map(SpotifyCredentials::getId)
                .ifPresent(spotifyCredentials::setId);

        return this.save(spotifyCredentials);
    }
}
