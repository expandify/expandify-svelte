package de.wittenbude.expandify.models;

import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.SpotifyUserPrivate;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
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
    private List<PlaylistSimplified> playlists;

    private Date date;

    @DocumentReference(lazy = true)
    private SpotifyUserPrivate owner;


    public Library(Date date, SpotifyUserPrivate owner) {
        this.date = date;
        this.owner = owner;
    }

    public Library(List<SavedAlbum> savedAlbums,
                   List<SavedTrack> savedTracks,
                   List<Artist> followedArtists,
                   List<PlaylistSimplified> playlists,
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


