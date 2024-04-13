package dev.kenowi.exportify.api.mappers;


import dev.kenowi.exportify.api.dto.PrivateUserDTO;
import dev.kenowi.exportify.api.dto.PublicUserDTO;
import dev.kenowi.exportify.snapshot.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.snapshot.entities.PublicSpotifyUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI)
public interface UserDtoMapper {


    PrivateUserDTO toDTO(PrivateSpotifyUser privateSpotifyUser);

    PublicUserDTO toDTO(PublicSpotifyUser publicSpotifyUser);
}
