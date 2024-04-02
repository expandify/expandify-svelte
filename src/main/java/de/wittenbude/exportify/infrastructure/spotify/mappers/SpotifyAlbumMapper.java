package de.wittenbude.exportify.infrastructure.spotify.mappers;

import de.wittenbude.exportify.domain.entities.Album;
import de.wittenbude.exportify.domain.entities.SavedAlbum;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyAlbum;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifySavedAlbum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
//TODO check if "@Mapper(uses = ImageMapper.class)" is needed or if dependency injection handles it already
public interface SpotifyAlbumMapper {


    @Mapping(target = "albumType", source = "albumType.type")
    @Mapping(target = "spotifyID", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restrictions", source = "restrictions.reason")
    @Mapping(target = "spotifyObjectType", source = "type.type")
    @Mapping(target = "spotifyArtistIDs", expression = "java(spotifyAlbum.getArtists().stream().map(a -> a.getId()).toList())")
    Album toEntity(SpotifyAlbum spotifyAlbum);


    @Mapping(target = "savedAt", expression = "java(spotifySavedAlbum.getAddedAt().toInstant())")
    SavedAlbum toEntity(SpotifySavedAlbum spotifySavedAlbum);
}