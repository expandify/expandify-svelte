package dev.kenowi.exportify.snapshot.api.mappers;


import dev.kenowi.exportify.snapshot.api.dto.SavedTrackDTO;
import dev.kenowi.exportify.snapshot.api.dto.TrackDTO;
import dev.kenowi.exportify.snapshot.entities.Track;
import dev.kenowi.exportify.snapshot.entities.valueobjects.SavedTrack;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackDtoMapper {

    TrackDTO toDTO(Track track);

    SavedTrackDTO toDTO(SavedTrack track);
}
