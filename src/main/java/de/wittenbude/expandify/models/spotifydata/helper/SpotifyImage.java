package de.wittenbude.expandify.models.spotifydata.helper;

import lombok.Data;
import lombok.NoArgsConstructor;
import se.michaelthelin.spotify.model_objects.specification.Image;

@Data
@NoArgsConstructor
public class SpotifyImage {
    private Integer height;
    private String url;
    private Integer width;

    public SpotifyImage(Image image) {
        this.height = image.getHeight();
        this.url = image.getUrl();
        this.width = image.getWidth();
    }
}
