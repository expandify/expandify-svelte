package dev.kenowi.exportify.api.mappers;

import dev.kenowi.exportify.api.dto.ArtistDTO;
import dev.kenowi.exportify.snapshot.entities.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI)
public interface ArtistDtoMapper {

    ArtistDTO toDTO(Artist artist);
}
