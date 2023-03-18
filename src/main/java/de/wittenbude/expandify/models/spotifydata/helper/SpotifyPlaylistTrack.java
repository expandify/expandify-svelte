package de.wittenbude.expandify.models.spotifydata.helper;

import de.wittenbude.expandify.models.spotifydata.SpotifyUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
public class SpotifyPlaylistTrack {
    private Date addedAt;
    @DocumentReference(lazy = true)
    private SpotifyUser addedBy;
    private Boolean isLocal;
    private Integer durationMs;
    private Map<String, String> externalUrls;
    private String href;
    @Id
    private String id;
    private String name;

    private ModelObjectType type;
    private String uri;



    public SpotifyPlaylistTrack(PlaylistTrack playlistTrack) {
        this.addedAt = playlistTrack.getAddedAt();
        this.addedBy = new SpotifyUser(playlistTrack.getAddedBy());
        this.isLocal = playlistTrack.getIsLocal();
        this.durationMs = playlistTrack.getTrack().getDurationMs();
        this.externalUrls = playlistTrack.getTrack().getExternalUrls().getExternalUrls();
        this.href = playlistTrack.getTrack().getHref();
        this.id = playlistTrack.getTrack().getId();
        this.name = playlistTrack.getTrack().getName();
        this.type = playlistTrack.getTrack().getType();
        this.uri = playlistTrack.getTrack().getUri();
    }
}
