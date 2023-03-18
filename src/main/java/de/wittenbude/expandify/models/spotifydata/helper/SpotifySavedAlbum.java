package de.wittenbude.expandify.models.spotifydata.helper;

import de.wittenbude.expandify.models.spotifydata.SpotifyAlbum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotifySavedAlbum {
    private Date addedAt;
    @DocumentReference(lazy = true)
    private SpotifyAlbum album;

    public SpotifySavedAlbum(SpotifyAlbum spotifyAlbum, Date addedAt) {
        this.addedAt = addedAt;
        this.album = spotifyAlbum;
    }
}
