package de.wittenbude.expandify.models.spotifydata;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "libraries")
public class SpotifyLibrary {

    @Id
    private String id;
    private SavedAlbum[] savedAlbums;
    private SavedTrack[] savedTracks;
    @DocumentReference(lazy = true)
    private SpotifyArtist[] spotifyArtists;
    @DocumentReference(lazy = true)
    private SpotifyPlaylist[] spotifyPlaylists;
    private Date date;
    @DocumentReference(lazy = true)
    private SpotifyUser owner;


    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @EqualsAndHashCode
    private class SavedAlbum {
        private Date addedAt;
        @DocumentReference(lazy = true)
        private SpotifyAlbum albums;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @EqualsAndHashCode
    private class SavedTrack {
        private Date addedAt;
        @DocumentReference(lazy = true)
        private SpotifyTrack spotifyTrack;
    }
}


