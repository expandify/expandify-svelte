package de.wittenbude.expandify.models.db;

import de.wittenbude.expandify.models.pojos.SavedAlbum;
import de.wittenbude.expandify.models.pojos.SavedTrack;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "libraries")
public class Library {

    @Id
    private String id;

    private List<SavedAlbum> savedAlbums;

    private List<SavedTrack> savedTracks;

    @DocumentReference(lazy = true)
    private List<Artist> followedArtists;

    @DocumentReference(lazy = true)
    private List<Playlist> playlists;

    private Date date;

    @DocumentReference(lazy = true)
    private SpotifyUserPrivate owner;

    public Library(List<SavedAlbum> savedAlbums,
                   List<SavedTrack> savedTracks,
                   List<Artist> followedArtists,
                   List<Playlist> playlists,
                   SpotifyUserPrivate owner,
                   Date date
    ) {
        this.savedAlbums = savedAlbums;
        this.savedTracks = savedTracks;
        this.followedArtists = followedArtists;
        this.playlists = playlists;
        this.owner = owner;
        this.date = date;
    }

}


