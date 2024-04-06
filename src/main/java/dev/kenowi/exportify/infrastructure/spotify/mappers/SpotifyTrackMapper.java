package dev.kenowi.exportify.infrastructure.spotify.mappers;

import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedTrack;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifySavedTrack;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpotifyTrackMapper {

    @Mapping(target = "spotifyArtistIDs", expression = "java(spotifyTrack.getArtists().stream().map(a -> a.getId()).toList())")
    @Mapping(target = "spotifyObjectType", source = "type")
    @Mapping(target = "restrictions", source = "restrictions.reason")
    @Mapping(target = "spotifyAlbumID", source = "album.id")
    @Mapping(target = "spotifyID", source = "id")
    @Mapping(target = "id", ignore = true)
    Track toEntity(SpotifyTrack spotifyTrack);

    @Mapping(target = "savedAt", expression = "java(spotifySavedTrack.getAddedAt().toInstant())")
    SavedTrack toEntity(SpotifySavedTrack spotifySavedTrack);
}
