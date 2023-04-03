package de.wittenbude.expandify.models.spotifydata;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.expandify.models.spotifydata.helper.Followers;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.enums.ProductType;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "spotifyUserPrivate")
public class SpotifyUserPrivate {
    private String birthdate;
    private CountryCode country;
    private String displayName;
    private String email;
    private Map<String, String> externalUrls;
    private Followers followers;
    private String href;
    @Id
    private String id;
    private Image[] images;
    private ProductType product;
    private ModelObjectType type;
    private String uri;

    public SpotifyUserPrivate(User user) {
        this.birthdate = user.getBirthdate();
        this.country = user.getCountry();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
        this.externalUrls = user.getExternalUrls().getExternalUrls();
        this.followers = user.getFollowers() != null ? new Followers(user.getFollowers()) : null;
        this.href = user.getHref();
        this.id = user.getId();
        this.images = user.getImages();
        this.product = user.getProduct();
        this.type = user.getType();
        this.uri = user.getUri();
    }
}
