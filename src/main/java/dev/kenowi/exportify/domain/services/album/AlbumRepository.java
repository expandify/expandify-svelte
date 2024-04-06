package dev.kenowi.exportify.domain.services.album;


import dev.kenowi.exportify.domain.entities.Album;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.utils.equalities.AlbumEquality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


interface AlbumRepository extends CrudRepository<Album, UUID> {

    @Query("select a from Album a where a.spotifyID = :spotifyID order by a.versionTimestamp desc limit 1")
    Optional<Album> findLatest(String spotifyID);

    @Query("select sa from Snapshot s join s.savedAlbums sa where s.id = :snapshot and sa.album.spotifyID = :spotifyArtistID and sa.album.versionTimestamp <= s.snapshotDate order by sa.album.versionTimestamp desc limit 1")
    Optional<SavedAlbum> getFromSnapshot(UUID snapshot, String spotifyArtistID);

    @Query("select sa from Snapshot s join s.savedAlbums sa where s.id = :snapshot and sa.album.versionTimestamp <= s.snapshotDate order by sa.album.versionTimestamp desc")
    List<SavedAlbum> getFromSnapshot(UUID snapshot);

    default Album upsert(Album album) {
        return this.findLatest(album.getSpotifyID())
                .filter(a -> AlbumEquality.equals(a, album))
                .orElseGet(() -> this.save(album));
    }

}
