package dev.kenowi.exportify.snapshot.repositories;

import dev.kenowi.exportify.authentication.AuthenticatedUser;
import dev.kenowi.exportify.snapshot.entities.Artist;
import dev.kenowi.exportify.snapshot.entities.equalities.ArtistEquality;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ArtistRepository {

    private final EntityManager entityManager;

    public ArtistRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Optional<Artist> findLatest(String spotifyID) {
        return entityManager
                .createQuery("select a from Artist a where a.spotifyID = :spotifyID order by a.versionTimestamp desc", Artist.class)
                .setMaxResults(1)
                .setParameter("spotifyID", spotifyID)
                .getResultStream()
                .findFirst();
    }


    @Transactional
    public Optional<Artist> getFromSnapshot(UUID snapshot, String spotifyArtistID) {
        return entityManager
                .createQuery("select a " +
                        "from Snapshot s " +
                        "join s.artists a " +
                        "where s.id = :snapshot " +
                        "and s.exportifyUser.id = :userID " +
                        "and a.spotifyID = :spotifyArtistID " +
                        "and a.versionTimestamp <= s.snapshotDate " +
                        "order by a.versionTimestamp " +
                        "desc", Artist.class)
                .setMaxResults(1)
                .setParameter("snapshot", snapshot)
                .setParameter("spotifyArtistID", spotifyArtistID)
                .setParameter("userID", AuthenticatedUser.current().getID())
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public List<Artist> getFromSnapshot(UUID snapshot) {
        return entityManager
                .createQuery("select a " +
                        "from Snapshot s " +
                        "join s.artists a " +
                        "where s.id = :snapshot " +
                        "and s.exportifyUser.id = :userID " +
                        "and a.versionTimestamp <= s.snapshotDate " +
                        "order by a.versionTimestamp " +
                        "desc", Artist.class)
                .setMaxResults(1)
                .setParameter("snapshot", snapshot)
                .setParameter("userID", AuthenticatedUser.current().getID())
                .getResultList();
    }


    @Transactional
    public Artist upsert(Artist artist) {
        return this.findLatest(artist.getSpotifyID())
                .filter(a -> ArtistEquality.equals(a, artist))
                .orElseGet(() -> this.save(artist));
    }


    private Artist save(Artist artist) {
        if (artist.getId() == null) {
            entityManager.persist(artist);
            return artist;
        } else {
            return entityManager.merge(artist);
        }
    }
}