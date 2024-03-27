package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.dto.ImageSchema;
import de.wittenbude.exportify.models.embeds.Image;
import de.wittenbude.exportify.spotify.data.SpotifyImage;

import java.util.ArrayList;
import java.util.List;

public class ImageConverter {


    public static List<ImageSchema> toDTOs(List<Image> images) {
        List<ImageSchema> imageSchemas = new ArrayList<>();
        for (Image image : images) {
            imageSchemas.add(ImageConverter.toDTO(image));
        }
        return imageSchemas;
    }

    public static ImageSchema toDTO(Image image) {
        return ImageSchema
                .builder()
                .url(image.getUrl())
                .width(image.getWidth())
                .height(image.getHeight())
                .build();
    }


    public static List<Image> from(SpotifyImage[] spotifyImages) {
        List<Image> images = new ArrayList<>();
        for (SpotifyImage spotifyImage : spotifyImages) {
            images.add(ImageConverter.from(spotifyImage));
        }
        return images;
    }

    public static Image from(SpotifyImage spotifyImage) {
        return new Image()
                .setUrl(spotifyImage.getUrl())
                .setWidth(spotifyImage.getWidth())
                .setHeight(spotifyImage.getHeight());
    }
}
