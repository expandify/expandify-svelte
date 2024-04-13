package dev.kenowi.exportify.authentication.repositories;

import dev.kenowi.exportify.authentication.entities.ExportifyUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ExportifyUserRepository {
    private final EntityManager entityManager;

    public ExportifyUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional
    public Optional<ExportifyUser> findByID(UUID id) {
        return Optional.ofNullable(entityManager.find(ExportifyUser.class, id));
    }

    @Transactional
    public Optional<ExportifyUser> findBySpotifyID(String spotifyUserID) {
        return entityManager
                .createQuery("select e from ExportifyUser e where e.spotifyUserID = :spotifyUserID", ExportifyUser.class)
                .setParameter("spotifyUserID", spotifyUserID)
                .getResultStream()
                .findFirst();
    }


    @Transactional
    public ExportifyUser save(ExportifyUser exportifyUser) {
        if (exportifyUser.getId() == null) {
            entityManager.persist(exportifyUser);
            return exportifyUser;
        } else {
            return entityManager.merge(exportifyUser);
        }
    }
}