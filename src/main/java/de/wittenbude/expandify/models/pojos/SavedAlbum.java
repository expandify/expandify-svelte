package de.wittenbude.expandify.models.pojos;

import de.wittenbude.expandify.models.db.Album;
import de.wittenbude.expandify.models.db.TrackInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedAlbum {
    private Date addedAt;
    @DocumentReference(lazy = true)
    private Album album;

    public SavedAlbum(Album album, Date addedAt) {
        this.addedAt = addedAt;
        this.album = album;
    }

    public SavedAlbum(se.michaelthelin.spotify.model_objects.specification.SavedAlbum savedAlbum, List<TrackInfo> tracks) {
        this.addedAt = savedAlbum.getAddedAt();
        this.album = new Album(savedAlbum.getAlbum(), tracks);
    }
}
