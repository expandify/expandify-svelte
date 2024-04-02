package de.wittenbude.exportify.application.mapper;


import de.wittenbude.exportify.application.dto.SnapshotSchema;
import de.wittenbude.exportify.domain.entities.Snapshot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SnapshotMapper {

    SnapshotSchema toDTO(Snapshot snapshot);
}
