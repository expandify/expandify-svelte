package de.wittenbude.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    private List<CountryCode> availableMarkets;

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    @JsonProperty("href")
    private String href;

    @JsonProperty("id")
    private String id;

    @JsonProperty("images")
    private List<SpotifyImage> images;

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
    private List<SpotifyArtistSimplified> artists;

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


}
