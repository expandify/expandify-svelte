package dev.kenowi.exportify.snapshot.mappers;

import dev.kenowi.exportify.snapshot.entities.Playlist;
import dev.kenowi.exportify.snapshot.entities.valueobjects.PlaylistTrack;
import dev.kenowi.exportify.snapshot.entities.valueobjects.SpotifyObjectType;
import dev.kenowi.exportify.spotify.dao.SpotifyIdProjection;
import dev.kenowi.exportify.spotify.dao.SpotifyPlaylist;
import dev.kenowi.exportify.spotify.dao.SpotifyPlaylistTrack;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI)
//TODO check if "@Mapper(uses = ImageMapper.class)" is needed or if dependency injection handles it already
public interface SpotifyPlaylistMapper {


    @Mapping(target = "followers", source = "followers.total")
    @Mapping(target = "spotifyID", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tracks", ignore = true)
    @Mapping(target = "ownerPublicUserSpotifyID", source = "owner.id")
    @Mapping(target = "spotifyObjectType", source = "type")
    @Mapping(target = "spotifySnapshotId", source = "snapshotId")
    @Mapping(target = "totalTracks", expression = "java(spotifyPlaylist.getTracks().getTotal())")
    Playlist toEntity(SpotifyPlaylist spotifyPlaylist);


    @Mapping(target = "addedAt", expression = "java(spotifyPlaylistTrack.getAddedAt() != null ? spotifyPlaylistTrack.getAddedAt().toInstant() : null)")
    @Mapping(target = "addedByPublicUserSpotifyID", source = "addedBy.id", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "spotifyTrackID", source = "track.id", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "spotifyObjectType", expression = "java(this.map(spotifyPlaylistTrack.getTrack()))")
    PlaylistTrack map(SpotifyPlaylistTrack spotifyPlaylistTrack);


    default SpotifyObjectType map(SpotifyIdProjection track) {

        if (track == null) {
            return null;
        }

        if (track.getType() == null) {
            return null;
        }

        return SpotifyObjectType.valueOf(track.getType().name());
    }
}
