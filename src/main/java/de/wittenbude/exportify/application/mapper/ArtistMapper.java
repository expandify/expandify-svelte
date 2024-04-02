package de.wittenbude.exportify.application.mapper;

import de.wittenbude.exportify.application.dto.ArtistSchema;
import de.wittenbude.exportify.domain.entities.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArtistMapper {

    ArtistSchema toDTO(Artist artist);
}
