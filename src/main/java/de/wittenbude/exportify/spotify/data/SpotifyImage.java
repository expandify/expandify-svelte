package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.embeds.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SpotifyImage {
    @JsonProperty("height")
    private Integer height;

    @JsonProperty("url")
    private String url;

    @JsonProperty("width")
    private Integer width;

    public static List<Image> convertAll(SpotifyImage[] spotifyImages) {
        List<Image> images = new ArrayList<>();
        for (SpotifyImage spotifyImage : spotifyImages) {
            images.add(spotifyImage.convert());
        }
        return images;
    }

    public Image convert() {
        return new Image()
                .setUrl(url)
                .setWidth(width)
                .setHeight(height);
    }
}
