package dev.kenowi.exportify.snapshot.repositories;

import dev.kenowi.exportify.authentication.AuthenticatedUser;
import dev.kenowi.exportify.snapshot.entities.Track;
import dev.kenowi.exportify.snapshot.entities.equalities.TrackEquality;
import dev.kenowi.exportify.snapshot.entities.valueobjects.SavedTrack;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TrackRepository {

    private final EntityManager entityManager;

    public TrackRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Optional<Track> findLatest(String spotifyID) {
        return entityManager
                .createQuery("select a from Track a where a.spotifyID = :spotifyID order by a.versionTimestamp desc", Track.class)
                .setMaxResults(1)
                .setParameter("spotifyID", spotifyID)
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public Optional<SavedTrack> getFromSnapshot(UUID snapshot, String spotifyTrackID) {
        return entityManager
                .createQuery("select t " +
                        "from Snapshot s " +
                        "join s.savedTracks t " +
                        "where s.id = :snapshot " +
                        "and s.exportifyUser.id = :userID " +
                        "and t.track.spotifyID = :spotifyTrackID " +
                        "and t.track.versionTimestamp <= s.snapshotDate " +
                        "order by t.track.versionTimestamp " +
                        "desc", SavedTrack.class)
                .setMaxResults(1)
                .setParameter("snapshot", snapshot)
                .setParameter("spotifyTrackID", spotifyTrackID)
                .setParameter("userID", AuthenticatedUser.current().getID())
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public List<SavedTrack> getFromSnapshot(UUID snapshot) {
        return entityManager
                .createQuery("select t " +
                        "from Snapshot s " +
                        "join s.savedTracks t " +
                        "where s.id = :snapshot " +
                        "and s.exportifyUser.id = :userID " +
                        "and t.track.versionTimestamp <= s.snapshotDate " +
                        "order by t.track.versionTimestamp " +
                        "desc", SavedTrack.class)
                .setMaxResults(1)
                .setParameter("snapshot", snapshot)
                .setParameter("userID", AuthenticatedUser.current().getID())
                .getResultList();
    }


    @Transactional
    public Track upsert(Track track) {
        return this.findLatest(track.getSpotifyID())
                .filter(a -> TrackEquality.equals(a, track))
                .orElseGet(() -> this.save(track));
    }


    private Track save(Track track) {
        if (track.getId() == null) {
            entityManager.persist(track);
            return track;
        } else {
            return entityManager.merge(track);
        }
    }

}
