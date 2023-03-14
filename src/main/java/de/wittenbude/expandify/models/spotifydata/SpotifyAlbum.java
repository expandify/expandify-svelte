package de.wittenbude.expandify.models.spotifydata;

import com.neovisionaries.i18n.CountryCode;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.enums.ReleaseDatePrecision;
import se.michaelthelin.spotify.model_objects.specification.*;

@Data
@NoArgsConstructor
@Document(collection = "albums")
public class SpotifyAlbum {
    private AlbumType albumType;
    private ArtistSimplified[] artists;
    private CountryCode[] availableMarkets;
    private Copyright[] copyrights;
    private ExternalId externalIds;
    private ExternalUrl externalUrls;
    private String[] genres;
    private String href;
    @Id
    private String id;
    private Image[] images;
    private String label;
    private String name;
    private Integer popularity;
    private String releaseDate;
    private ReleaseDatePrecision releaseDatePrecision;
    @DocumentReference(lazy = true)
    private SpotifyTrack[] spotifyTracks;
    private ModelObjectType type;
    private String uri;
}
