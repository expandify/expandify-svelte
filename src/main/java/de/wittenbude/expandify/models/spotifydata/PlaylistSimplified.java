package de.wittenbude.expandify.models.spotifydata;

import de.wittenbude.expandify.models.spotifydata.helper.Image;
import de.wittenbude.expandify.models.spotifydata.helper.PlaylistTrack;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "playlistsSimplified")
public class PlaylistSimplified {

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
    //private String description;
    private Map<String, String> externalUrls;
    //private SpotifyFollowers followers;
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

    public PlaylistSimplified(se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified playlist, List<PlaylistTrack> playlistTracks) {
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

        this.compositeId = new CompositeId(playlist.getId(), playlist.getSnapshotId());
    }
}
