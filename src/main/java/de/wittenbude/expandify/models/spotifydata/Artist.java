package de.wittenbude.expandify.models.spotifydata;

import de.wittenbude.expandify.models.spotifydata.helper.Followers;
import de.wittenbude.expandify.models.spotifydata.helper.Image;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.Arrays;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "artists")
public class Artist {

    private Map<String, String> externalUrls;
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

    public Artist(se.michaelthelin.spotify.model_objects.specification.Artist artist) {
        this.externalUrls = artist.getExternalUrls().getExternalUrls();
        this.followers = new Followers(artist.getFollowers());
        this.genres = artist.getGenres();
        this.href = artist.getHref();
        this.id = artist.getId();
        this.images = Arrays.stream(artist.getImages()).map(Image::new).toArray(Image[]::new);
        this.name = artist.getName();
        this.popularity = artist.getPopularity();
        this.type = artist.getType();
        this.uri = artist.getUri();
    }
}
