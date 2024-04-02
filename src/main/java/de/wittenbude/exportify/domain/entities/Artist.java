package de.wittenbude.exportify.domain.entities;

import de.wittenbude.exportify.domain.valueobjects.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Artist {
    @Id
    @GeneratedValue
    private UUID id;

    @CreationTimestamp
    private Instant versionTimestamp;

    private Integer followers;
    private String href;
    private String spotifyID;
    private String name;
    private Integer popularity;
    private String spotifyObjectType;
    private String uri;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Image> images;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> genres;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Artist artist = (Artist) o;
        return getId() != null && Objects.equals(getId(), artist.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
