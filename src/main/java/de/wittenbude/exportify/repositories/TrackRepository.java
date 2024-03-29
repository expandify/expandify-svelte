package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.Track;
import de.wittenbude.exportify.models.comparators.TrackEquality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TrackRepository extends CrudRepository<Track, UUID> {

    @Query("select a from Track a where a.spotifyID = :spotifyID order by a.versionTimestamp desc limit 1")
    Optional<Track> findLatest(String spotifyID);

    default Track upsert(Track track) {
        return this.findLatest(track.getSpotifyID())
                .filter(a -> TrackEquality.equals(a, track))
                .orElseGet(() -> this.save(track));
    }
}
