package de.wittenbude.exportify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Builder
public class SnapshotSchema {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("artists")
    private Collection<ArtistSchema> artists;
}
