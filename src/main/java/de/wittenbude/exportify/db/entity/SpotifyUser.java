package de.wittenbude.exportify.db.entity;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.spotify.data.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotifyUser {
    private String displayName;
    private Map<String, String> externalUrls;
    private Followers followers;
    private String href;
    private String id;
    private List<Image> images;
    private SpotifyObjectType type;
    private String uri;
    private CountryCode country;
    private String email;
    private ExplicitContent explicitContent;
    private ProductType product;
}
