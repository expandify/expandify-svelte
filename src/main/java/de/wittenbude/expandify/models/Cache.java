package de.wittenbude.expandify.models;

import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.SpotifyUser;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "cache")
public class Cache {


    @Id
    private String id;

    private List<SavedAlbum> savedAlbums = new ArrayList<>();

    private List<SavedTrack> savedTracks = new ArrayList<>();

    @DocumentReference(lazy = true)
    private List<Artist> followedArtists = new ArrayList<>();

    @DocumentReference(lazy = true)
    private List<PlaylistSimplified> playlists = new ArrayList<>();

}


