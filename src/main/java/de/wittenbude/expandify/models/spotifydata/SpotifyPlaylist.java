package de.wittenbude.expandify.models.spotifydata;

import de.wittenbude.expandify.models.spotifydata.helper.Followers;
import de.wittenbude.expandify.models.spotifydata.helper.Image;
import de.wittenbude.expandify.models.spotifydata.helper.PlaylistTrack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.Playlist;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "playlistsSimplified")
public class SpotifyPlaylist {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompositeId {
        private String id;
        private String snapshotId;
    }

    @Id
    private CompositeId compositeId;

    private Boolean collaborative;
    private String description;
    private Map<String, String> externalUrls;
    private Followers followers;
    private String href;
    private String id;
    private Image[] images;
    private String name;
    @DocumentReference(lazy = true)
    private SpotifyUser owner;
    private Boolean publicAccess;
    private String snapshotId;
    private List<PlaylistTrack> tracks;
    private ModelObjectType type;
    private String uri;

    public SpotifyPlaylist(Playlist playlist, List<PlaylistTrack> playlistTracks) {
        this.collaborative = playlist.getIsCollaborative();
        this.externalUrls = playlist.getExternalUrls().getExternalUrls();
        this.href = playlist.getHref();
        this.id = playlist.getId();
        this.images = Arrays.stream(playlist.getImages()).map(Image::new).toArray(Image[]::new);
        this.name = playlist.getName();
        this.owner = new SpotifyUser(playlist.getOwner());
        this.publicAccess = playlist.getIsPublicAccess();
        this.snapshotId = playlist.getSnapshotId();
        this.tracks = playlistTracks;
        this.uri = playlist.getUri();
        this.description = playlist.getDescription();
        this.followers = new Followers(playlist.getFollowers());

        this.compositeId = new CompositeId(playlist.getId(), playlist.getSnapshotId());
    }
}
