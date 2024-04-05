package dev.kenowi.exportify.infrastructure.spotify.mappers;

import dev.kenowi.exportify.domain.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.domain.entities.PublicSpotifyUser;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPrivateUser;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPublicUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpotifyUserMapper {

    @Mapping(target = "spotifyID", source = "id")
    @Mapping(target = "spotifyProductType", source = "product")
    @Mapping(target = "explicitContentFilterEnabled", source = "spotifyExplicitContent.filterEnabled")
    @Mapping(target = "explicitContentFilterLocked", source = "spotifyExplicitContent.filterLocked")
    @Mapping(target = "publicSpotifyUserID", source = "id")
    @Mapping(target = "id", ignore = true)
    PrivateSpotifyUser toEntity(SpotifyPrivateUser spotifyPrivateUser);


    @Mapping(target = "spotifyID", source = "id")
    @Mapping(target = "followers", source = "spotifyFollowers.total")
    @Mapping(target = "images", source = "spotifyImages")
    @Mapping(target = "spotifyObjectType", source = "type")
    @Mapping(target = "id", ignore = true)
    PublicSpotifyUser toEntity(SpotifyPublicUser spotifyPublicUser);
}
