package dev.kenowi.exportify.api.mappers;

import dev.kenowi.exportify.api.dto.AlbumDTO;
import dev.kenowi.exportify.api.dto.SavedAlbumDTO;
import dev.kenowi.exportify.snapshot.entities.Album;
import dev.kenowi.exportify.snapshot.entities.valueobjects.SavedAlbum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI)
public interface AlbumDtoMapper {

    @Mapping(target = "releaseDatePrecision", source = "releaseDatePrecision")
    AlbumDTO toDTO(Album album);

    SavedAlbumDTO toDTO(SavedAlbum album);
}
