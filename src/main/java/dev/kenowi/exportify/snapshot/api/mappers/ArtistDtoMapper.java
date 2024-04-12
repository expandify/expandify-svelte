package dev.kenowi.exportify.snapshot.api.mappers;

import dev.kenowi.exportify.snapshot.api.dto.ArtistDTO;
import dev.kenowi.exportify.snapshot.entities.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArtistDtoMapper {

    ArtistDTO toDTO(Artist artist);
}
