package dev.kenowi.exportify.application.mapper;


import dev.kenowi.exportify.application.dto.SavedTrackSchema;
import dev.kenowi.exportify.application.dto.TrackSchema;
import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.valueobjects.SavedTrack;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackMapper {

    TrackSchema toDTO(Track track);

    SavedTrackSchema toDTO(SavedTrack track);
}
