package de.wittenbude.expandify.models.spotifydata.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Image {
    private Integer height;
    private String url;
    private Integer width;

    public Image(se.michaelthelin.spotify.model_objects.specification.Image image) {
        this.height = image.getHeight();
        this.url = image.getUrl();
        this.width = image.getWidth();
    }
}
