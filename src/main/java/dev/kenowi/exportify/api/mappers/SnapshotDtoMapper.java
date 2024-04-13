package dev.kenowi.exportify.api.mappers;


import dev.kenowi.exportify.api.dto.SnapshotDTO;
import dev.kenowi.exportify.snapshot.entities.Snapshot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI)
public interface SnapshotDtoMapper {

    SnapshotDTO toDTO(Snapshot snapshot);
}
