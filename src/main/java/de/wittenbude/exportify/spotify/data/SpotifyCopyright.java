package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyCopyright {
    @JsonProperty("text")
    private String text;

    @JsonProperty("type")
    private Type type;

    @Getter
    @AllArgsConstructor
    public enum Type {
        @JsonProperty("c")
        C("c"),

        @JsonProperty("p")
        P("p");


        public final String type;
    }
}
