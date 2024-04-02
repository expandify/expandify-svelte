package de.wittenbude.exportify.application.mapper;


import de.wittenbude.exportify.application.dto.AlbumSchema;
import de.wittenbude.exportify.application.dto.SavedAlbumSchema;
import de.wittenbude.exportify.domain.entities.Album;
import de.wittenbude.exportify.domain.entities.SavedAlbum;
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
