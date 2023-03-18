package de.wittenbude.expandify.models.spotifydata;

import de.wittenbude.expandify.models.spotifydata.helper.SpotifySavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifySavedTrack;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "libraries")
public class SpotifyLibrary {

    @Id
    private String id;
    private boolean latest;
    private List<SpotifySavedAlbum> savedAlbums;
    private List<SpotifySavedTrack> savedTracks;
    @DocumentReference(lazy = true)
    private List<SpotifyArtist> followedArtists;
    @DocumentReference(lazy = true)
    private List<SpotifyPlaylistSimplified> playlists;
    private Date date;
    @DocumentReference(lazy = true)
    private SpotifyUser owner;


    public SpotifyLibrary(boolean latest, Date date, SpotifyUser owner) {
        this.latest = latest;
        this.date = date;
        this.owner = owner;
    }
}


