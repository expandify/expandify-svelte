package de.wittenbude.expandify.models.db;

import de.wittenbude.expandify.models.pojos.Followers;
import de.wittenbude.expandify.models.pojos.Image;
import de.wittenbude.expandify.models.pojos.PlaylistTrack;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "playlists")
public class Playlist {

    @Id
    private String id;
    private Boolean collaborative;
    private String description;
    private Map<String, String> externalUrls;
    private Followers followers;
    private String href;
    private String spotifyId;
    private Image[] images;
    private String name;
    @DocumentReference(lazy = true)
    private SpotifyUserPublic owner;
    private Boolean publicAccess;
    private String snapshotId;
    private List<PlaylistTrack> tracks;
    private Integer totalTracks;
    private ModelObjectType type;
    private String uri;

    public Playlist(se.michaelthelin.spotify.model_objects.specification.Playlist playlist, List<PlaylistTrack> playlistTracks) {
        this.collaborative = playlist.getIsCollaborative();
        this.externalUrls = playlist.getExternalUrls().getExternalUrls();
        this.href = playlist.getHref();
        this.spotifyId = playlist.getId();
        this.images = Arrays.stream(playlist.getImages()).map(Image::new).toArray(Image[]::new);
        this.name = playlist.getName();
        this.owner = new SpotifyUserPublic(playlist.getOwner());
        this.publicAccess = playlist.getIsPublicAccess();
        this.snapshotId = playlist.getSnapshotId();
        this.tracks = playlistTracks;
        this.uri = playlist.getUri();
        this.description = playlist.getDescription();
        this.followers = new Followers(playlist.getFollowers());
        this.totalTracks = playlist.getTracks().getTotal();

        this.id = this.spotifyId + this.snapshotId;
    }
}
