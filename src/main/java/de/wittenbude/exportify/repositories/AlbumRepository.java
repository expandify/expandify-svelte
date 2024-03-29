package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.Album;
import de.wittenbude.exportify.models.Snapshot;
import de.wittenbude.exportify.models.comparators.AlbumEquality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface AlbumRepository extends CrudRepository<Album, UUID> {

    @Query("select a from Album a where a.spotifyID = :spotifyID order by a.versionTimestamp desc limit 1")
    Optional<Album> findLatest(String spotifyID);

    @Query("select sa from Snapshot s join s.savedAlbums sa where s.id = :snapshot and sa.album.spotifyID = :spotifyArtistID and sa.album.versionTimestamp <= s.snapshotDate order by sa.album.versionTimestamp desc limit 1")
    Optional<Snapshot.SavedAlbum> getFromSnapshot(UUID snapshot, String spotifyArtistID);

    @Query("select sa from Snapshot s join s.savedAlbums sa where s.id = :snapshot and sa.album.versionTimestamp <= s.snapshotDate order by sa.album.versionTimestamp desc")
    List<Snapshot.SavedAlbum> getFromSnapshot(UUID snapshot);

    default Album upsert(Album album) {
        return this.findLatest(album.getSpotifyID())
                .filter(a -> AlbumEquality.equals(a, album))
                .orElseGet(() -> this.save(album));
    }

}
