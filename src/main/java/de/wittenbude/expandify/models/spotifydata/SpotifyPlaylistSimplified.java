package de.wittenbude.expandify.models.spotifydata;

import de.wittenbude.expandify.models.spotifydata.helper.SpotifyImage;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifyPlaylistTrack;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "playlists")
public class SpotifyPlaylistSimplified {

    private Boolean collaborative;
    //private String description;
    private Map<String, String> externalUrls;
    //private SpotifyFollowers followers;
    private String href;
    @Id
    private String id;
    private SpotifyImage[] images;
    private String name;
    @DocumentReference(lazy = true)
    private SpotifyUser owner;
    private Boolean publicAccess;
    private String snapshotId;
    private List<SpotifyPlaylistTrack> tracks;
    private ModelObjectType type;
    private String uri;

    public SpotifyPlaylistSimplified(PlaylistSimplified playlist, List<SpotifyPlaylistTrack> playlistTracks) {
        this.collaborative = playlist.getIsCollaborative();
        this.externalUrls = playlist.getExternalUrls().getExternalUrls();
        this.href = playlist.getHref();
        this.id = playlist.getId();
        this.images = Arrays.stream(playlist.getImages()).map(SpotifyImage::new).toArray(SpotifyImage[]::new);
        this.name = playlist.getName();
        this.owner = new SpotifyUser(playlist.getOwner());
        this.publicAccess = playlist.getIsPublicAccess();
        this.snapshotId = playlist.getSnapshotId();
        this.tracks = playlistTracks;
        this.uri = playlist.getUri();
    }
}
