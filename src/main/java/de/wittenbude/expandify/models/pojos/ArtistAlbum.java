package de.wittenbude.expandify.models.pojos;

import de.wittenbude.expandify.models.db.AlbumInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.AlbumGroup;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistAlbum {
    private AlbumGroup albumGroup;
    @DocumentReference(lazy = true)
    private AlbumInfo album;

/*
    public ArtistAlbum(AlbumInfo album, AlbumGroup albumGroup) {
        this.albumGroup = albumGroup;
        this.album = album;
    }

    public ArtistAlbum(se.michaelthelin.spotify.model_objects.specification.AlbumSimplified albumSimplified) {
        this.albumGroup = albumSimplified.getAlbumGroup();
        this.album = new AlbumInfo(albumSimplified);
    }
*/
}
