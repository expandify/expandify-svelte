package de.wittenbude.exportify.infrastructure.spotify.mappers;

import de.wittenbude.exportify.domain.entities.Playlist;
import de.wittenbude.exportify.domain.valueobjects.PlaylistTrack;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyPlaylist;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyPlaylistTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
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
    @Mapping(target = "addedByPublicUserSpotifyID", source = "addedBy.id")
    @Mapping(target = "trackSpotifyID", expression = "java(spotifyPlaylistTrack.getTrack().getId())")
    @Mapping(target = "spotifyObjectType", expression = "java(de.wittenbude.exportify.domain.valueobjects.SpotifyObjectType.valueOf(spotifyPlaylistTrack.getTrack().getType().name()))")
    PlaylistTrack map(SpotifyPlaylistTrack spotifyPlaylistTrack);

}
