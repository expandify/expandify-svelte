package dev.kenowi.exportify.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SnapshotSchema {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("artists")
    private List<ArtistSchema> artists;

    @JsonProperty("saved_albums")
    private List<SavedAlbumSchema> savedAlbums;

    @JsonProperty("saved_tracks")
    private List<SavedTrackSchema> savedTracks;

}
