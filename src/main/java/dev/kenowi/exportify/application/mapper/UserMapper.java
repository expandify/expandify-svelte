package dev.kenowi.exportify.application.mapper;


import dev.kenowi.exportify.application.dto.PrivateUserSchema;
import dev.kenowi.exportify.application.dto.PublicUserSchema;
import dev.kenowi.exportify.domain.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.domain.entities.PublicSpotifyUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {


    PrivateUserSchema toDTO(PrivateSpotifyUser privateSpotifyUser);

    PublicUserSchema toDTO(PublicSpotifyUser publicSpotifyUser);
}
