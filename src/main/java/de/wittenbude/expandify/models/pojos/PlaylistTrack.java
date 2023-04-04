package de.wittenbude.expandify.models.pojos;

import de.wittenbude.expandify.models.db.Episode;
import de.wittenbude.expandify.models.db.SpotifyUserPublic;
import de.wittenbude.expandify.models.db.Track;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
public class PlaylistTrack {
    private Date addedAt;
    @DocumentReference(lazy = true)
    private SpotifyUserPublic addedBy;

    private Boolean isLocal;
    private Integer durationMs;
    private Map<String, String> externalUrls;
    private String href;
    @Id
    private String id;
    private String name;
    private ModelObjectType type;
    private String uri;

    @DocumentReference(lazy = true)
    private Track track;
    @DocumentReference(lazy = true)
    private Episode episode;



    public PlaylistTrack(se.michaelthelin.spotify.model_objects.specification.PlaylistTrack playlistTrack) {
        this.addedAt = playlistTrack.getAddedAt();
        this.addedBy = new SpotifyUserPublic(playlistTrack.getAddedBy());
        this.isLocal = playlistTrack.getIsLocal();
        this.durationMs = playlistTrack.getTrack().getDurationMs();
        this.externalUrls = playlistTrack.getTrack().getExternalUrls().getExternalUrls();
        this.href = playlistTrack.getTrack().getHref();
        this.id = playlistTrack.getTrack().getId();
        this.name = playlistTrack.getTrack().getName();
        this.type = playlistTrack.getTrack().getType();
        this.uri = playlistTrack.getTrack().getUri();

        if (playlistTrack.getTrack().getType() == ModelObjectType.TRACK) {
            this.track = new Track((se.michaelthelin.spotify.model_objects.specification.Track) playlistTrack.getTrack());
        }

        if (playlistTrack.getTrack().getType() == ModelObjectType.EPISODE) {
            this.episode = new Episode((se.michaelthelin.spotify.model_objects.specification.Episode) playlistTrack.getTrack());
        }
    }
}
