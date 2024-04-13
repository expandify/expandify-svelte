package dev.kenowi.exportify.snapshot.mappers;

import dev.kenowi.exportify.snapshot.entities.Track;
import dev.kenowi.exportify.snapshot.entities.valueobjects.SavedTrack;
import dev.kenowi.exportify.spotify.dao.SpotifySavedTrack;
import dev.kenowi.exportify.spotify.dao.SpotifyTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI)
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
