package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.track.api.Track;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

import static de.wittenbude.exportify.utils.Converters.ifNotNull;

@Getter
@Setter
public class SpotifyTrack extends SpotifyTrackSimplified {

    @JsonProperty("album")
    private SpotifyAlbumSimplified album;

    @JsonProperty("artists")
    private SpotifyArtist[] artists;

    @JsonProperty("external_ids")
    private SpotifyExternalIDs externalIDs;

    @JsonProperty("popularity")
    private Integer popularity;

    public Track convert(String spotifyAlbumID, List<String> spotifyArtistIDs) {
        return new Track()
                .setId(null)
                .setDiscNumber(this.getDiscNumber())
                .setDurationMs(this.getDurationMs())
                .setExplicit(this.getExplicit())
                .setHref(this.getHref())
                .setSpotifyID(this.getId())
                .setIsPlayable(this.getIsPlayable())
                .setRestrictions(ifNotNull(this.getRestrictions(), SpotifyRestrictions::getReason))
                .setName(this.getName())
                .setPreviewUrl(this.getPreviewUrl())
                .setTrackNumber(this.getTrackNumber())
                .setSpotifyObjectType(this.getType().getType())
                .setUri(this.getUri())
                .setIsLocal(this.getIsLocal())
                .setExternalIDs(ifNotNull(this.getExternalIDs(), SpotifyExternalIDs::convert))
                .setPopularity(this.getPopularity())
                .setExternalUrls(this.getExternalUrls())
                .setAvailableMarkets(Arrays.asList(this.getAvailableMarkets()))
                .setSpotifyAlbumID(spotifyAlbumID)
                .setSpotifyArtistIDs(spotifyArtistIDs);
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

        public Track.ExternalIDs convert() {
            return new Track.ExternalIDs()
                    .setEan(this.getEan())
                    .setUpc(this.getUpc())
                    .setIsrc(this.getIsrc());
        }
    }
}
