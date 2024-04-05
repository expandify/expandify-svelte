package de.wittenbude.exportify.application.mapper;


import de.wittenbude.exportify.application.dto.SavedTrackSchema;
import de.wittenbude.exportify.application.dto.TrackSchema;
import de.wittenbude.exportify.domain.entities.Track;
import de.wittenbude.exportify.domain.valueobjects.SavedTrack;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackMapper {

    TrackSchema toDTO(Track track);

    SavedTrackSchema toDTO(SavedTrack track);
}
