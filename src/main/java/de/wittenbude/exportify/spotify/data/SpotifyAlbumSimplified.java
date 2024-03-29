package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.album.api.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SpotifyAlbumSimplified {

    @JsonProperty("album_type")
    private SpotifyAlbumType albumType;

    @JsonProperty("total_tracks")
    private Integer totalTracks;

    @JsonProperty("available_markets")
    private CountryCode[] availableMarkets;

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    @JsonProperty("href")
    private String href;

    @JsonProperty("id")
    private String id;

    @JsonProperty("images")
    private SpotifyImage[] images;

    @JsonProperty("name")
    private String name;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("release_date_precision")
    private SpotifyReleaseDatePrecision releaseDatePrecision;

    @JsonProperty("restrictions")
    private SpotifyRestrictions restrictions;

    @JsonProperty("type")
    private SpotifyObjectType type;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("artists")
    private SpotifyArtistSimplified[] artists;

    @JsonProperty("album_group")
    private SpotifyAlbumGroupType albumGroup;


    @Getter
    @AllArgsConstructor
    public enum SpotifyAlbumType {

        @JsonProperty("album")
        ALBUM("album"),

        @JsonProperty("compilation")
        COMPILATION("compilation"),

        @JsonProperty("single")
        SINGLE("single");


        private final String type;
    }

    @Getter
    @AllArgsConstructor
    public enum SpotifyReleaseDatePrecision {

        @JsonProperty("day")
        DAY("day"),

        @JsonProperty("month")
        MONTH("month"),

        @JsonProperty("year")
        YEAR("year");

        private final String precision;

        public Album.ReleaseDatePrecision convert() {
            return switch (this) {
                case DAY -> Album.ReleaseDatePrecision.DAY;
                case MONTH -> Album.ReleaseDatePrecision.MONTH;
                case YEAR -> Album.ReleaseDatePrecision.YEAR;
            };
        }
    }

    @Getter
    @AllArgsConstructor
    public enum SpotifyAlbumGroupType {

        @JsonProperty("album")
        ALBUM("album"),

        @JsonProperty("single")
        SINGLE("single"),

        @JsonProperty("compilation")
        COMPILATION("compilation"),

        @JsonProperty("appears_on")
        APPEARS_ON("appears_on");


        public final String type;
    }

    @Getter
    @Setter
    public static class SpotifyImage {
        @JsonProperty("height")
        private Integer height;

        @JsonProperty("url")
        private String url;

        @JsonProperty("width")
        private Integer width;

        public static List<Album.Image> convert(SpotifyImage[] spotifyImages) {
            return Arrays
                    .stream(spotifyImages)
                    .map(SpotifyImage::convert)
                    .toList();
        }

        public Album.Image convert() {
            return new Album.Image()
                    .setUrl(this.getUrl())
                    .setWidth(this.getWidth())
                    .setHeight(this.getHeight());
        }
    }


}
