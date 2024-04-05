package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
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
    public enum SpotifyAlbumType {

        @JsonProperty("album")
        ALBUM,

        @JsonProperty("compilation")
        COMPILATION,

        @JsonProperty("single")
        SINGLE
    }

    @Getter
    public enum SpotifyAlbumGroupType {

        @JsonProperty("album")
        ALBUM,

        @JsonProperty("single")
        SINGLE,

        @JsonProperty("compilation")
        COMPILATION,

        @JsonProperty("appears_on")
        APPEARS_ON
    }
}
