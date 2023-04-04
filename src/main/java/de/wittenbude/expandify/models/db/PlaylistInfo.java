package de.wittenbude.expandify.models.db;

import de.wittenbude.expandify.models.pojos.Image;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.Arrays;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "playlists.infos")
public class PlaylistInfo {

    @Id
    private String id;
    private Boolean collaborative;
    private Map<String, String> externalUrls;
    private String href;
    private String spotifyId;
    private Image[] images;
    private String name;
    @DocumentReference(lazy = true)
    private SpotifyUserPublic owner;
    private Boolean publicAccess;
    private String snapshotId;
    private Integer totalTracks;
    private ModelObjectType type;
    private String uri;

    public PlaylistInfo(se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified playlist) {
        this.collaborative = playlist.getIsCollaborative();
        this.externalUrls = playlist.getExternalUrls().getExternalUrls();
        this.href = playlist.getHref();
        this.spotifyId = playlist.getId();
        this.images = Arrays.stream(playlist.getImages()).map(Image::new).toArray(Image[]::new);
        this.name = playlist.getName();
        this.owner = new SpotifyUserPublic(playlist.getOwner());
        this.publicAccess = playlist.getIsPublicAccess();
        this.snapshotId = playlist.getSnapshotId();
        this.uri = playlist.getUri();
        this.totalTracks = playlist.getTracks().getTotal();

        this.id = this.spotifyId + this.snapshotId;
    }

}
