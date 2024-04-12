package dev.kenowi.exportify.authentication.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class ExportifyUser {

    @Id
    @GeneratedValue
    private UUID id;

    private String spotifyUserID;
    //private String googleUserID;

    //@OneToMany(mappedBy = "exportifyUser", orphanRemoval = true)
    //private Set<Snapshot> snapshots;
}
