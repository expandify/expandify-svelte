package de.wittenbude.exportify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.embeds.Image;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ImageSchema {
    @JsonProperty("height")
    private final Integer height;

    @JsonProperty("url")
    private final String url;

    @JsonProperty("width")
    private final Integer width;

    public static List<ImageSchema> fromAll(List<Image> images) {
        List<ImageSchema> imageSchemas = new ArrayList<>();
        for (Image image : images) {
            imageSchemas.add(ImageSchema.from(image));
        }
        return imageSchemas;
    }

    public static ImageSchema from(Image image) {
        return ImageSchema
                .builder()
                .url(image.getUrl())
                .width(image.getWidth())
                .height(image.getHeight())
                .build();
    }
}
