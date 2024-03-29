package de.wittenbude.exportify.api.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.track.api.Track;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class TrackSchema {


    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("version_timestamp")
    private final Instant versionTimestamp;

    @JsonProperty("disc_number")
    private final Integer discNumber;

    @JsonProperty("duration_ms")
    private final Integer durationMs;

    @JsonProperty("explicit")
    private final Boolean explicit;

    @JsonProperty("href")
    private final String href;

    @JsonProperty("spotify_id")
    private final String spotifyID;

    @JsonProperty("is_playable")
    private final Boolean isPlayable;

    @JsonProperty("restrictions")
    private final String restrictions;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("preview_url")
    private final String previewUrl;

    @JsonProperty("track_number")
    private final Integer trackNumber;

    @JsonProperty("spotify_object_type")
    private final String spotifyObjectType;

    @JsonProperty("uri")
    private final String uri;

    @JsonProperty("is_local")
    private final Boolean isLocal;

    @JsonProperty("external_ids")
    private final ExternalIDsSchema externalIDs;

    @JsonProperty("popularity")
    private final Integer popularity;

    @JsonProperty("external_urls")
    private final Map<String, String> externalUrls;

    @JsonProperty("available_markets")
    private final List<CountryCode> availableMarkets;

    @JsonProperty("spotify_album_id")
    private final String spotifyAlbumID;

    @JsonProperty("spotify_artist_ids")
    private final List<String> spotifyArtistIDs;

    public static TrackSchema from(Track track) {
        return TrackSchema
                .builder()
                .id(track.getId())
                .versionTimestamp(track.getVersionTimestamp())
                .discNumber(track.getDiscNumber())
                .durationMs(track.getDurationMs())
                .explicit(track.getExplicit())
                .href(track.getHref())
                .spotifyID(track.getSpotifyID())
                .isPlayable(track.getIsPlayable())
                .restrictions(track.getRestrictions())
                .name(track.getName())
                .previewUrl(track.getPreviewUrl())
                .trackNumber(track.getTrackNumber())
                .spotifyObjectType(track.getSpotifyObjectType())
                .uri(track.getUri())
                .isLocal(track.getIsLocal())
                .externalIDs(ExternalIDsSchema.from(track.getExternalIDs()))
                .popularity(track.getPopularity())
                .externalUrls(track.getExternalUrls())
                .availableMarkets(track.getAvailableMarkets())
                .spotifyAlbumID(track.getSpotifyAlbumID())
                .spotifyArtistIDs(track.getSpotifyArtistIDs())
                .build();
    }

    @Getter
    @Builder
    public static class ExternalIDsSchema {

        @JsonProperty("isrc")
        private final String isrc;

        @JsonProperty("ean")
        private final String ean;

        @JsonProperty("upc")
        private final String upc;

        public static ExternalIDsSchema from(Track.ExternalIDs externalIDs) {
            if (externalIDs == null) {
                return null;
            }
            return ExternalIDsSchema
                    .builder()
                    .ean(externalIDs.getEan())
                    .upc(externalIDs.getUpc())
                    .isrc(externalIDs.getIsrc())
                    .build();
        }
    }
}