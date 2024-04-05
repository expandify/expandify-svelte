package dev.kenowi.exportify.application.mapper;


import dev.kenowi.exportify.application.dto.AlbumSchema;
import dev.kenowi.exportify.application.dto.SavedAlbumSchema;
import dev.kenowi.exportify.domain.entities.Album;
import dev.kenowi.exportify.domain.valueobjects.SavedAlbum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumMapper {

    @Mapping(target = "releaseDatePrecision", source = "releaseDatePrecision")
    AlbumSchema toDTO(Album album);

    SavedAlbumSchema toDTO(SavedAlbum album);
}
