package de.wittenbude.exportify.domain.entities;

import de.wittenbude.exportify.domain.valueobjects.Image;
import de.wittenbude.exportify.domain.valueobjects.SpotifyObjectType;
import jakarta.persistence.*;
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
    private String uri;

    @Enumerated(EnumType.STRING)
    private SpotifyObjectType spotifyObjectType;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Image> images;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;

}
