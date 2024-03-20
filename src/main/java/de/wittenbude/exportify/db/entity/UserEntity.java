package de.wittenbude.exportify.db.entity;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.models.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    private String displayName;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;

    @JdbcTypeCode(SqlTypes.JSON)
    private Followers followers;

    private String href;

    @Column(unique = true)
    private String spotifyID;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Image> images;

    private SpotifyObjectType type;

    private String uri;

    private CountryCode country;

    private String email;

    @JdbcTypeCode(SqlTypes.JSON)
    private ExplicitContent explicitContent;

    private ProductType product;


    public static UserEntity from(PrivateUser privateUser) {
        return UserEntity
                .builder()
                .id(privateUser.getId())
                .displayName(privateUser.getDisplayName())
                .externalUrls(privateUser.getExternalUrls())
                .followers(privateUser.getFollowers())
                .href(privateUser.getHref())
                .spotifyID(privateUser.getSpotifyID())
                .images(privateUser.getImages())
                .type(privateUser.getType())
                .uri(privateUser.getUri())
                .country(privateUser.getCountry())
                .email(privateUser.getEmail())
                .explicitContent(privateUser.getExplicitContent())
                .product(privateUser.getProduct())
                .build();
    }

    public PrivateUser convert() {
        return PrivateUser
                .builder()
                .id(id)
                .displayName(displayName)
                .externalUrls(externalUrls)
                .followers(followers)
                .href(href)
                .spotifyID(spotifyID)
                .images(images)
                .type(type)
                .uri(uri)
                .country(country)
                .email(email)
                .explicitContent(explicitContent)
                .product(product)
                .build();
    }

}

