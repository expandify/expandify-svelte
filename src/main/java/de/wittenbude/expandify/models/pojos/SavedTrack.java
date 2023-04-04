package de.wittenbude.expandify.models.pojos;

import de.wittenbude.expandify.models.db.Track;
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

    public SavedTrack(se.michaelthelin.spotify.model_objects.specification.SavedTrack savedTrack) {
        this.addedAt = savedTrack.getAddedAt();
        this.track = new Track(savedTrack.getTrack());
    }
}
