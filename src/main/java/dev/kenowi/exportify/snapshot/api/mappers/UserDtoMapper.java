package dev.kenowi.exportify.snapshot.api.mappers;


import dev.kenowi.exportify.snapshot.api.dto.PrivateUserDTO;
import dev.kenowi.exportify.snapshot.api.dto.PublicUserDTO;
import dev.kenowi.exportify.snapshot.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.snapshot.entities.PublicSpotifyUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDtoMapper {


    PrivateUserDTO toDTO(PrivateSpotifyUser privateSpotifyUser);

    PublicUserDTO toDTO(PublicSpotifyUser publicSpotifyUser);
}
