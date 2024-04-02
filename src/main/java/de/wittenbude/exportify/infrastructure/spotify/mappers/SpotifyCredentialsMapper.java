package de.wittenbude.exportify.infrastructure.spotify.mappers;

import de.wittenbude.exportify.domain.entities.SpotifyCredentials;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpotifyCredentialsMapper {

    @Mapping(target = "expiresAt", expression = "java(java.time.Instant.now().plus(spotifyTokenResponse.getExpiresIn(), java.time.temporal.ChronoUnit.SECONDS))")
    SpotifyCredentials toEntity(SpotifyTokenResponse spotifyTokenResponse);
}
