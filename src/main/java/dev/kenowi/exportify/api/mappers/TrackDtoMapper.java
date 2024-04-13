package dev.kenowi.exportify.api.mappers;


import dev.kenowi.exportify.api.dto.SavedTrackDTO;
import dev.kenowi.exportify.api.dto.TrackDTO;
import dev.kenowi.exportify.snapshot.entities.Track;
import dev.kenowi.exportify.snapshot.entities.valueobjects.SavedTrack;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI)
public interface TrackDtoMapper {

    TrackDTO toDTO(Track track);

    SavedTrackDTO toDTO(SavedTrack track);
}
