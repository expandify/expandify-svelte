package dev.kenowi.exportify.domain.service.artist;

import dev.kenowi.exportify.domain.entities.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface ArtistRepository extends CrudRepository<Artist, UUID> {

    @Query("select a from Artist a where a.spotifyID = :spotifyID order by a.versionTimestamp desc limit 1")
    Optional<Artist> findLatest(String spotifyID);

    @Query("select a from Snapshot s join s.artists a where s.id = :snapshot and a.spotifyID = :spotifyArtistID and a.versionTimestamp <= s.snapshotDate order by a.versionTimestamp desc limit 1")
    Optional<Artist> getFromSnapshot(UUID snapshot, String spotifyArtistID);

    @Query("select a from Snapshot s join s.artists a where s.id = :snapshot and a.versionTimestamp <= s.snapshotDate order by a.versionTimestamp desc")
    List<Artist> getFromSnapshot(UUID snapshot);


    default Artist upsert(Artist artist) {
        return this.findLatest(artist.getSpotifyID())
                .filter(a -> ArtistEquality.equals(a, artist))
                .orElseGet(() -> this.save(artist));
    }

}