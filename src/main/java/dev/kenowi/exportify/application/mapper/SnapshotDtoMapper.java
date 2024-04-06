package dev.kenowi.exportify.application.mapper;


import dev.kenowi.exportify.application.dto.SnapshotSchema;
import dev.kenowi.exportify.domain.entities.Snapshot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SnapshotDtoMapper {

    SnapshotSchema toDTO(Snapshot snapshot);
}
