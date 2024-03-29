package de.wittenbude.exportify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class AlbumSchema {

    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("version_timestamp")
    private final Instant versionTimestamp;

    @JsonProperty("album_type")
    private final String albumType;

    @JsonProperty("total_tracks")
    private final Integer totalTracks;

    @JsonProperty("href")
    private final String href;

    @JsonProperty("spotify_id")
    private final String spotifyID;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("release_date")
    private final String releaseDate;

    @JsonProperty("release_date_precision")
    private final String releaseDatePrecision;

    @JsonProperty("restrictions")
    private final String restrictions;

    @JsonProperty("spotify_object_type")
    private final String spotifyObjectType;

    @JsonProperty("uri")
    private final String uri;

    @JsonProperty("external_ids")
    private final ExternalIDsSchema externalIDs;

    @JsonProperty("label")
    private final String label;

    @JsonProperty("popularity")
    private final Integer popularity;

    @JsonProperty("copyrights")
    private final Collection<CopyrightSchema> copyrights;

    @JsonProperty("images")
    private final Collection<ImageSchema> images;

    @JsonProperty("available_markets")
    private final Collection<CountryCode> availableMarkets;

    @JsonProperty("external_urls")
    private final Map<String, String> externalUrls;

    @JsonProperty("genres")
    private final Collection<String> genres;

    @JsonProperty("spotify_artist_ids")
    private final List<String> spotifyArtistIDs;

    @JsonProperty("spotify_track_ids")
    private final List<String> spotifyTrackIDs;
}


