package dev.kenowi.exportify.snapshot.entities;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class PrivateSpotifyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreationTimestamp
    private Instant versionTimestamp;

    //private EventStatus publicUserStatus = EventStatus.PENDING;

    private String spotifyID;
    private CountryCode country;
    private String email;
    private String spotifyProductType;
    private Boolean explicitContentFilterEnabled;
    private Boolean explicitContentFilterLocked;

    private String publicSpotifyUserID;
}

