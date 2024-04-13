package dev.kenowi.exportify.snapshot.repositories;


import dev.kenowi.exportify.authentication.AuthenticatedUser;
import dev.kenowi.exportify.snapshot.entities.Playlist;
import dev.kenowi.exportify.snapshot.entities.equalities.PlaylistEquality;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PlaylistRepository {

    private final EntityManager entityManager;

    public PlaylistRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Optional<Playlist> findLatest(String spotifyID) {
        return entityManager
                .createQuery("select p " +
                        "from Playlist p " +
                        "where p.spotifyID = :spotifyID " +
                        "and (p.ownerPublicUserSpotifyID = :userID or p.publicAccess) " +
                        "order by p.versionTimestamp " +
                        "desc", Playlist.class)
                .setMaxResults(1)
                .setParameter("spotifyID", spotifyID)
                .setParameter("userID", AuthenticatedUser.current().getUser().getSpotifyUserID())
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public Optional<Playlist> getFromSnapshot(UUID snapshot, String spotifyPlaylistID) {
        return entityManager
                .createQuery("select p " +
                        "from Snapshot s " +
                        "join s.playlists p " +
                        "where s.id = :snapshot " +
                        "and s.exportifyUser.id = :userID " +
                        "and p.spotifyID = :spotifyPlaylistID " +
                        "and p.versionTimestamp <= s.snapshotDate " +
                        "order by p.versionTimestamp " +
                        "desc", Playlist.class)
                .setMaxResults(1)
                .setParameter("snapshot", snapshot)
                .setParameter("spotifyPlaylistID", spotifyPlaylistID)
                .setParameter("userID", AuthenticatedUser.current().getID())
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public List<Playlist> getFromSnapshot(UUID snapshot) {
        return entityManager
                .createQuery("select p " +
                        "from Snapshot s " +
                        "join s.playlists p " +
                        "where s.id = :snapshot " +
                        "and s.exportifyUser.id = :userID " +
                        "and p.versionTimestamp <= s.snapshotDate " +
                        "order by p.versionTimestamp " +
                        "desc", Playlist.class)
                .setMaxResults(1)
                .setParameter("snapshot", snapshot)
                .setParameter("userID", AuthenticatedUser.current().getID())
                .getResultList();
    }


    @Transactional
    public Playlist upsert(Playlist playlist) {
        return this.findLatest(playlist.getSpotifyID())
                .filter(a -> PlaylistEquality.equals(a, playlist))
                .orElseGet(() -> this.save(playlist));
    }


    private Playlist save(Playlist playlist) {
        if (playlist.getId() == null) {
            entityManager.persist(playlist);
            return playlist;
        } else {
            return entityManager.merge(playlist);
        }
    }

}
