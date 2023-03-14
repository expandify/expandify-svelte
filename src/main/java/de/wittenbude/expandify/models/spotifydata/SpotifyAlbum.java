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

    public SpotifyAlbum(SavedAlbum savedAlbum) {
        this.albumType = savedAlbum.getAlbum().getAlbumType();
        this.artists = savedAlbum.getAlbum().getArtists();
        this.availableMarkets = savedAlbum.getAlbum().getAvailableMarkets();
        this.copyrights = savedAlbum.getAlbum().getCopyrights();
        this.externalIds = savedAlbum.getAlbum().getExternalIds();
        this.externalUrls = savedAlbum.getAlbum().getExternalUrls();
        this.genres = savedAlbum.getAlbum().getGenres();
        this.href = savedAlbum.getAlbum().getHref();
        this.id = savedAlbum.getAlbum().getId();
        this.images = savedAlbum.getAlbum().getImages();
        this.label = savedAlbum.getAlbum().getLabel();
        this.name = savedAlbum.getAlbum().getName();
        this.popularity = savedAlbum.getAlbum().getPopularity();
        this.releaseDate = savedAlbum.getAlbum().getReleaseDate();
        this.releaseDatePrecision = savedAlbum.getAlbum().getReleaseDatePrecision();
        this.spotifyTracks = null; // TODO
        this.type = savedAlbum.getAlbum().getType();
        this.uri = savedAlbum.getAlbum().getUri();
    }
}
