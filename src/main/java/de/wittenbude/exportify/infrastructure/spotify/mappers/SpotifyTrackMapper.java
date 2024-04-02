package de.wittenbude.exportify.infrastructure.spotify.mappers;

import de.wittenbude.exportify.domain.entities.Track;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpotifyTrackMapper {

    @Mapping(target = "spotifyArtistIDs", expression = "java(spotifyTrack.getArtists().stream().map(a -> a.getId()).toList())")
    @Mapping(target = "spotifyObjectType", source = "type.type")
    @Mapping(target = "restrictions", source = "restrictions.reason")
    @Mapping(target = "spotifyAlbumID", source = "album.id")
    @Mapping(target = "spotifyID", source = "id")
    @Mapping(target = "id", ignore = true)
    Track toEntity(SpotifyTrack spotifyTrack);
}
