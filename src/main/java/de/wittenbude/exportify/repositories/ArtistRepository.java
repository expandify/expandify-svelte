package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.comparators.ArtistEquality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ArtistRepository extends CrudRepository<Artist, UUID> {

    @Query("select a from Artist a where a.spotifyID = ?1 order by a.versionTimestamp desc limit 1")
    Optional<Artist> findLatest(String spotifyID);

    //@Query("select a from Artist a join Snapshot s on s.artists  where s.id = ?1 and a.spotifyID = ?2 and a.versionTimestamp <= s.snapshotDate order by a.versionTimestamp desc limit 1")
    @Query("select a from Snapshot s join s.artists a where s.id = :snapshot and a.spotifyID = :spotifyArtistID and a.versionTimestamp <= s.snapshotDate order by a.versionTimestamp desc limit 1")
    Optional<Artist> getArtistFromSnapshot(UUID snapshot, String spotifyArtistID);

    @Query("select a from Snapshot s join s.artists a where s.id = :snapshot and a.versionTimestamp <= s.snapshotDate order by a.versionTimestamp desc")
    List<Artist> getArtistsFromSnapshot(UUID snapshot);


    default Artist upsert(Artist artist) {
        return this.findLatest(artist.getSpotifyID())
                .filter(a -> ArtistEquality.equals(a, artist))
                .orElseGet(() -> this.save(artist));
    }

}
