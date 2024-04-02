package de.wittenbude.exportify.application.mapper;


import de.wittenbude.exportify.application.dto.PrivateUserSchema;
import de.wittenbude.exportify.application.dto.PublicUserSchema;
import de.wittenbude.exportify.domain.entities.PrivateSpotifyUser;
import de.wittenbude.exportify.domain.entities.PublicSpotifyUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {


    PrivateUserSchema toDTO(PrivateSpotifyUser privateSpotifyUser);

    PublicUserSchema toDTO(PublicSpotifyUser publicSpotifyUser);
}
