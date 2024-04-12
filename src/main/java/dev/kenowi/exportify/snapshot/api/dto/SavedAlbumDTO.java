package dev.kenowi.exportify.snapshot.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SavedAlbumDTO {
    @JsonProperty("saved_at")
    private Instant savedAt;

    @JsonProperty("album")
    private AlbumDTO album;

}


