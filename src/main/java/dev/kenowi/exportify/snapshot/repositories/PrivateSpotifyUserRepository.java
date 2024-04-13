package dev.kenowi.exportify.snapshot.repositories;


import dev.kenowi.exportify.snapshot.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.snapshot.entities.equalities.UserEquality;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;


@ApplicationScoped
public class PrivateSpotifyUserRepository {

    private final EntityManager entityManager;

    public PrivateSpotifyUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Optional<PrivateSpotifyUser> findLatest(String spotifyID) {
        return entityManager
                .createQuery("select a from PrivateSpotifyUser a where a.spotifyID = :spotifyID order by a.versionTimestamp desc", PrivateSpotifyUser.class)
                .setMaxResults(1)
                .setParameter("spotifyID", spotifyID)
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public PrivateSpotifyUser upsert(PrivateSpotifyUser privateSpotifyUser) {
        return this.findLatest(privateSpotifyUser.getSpotifyID())
                .filter(u -> UserEquality.equals(u, privateSpotifyUser))
                .orElseGet(() -> this.save(privateSpotifyUser));
    }

    private PrivateSpotifyUser save(PrivateSpotifyUser exportifyUser) {
        if (exportifyUser.getId() == null) {
            entityManager.persist(exportifyUser);
            return exportifyUser;
        } else {
            return entityManager.merge(exportifyUser);
        }
    }
}
