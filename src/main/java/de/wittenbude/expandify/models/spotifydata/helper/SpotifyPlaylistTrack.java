package de.wittenbude.expandify.models.spotifydata.helper;

import de.wittenbude.expandify.models.spotifydata.SpotifyTrack;
import de.wittenbude.expandify.models.spotifydata.SpotifyUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Date;

@Data
@NoArgsConstructor
public class SpotifyPlaylistTrack {
    private Date addedAt;
    @DocumentReference(lazy = true)
    private SpotifyUser addedBy;
    private Boolean isLocal;
    @DocumentReference(lazy = true)
    private SpotifyTrack track;

    public SpotifyPlaylistTrack(PlaylistTrack playlistTrack, Track track) {
        this.addedAt = playlistTrack.getAddedAt();
        this.addedBy = new SpotifyUser(playlistTrack.getAddedBy());
        this.isLocal = playlistTrack.getIsLocal();
        this.track = new SpotifyTrack(track);
    }
}
