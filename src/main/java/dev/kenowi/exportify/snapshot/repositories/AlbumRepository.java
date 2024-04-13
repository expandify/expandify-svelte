package dev.kenowi.exportify.snapshot.repositories;


import dev.kenowi.exportify.authentication.AuthenticatedUser;
import dev.kenowi.exportify.snapshot.entities.Album;
import dev.kenowi.exportify.snapshot.entities.equalities.AlbumEquality;
import dev.kenowi.exportify.snapshot.entities.valueobjects.SavedAlbum;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
public class AlbumRepository {

    private final EntityManager entityManager;

    public AlbumRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Optional<Album> findLatest(String spotifyID) {
        return entityManager
                .createQuery("select a " +
                        "from Album a " +
                        "where a.spotifyID = :spotifyID " +
                        "order by a.versionTimestamp " +
                        "desc", Album.class)
                .setMaxResults(1)
                .setParameter("spotifyID", spotifyID)
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public Optional<SavedAlbum> getFromSnapshot(UUID snapshot, String spotifyAlbumID) {
        return entityManager
                .createQuery("select a " +
                        "from Snapshot s " +
                        "join s.savedAlbums a " +
                        "where s.id = :snapshot " +
                        "and s.exportifyUser.id = :userID " +
                        "and a.album.spotifyID = :spotifyAlbumID " +
                        "and a.album.versionTimestamp <= s.snapshotDate " +
                        "order by a.album.versionTimestamp " +
                        "desc", SavedAlbum.class)
                .setMaxResults(1)
                .setParameter("snapshot", snapshot)
                .setParameter("spotifyAlbumID", spotifyAlbumID)
                .setParameter("userID", AuthenticatedUser.current().getID())
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public List<SavedAlbum> getFromSnapshot(UUID snapshot) {
        return entityManager
                .createQuery("select a " +
                        "from Snapshot s " +
                        "join s.savedAlbums a " +
                        "where s.id = :snapshot " +
                        "and s.exportifyUser.id = :userID " +
                        "and a.album.versionTimestamp <= s.snapshotDate " +
                        "order by a.album.versionTimestamp " +
                        "desc", SavedAlbum.class)
                .setMaxResults(1)
                .setParameter("snapshot", snapshot)
                .setParameter("userID", AuthenticatedUser.current().getID())
                .getResultList();
    }

    @Transactional
    public Album upsert(Album album) {
        return this.findLatest(album.getSpotifyID())
                .filter(a -> AlbumEquality.equals(a, album))
                .orElseGet(() -> this.save(album));
    }


    private Album save(Album album) {
        if (album.getId() == null) {
            entityManager.persist(album);
            return album;
        } else {
            return entityManager.merge(album);
        }
    }

}
