package de.wittenbude.exportify.models;

import de.wittenbude.exportify.models.embeds.Followers;
import de.wittenbude.exportify.models.embeds.Image;
import de.wittenbude.exportify.models.embeds.ObjectType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;

    @JdbcTypeCode(SqlTypes.JSON)
    private Followers followers;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> genres;

    private String href;

    @Column(unique = true)
    private String spotifyID;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Image> images;

    private String name;

    private Integer popularity;

    private ObjectType objectType;

    private String uri;
}
