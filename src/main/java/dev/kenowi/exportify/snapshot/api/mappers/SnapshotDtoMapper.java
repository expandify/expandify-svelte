package dev.kenowi.exportify.snapshot.api.mappers;


import dev.kenowi.exportify.snapshot.api.dto.SnapshotDTO;
import dev.kenowi.exportify.snapshot.entities.Snapshot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SnapshotDtoMapper {

    SnapshotDTO toDTO(Snapshot snapshot);
}
