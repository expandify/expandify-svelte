package dev.kenowi.exportify.snapshot.repositories;

import dev.kenowi.exportify.snapshot.entities.Track;
import dev.kenowi.exportify.snapshot.entities.equalities.TrackEquality;
import dev.kenowi.exportify.snapshot.entities.valueobjects.SavedTrack;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrackRepository extends CrudRepository<Track, UUID> {

    @Query("select a from Track a where a.spotifyID = :spotifyID order by a.versionTimestamp desc limit 1")
    Optional<Track> findLatest(String spotifyID);

    @Query("select st from Snapshot s join s.savedTracks st where s.id = :snapshot and st.track.spotifyID = :spotifyArtistID and st.track.versionTimestamp <= s.snapshotDate order by st.track.versionTimestamp desc limit 1")
    Optional<SavedTrack> getFromSnapshot(UUID snapshot, String spotifyArtistID);

    @Query("select st from Snapshot s join s.savedTracks st where s.id = :snapshot and st.track.versionTimestamp <= s.snapshotDate order by st.track.versionTimestamp desc")
    List<SavedTrack> getFromSnapshot(UUID snapshot);

    default Track upsert(Track track) {
        return this.findLatest(track.getSpotifyID())
                .filter(a -> TrackEquality.equals(a, track))
                .orElseGet(() -> this.save(track));
    }
}
