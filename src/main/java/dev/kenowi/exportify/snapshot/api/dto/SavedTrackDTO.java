package dev.kenowi.exportify.snapshot.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SavedTrackDTO {
    @JsonProperty("saved_at")
    private Instant savedAt;

    @JsonProperty("album")
    private TrackDTO track;
}


