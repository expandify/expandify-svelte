package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.embeds.Followers;
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
        return new Followers()
                .setTotal(total)
                .setHref(href);
    }
}
