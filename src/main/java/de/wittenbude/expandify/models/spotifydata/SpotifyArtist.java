package de.wittenbude.expandify.models.spotifydata;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.ExternalUrl;
import se.michaelthelin.spotify.model_objects.specification.Followers;
import se.michaelthelin.spotify.model_objects.specification.Image;

@Data
@NoArgsConstructor
@Document(collection = "artists")
public class SpotifyArtist {

    private ExternalUrl externalUrls;
    private Followers followers;
    private String[] genres;
    private String href;
    @Id
    private String id;
    private Image[] images;
    private String name;
    private Integer popularity;
    private ModelObjectType type;
    private String uri;
}
