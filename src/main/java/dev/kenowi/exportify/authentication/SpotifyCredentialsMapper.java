package dev.kenowi.exportify.authentication;

import dev.kenowi.exportify.authentication.entities.SpotifyCredentials;
import dev.kenowi.exportify.spotify.dao.SpotifyTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI)
public interface SpotifyCredentialsMapper {

    @Mapping(target = "expiresAt", expression = "java(java.time.Instant.now().plus(spotifyTokenResponse.getExpiresIn(), java.time.temporal.ChronoUnit.SECONDS))")
    SpotifyCredentials toEntity(SpotifyTokenResponse spotifyTokenResponse);
}
