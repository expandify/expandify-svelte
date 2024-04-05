package dev.kenowi.exportify.domain.entities;

import dev.kenowi.exportify.domain.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.valueobjects.SavedTrack;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Snapshot {

    @Id
    @GeneratedValue
    private UUID id;

    @CreationTimestamp
    private Instant snapshotDate;

    @ManyToOne
    @JoinColumn
    private ExportifyUser exportifyUser;

    @ManyToMany
    @JoinTable
    private Set<Artist> artists;

    @ElementCollection
    private Set<SavedTrack> savedTracks;

    @ElementCollection
    private Set<SavedAlbum> savedAlbums;

    @ManyToMany
    @JoinTable
    private Set<Playlist> playlists;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Snapshot snapshot = (Snapshot) o;
        return getId() != null && Objects.equals(getId(), snapshot.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }


}
