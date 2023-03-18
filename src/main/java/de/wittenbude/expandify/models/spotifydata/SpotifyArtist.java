package de.wittenbude.expandify.models.spotifydata;

import de.wittenbude.expandify.models.spotifydata.helper.SpotifyFollowers;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifyImage;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

import java.util.Arrays;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "artists")
public class SpotifyArtist extends SpotifyArtistSimplified {

    private Map<String, String> externalUrls;
    private SpotifyFollowers followers;
    private String[] genres;
    private String href;
    @Id
    private String id;
    private SpotifyImage[] images;
    private String name;
    private Integer popularity;
    private ModelObjectType type;
    private String uri;

    public SpotifyArtist(Artist artist) {
        this.externalUrls = artist.getExternalUrls().getExternalUrls();
        this.followers = new SpotifyFollowers(artist.getFollowers());
        this.genres = artist.getGenres();
        this.href = artist.getHref();
        this.id = artist.getId();
        this.images = Arrays.stream(artist.getImages()).map(SpotifyImage::new).toArray(SpotifyImage[]::new);
        this.name = artist.getName();
        this.popularity = artist.getPopularity();
        this.type = artist.getType();
        this.uri = artist.getUri();
    }
}
