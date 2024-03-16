package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CursorPage<T> {

    @Getter
    @Setter
    public static class Cursors {
        @JsonProperty("after")
        private String after;

        @JsonProperty("before")
        private String before;
    }

    @JsonProperty("href")
    private String href;

    @JsonProperty("limit")
    private int limit;

    @JsonProperty("next")
    private String next;

    @JsonProperty("cursors")
    private Cursors cursors;

    @JsonProperty("total")
    private int total;

    @JsonProperty("items")
    private List<T> items;
}
