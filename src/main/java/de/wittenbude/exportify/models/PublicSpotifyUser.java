package de.wittenbude.exportify.models;

import de.wittenbude.exportify.models.embeds.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class PublicSpotifyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreationTimestamp
    private Instant versionTimestamp;

    private String displayName;
    private Integer followers;
    private String href;
    private String spotifyID;
    private String spotifyObjectType;
    private String uri;


    //@ElementCollection
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Image> images;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;
}
