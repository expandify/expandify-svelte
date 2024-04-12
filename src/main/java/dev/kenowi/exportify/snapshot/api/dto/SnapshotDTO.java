package dev.kenowi.exportify.snapshot.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SnapshotDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("artists")
    private List<ArtistDTO> artists;

    @JsonProperty("saved_albums")
    private List<SavedAlbumDTO> savedAlbums;

    @JsonProperty("saved_tracks")
    private List<SavedTrackDTO> savedTracks;

    //TODO Playlist Schema
}
