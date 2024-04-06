package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SpotifySavedTrack {

    @JsonProperty("added_at")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date addedAt;


    @JsonProperty("track")
    private SpotifyTrack track;
}
