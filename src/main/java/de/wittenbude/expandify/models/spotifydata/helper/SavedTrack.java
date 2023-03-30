package de.wittenbude.expandify.models.spotifydata.helper;

import de.wittenbude.expandify.models.spotifydata.Track;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedTrack {
    private Date addedAt;
    @DocumentReference(lazy = true)
    private Track track;

    public SavedTrack(Track track, Date addedAt) {
        this.addedAt = addedAt;
        this.track = track;
    }
}
