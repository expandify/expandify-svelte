package de.wittenbude.expandify.models.spotifydata.helper;

import de.wittenbude.expandify.models.spotifydata.SpotifyTrack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotifySavedTrack {
    private Date addedAt;
    @DocumentReference(lazy = true)
    private SpotifyTrack track;

    public SpotifySavedTrack(SpotifyTrack track, Date addedAt) {
        this.addedAt = addedAt;
        this.track = track;
    }
}
