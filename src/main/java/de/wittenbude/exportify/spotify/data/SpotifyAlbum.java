package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.album.api.Album;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

import static de.wittenbude.exportify.utils.Converters.ifNotNull;

@Getter
@Setter
public class SpotifyAlbum extends SpotifyAlbumSimplified {

    @JsonProperty("tracks")
    private SpotifyPage<SpotifyTrackSimplified> tracks;

    @JsonProperty("copyrights")
    private SpotifyCopyright[] copyrights;

    @JsonProperty("externalIDs")
    private SpotifyExternalIDs externalIDs;

    @JsonProperty("genres")
    private String[] genres;

    @JsonProperty("label")
    private String label;

    @JsonProperty("popularity")
    private Integer popularity;

    public Album convert(List<String> spotifyArtistIDs, List<String> spotifyTrackIDs) {
        return new Album()
                .setId(null)
                .setAlbumType(this.getAlbumType().getType())
                .setTotalTracks(this.getTotalTracks())
                .setAvailableMarkets(Arrays.asList(this.getAvailableMarkets()))
                .setExternalUrls(this.getExternalUrls())
                .setHref(this.getHref())
                .setSpotifyID(this.getId())
                .setImages(ifNotNull(this.getImages(), SpotifyImage::convert))
                .setName(this.getName())
                .setPopularity(this.getPopularity())
                .setReleaseDatePrecision(ifNotNull(this.getReleaseDatePrecision(), SpotifyReleaseDatePrecision::convert))
                .setRestrictions(ifNotNull(this.getRestrictions(), SpotifyRestrictions::getReason))
                .setSpotifyObjectType(this.getType().getType())
                .setUri(this.getUri())
                .setSpotifyArtistIDs(spotifyArtistIDs)
                .setSpotifyTrackIDs(spotifyTrackIDs)
                .setCopyrights(ifNotNull(this.getCopyrights(), SpotifyCopyright::convert))
                .setExternalIDs(ifNotNull(this.getExternalIDs(), SpotifyExternalIDs::convert))
                .setGenres(Arrays.asList(this.getGenres()))
                .setLabel(this.getLabel())
                .setPopularity(this.getPopularity());
    }

    @Getter
    @Setter
    public static class SpotifyCopyright {
        @JsonProperty("text")
        private String text;

        @JsonProperty("type")
        private String type;

        public static List<Album.Copyright> convert(SpotifyCopyright[] copyrights) {
            return Arrays
                    .stream(copyrights)
                    .map(SpotifyCopyright::convert)
                    .toList();
        }

        public Album.Copyright convert() {
            return new Album.Copyright()
                    .setText(this.getText())
                    .setType(this.getType());
        }
    }

    @Getter
    @Setter
    public static class SpotifyExternalIDs {

        @JsonProperty("isrc")
        private String isrc;

        @JsonProperty("ean")
        private String ean;

        @JsonProperty("upc")
        private String upc;

        public Album.ExternalIDs convert() {
            return new Album.ExternalIDs()
                    .setEan(this.getEan())
                    .setUpc(this.getUpc())
                    .setIsrc(this.getIsrc());
        }
    }
}
