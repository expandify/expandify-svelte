package de.wittenbude.exportify.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SavedAlbumSchema {
    @JsonProperty("saved_at")
    private Instant savedAt;

    @JsonProperty("album")
    private AlbumSchema album;

}


