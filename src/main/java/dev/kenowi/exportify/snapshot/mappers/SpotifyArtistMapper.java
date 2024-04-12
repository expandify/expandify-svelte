package dev.kenowi.exportify.snapshot.mappers;

import dev.kenowi.exportify.snapshot.entities.Artist;
import dev.kenowi.exportify.spotify.dao.SpotifyArtist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
//TODO check if "@Mapper(uses = ImageMapper.class)" is needed or if dependency injection handles it already
public interface SpotifyArtistMapper {

    @Mapping(target = "spotifyID", source = "id")
    @Mapping(target = "spotifyObjectType", source = "type")
    @Mapping(target = "followers", source = "spotifyFollowers.total")
    @Mapping(target = "images", source = "spotifyImages")
    @Mapping(target = "id", ignore = true)
    Artist toEntity(SpotifyArtist person);
}