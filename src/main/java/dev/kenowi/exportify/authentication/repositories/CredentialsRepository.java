package dev.kenowi.exportify.authentication.repositories;

import dev.kenowi.exportify.authentication.entities.SpotifyCredentials;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CredentialsRepository {

    private final EntityManager entityManager;

    public CredentialsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional
    public Optional<SpotifyCredentials> findByUserID(UUID userID) {
        String query = "SELECT s FROM SpotifyCredentials s WHERE s.exportifyUser.id = :userID";
        return entityManager
                .createQuery(query, SpotifyCredentials.class)
                .setParameter("userID", userID)
                .getResultStream()
                .findFirst();
    }


    @Transactional
    public SpotifyCredentials upsert(SpotifyCredentials spotifyCredentials) {
        this.findByUserID(spotifyCredentials.getExportifyUser().getId())
                .map(SpotifyCredentials::getId)
                .ifPresent(spotifyCredentials::setId);

        return this.save(spotifyCredentials);
    }

    private SpotifyCredentials save(SpotifyCredentials spotifyCredentials) {
        if (spotifyCredentials.getId() == null) {
            entityManager.persist(spotifyCredentials);
            return spotifyCredentials;
        } else {
            return entityManager.merge(spotifyCredentials);
        }
    }

}
