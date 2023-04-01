package de.wittenbude.expandify.models.spotifydata.helper;

import de.wittenbude.expandify.models.spotifydata.Album;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

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

    public SavedAlbum(se.michaelthelin.spotify.model_objects.specification.SavedAlbum savedAlbum) {
        this.addedAt = savedAlbum.getAddedAt();
        this.album = new Album(savedAlbum.getAlbum());
    }
}
