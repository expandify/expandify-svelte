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
public class PublicUser {
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

    private ObjectType objectType;

    private String uri;
}
