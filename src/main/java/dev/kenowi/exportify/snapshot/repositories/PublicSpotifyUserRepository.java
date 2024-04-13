package dev.kenowi.exportify.snapshot.repositories;

import dev.kenowi.exportify.snapshot.entities.PublicSpotifyUser;
import dev.kenowi.exportify.snapshot.entities.equalities.UserEquality;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;


@ApplicationScoped
public class PublicSpotifyUserRepository {

    private final EntityManager entityManager;

    public PublicSpotifyUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Optional<PublicSpotifyUser> findLatest(String spotifyID) {
        return entityManager
                .createQuery("select a from PublicSpotifyUser a where a.spotifyID = :spotifyID order by a.versionTimestamp desc", PublicSpotifyUser.class)
                .setMaxResults(1)
                .setParameter("spotifyID", spotifyID)
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public PublicSpotifyUser upsert(PublicSpotifyUser privateSpotifyUser) {
        return this.findLatest(privateSpotifyUser.getSpotifyID())
                .filter(u -> UserEquality.equals(u, privateSpotifyUser))
                .orElseGet(() -> this.save(privateSpotifyUser));
    }

    private PublicSpotifyUser save(PublicSpotifyUser exportifyUser) {
        if (exportifyUser.getId() == null) {
            entityManager.persist(exportifyUser);
            return exportifyUser;
        } else {
            return entityManager.merge(exportifyUser);
        }
    }
}
