package de.wittenbude.expandify.models.spotifydata;

import de.wittenbude.expandify.models.spotifydata.helper.SpotifyFollowers;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifyImage;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifyPlaylistTrack;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "playlists")
public class SpotifyPlaylist {

    private Boolean collaborative;
    private String description;
    private Map<String, String> externalUrls;
    private SpotifyFollowers followers;
    private String href;
    @Id
    private String id;
    private SpotifyImage[] images;
    private String name;
    @DocumentReference(lazy = true)
    private SpotifyUser owner;
    private Boolean publicAccess;
    private String snapshotId;
    private SpotifyPlaylistTrack[] tracks;
    private ModelObjectType type;
    private String uri;

    public SpotifyPlaylist(Playlist playlist, SpotifyPlaylistTrack[] playlistTracks) {
        this.collaborative = playlist.getIsCollaborative();
        this.description = playlist.getDescription();
        this.externalUrls = playlist.getExternalUrls().getExternalUrls();
        this.followers = new SpotifyFollowers(playlist.getFollowers());
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
