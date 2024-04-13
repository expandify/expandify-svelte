package dev.kenowi.exportify.spotify.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SpotifySavedAlbum {

    @JsonProperty("added_at")
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date addedAt;

    @JsonProperty("album")
    private SpotifyAlbum album;
}
