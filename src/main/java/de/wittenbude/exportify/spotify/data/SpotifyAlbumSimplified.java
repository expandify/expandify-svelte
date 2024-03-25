package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

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

}
