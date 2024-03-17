package de.wittenbude.exportify.db.entity;

import de.wittenbude.exportify.spotify.data.Followers;
import de.wittenbude.exportify.spotify.data.Image;
import de.wittenbude.exportify.spotify.data.SpotifyObjectType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;

    @JdbcTypeCode(SqlTypes.JSON)
    private Followers followers;

    @JdbcTypeCode(SqlTypes.JSON)
    private String[] genres;

    private String href;

    private String spotifyID;

    @JdbcTypeCode(SqlTypes.JSON)
    private Image[] images;

    private String name;

    private Integer popularity;

    private SpotifyObjectType type;

    private String uri;

}
