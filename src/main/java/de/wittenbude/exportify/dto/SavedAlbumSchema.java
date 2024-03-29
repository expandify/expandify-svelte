package de.wittenbude.exportify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.Album;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class SavedAlbumSchema {

    @JsonProperty("saved_at")
    private final Instant savedAt;

    @JsonProperty("album")
    private final Album album;
}


