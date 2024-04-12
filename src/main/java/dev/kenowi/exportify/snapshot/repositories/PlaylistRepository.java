package dev.kenowi.exportify.snapshot.repositories;


import dev.kenowi.exportify.snapshot.entities.Playlist;
import dev.kenowi.exportify.snapshot.entities.equalities.PlaylistEquality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PlaylistRepository extends CrudRepository<Playlist, UUID> {

    @Query("select a from Album a where a.spotifyID = :spotifyID order by a.versionTimestamp desc limit 1")
    Optional<Playlist> findLatest(String spotifyID);

    @Query("select p from Snapshot s join s.playlists p where s.id = :snapshot and p.spotifyID = :spotifyPlaylistID and p.versionTimestamp <= s.snapshotDate order by p.versionTimestamp desc limit 1")
    Optional<Playlist> getFromSnapshot(UUID snapshot, String spotifyPlaylistID);

    @Query("select p from Snapshot s join s.playlists p where s.id = :snapshot and p.versionTimestamp <= s.snapshotDate order by p.versionTimestamp desc")
    List<Playlist> getFromSnapshot(UUID snapshot);

    default Playlist upsert(Playlist playlist) {
        return this.findLatest(playlist.getSpotifyID())
                .filter(a -> PlaylistEquality.equals(a, playlist))
                .orElseGet(() -> this.save(playlist));
    }

}
