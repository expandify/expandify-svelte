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
@Document(collection = "spotifyUserPublic")
public class SpotifyUserPublic {
    private String displayName;
    private Map<String, String> externalUrls;
    private Followers followers;
    private String href;
    @Id
    private String id;
    private Image[] images;
    private ModelObjectType type;
    private String uri;

    public SpotifyUserPublic(User user) {
        this.displayName = user.getDisplayName();
        this.externalUrls = user.getExternalUrls().getExternalUrls();
        this.followers = user.getFollowers() != null ? new Followers(user.getFollowers()) : null;
        this.href = user.getHref();
        this.id = user.getId();
        this.images = user.getImages();
        this.type = user.getType();
        this.uri = user.getUri();
    }
}
