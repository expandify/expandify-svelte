package dev.kenowi.exportify.application.mapper;

import dev.kenowi.exportify.application.dto.ArtistSchema;
import dev.kenowi.exportify.domain.entities.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArtistMapper {

    ArtistSchema toDTO(Artist artist);
}
