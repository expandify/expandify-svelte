package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.Followers;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyFollowers {
    @JsonProperty("href")
    private String href;

    @JsonProperty("total")
    private Integer total;

    public Followers convert() {
        return Followers
                .builder()
                .total(total)
                .href(href)
                .build();
    }
}
