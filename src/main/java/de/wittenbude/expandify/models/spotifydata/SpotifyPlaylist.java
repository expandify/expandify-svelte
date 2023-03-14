package de.wittenbude.expandify.models.spotifydata;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.ExternalUrl;
import se.michaelthelin.spotify.model_objects.specification.Followers;
import se.michaelthelin.spotify.model_objects.specification.Image;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "playlists")
public class SpotifyPlaylist {

    private Boolean collaborative;
    private String description;
    private ExternalUrl externalUrls;
    private Followers followers;
    private String href;
    @Id
    private String id;
    private Image[] images;
    private String name;
    @DocumentReference(lazy = true)
    private SpotifyUser owner;
    private Boolean publicAccess;
    private String snapshotId;
    private PlaylistTrack[] tracks;
    private ModelObjectType type;
    private String uri;

    private class PlaylistTrack {
        private Date addedAt;
        @DocumentReference(lazy = true)
        private SpotifyUser addedBy;
        private Boolean isLocal;
        @DocumentReference(lazy = true)
        private SpotifyTrack spotifyTrack;
    }
}
