package de.wittenbude.exportify.api.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.album.api.Album;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.*;

@Getter
@Builder
class AlbumSchema {

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

    public static AlbumSchema from(Album album) {
        return AlbumSchema
                .builder()
                .albumType(album.getAlbumType())
                .totalTracks(album.getTotalTracks())
                .availableMarkets(album.getAvailableMarkets())
                .externalUrls(album.getExternalUrls())
                .href(album.getHref())
                .spotifyID(album.getSpotifyID())
                .images(ImageSchema.from(album.getImages()))
                .name(album.getName())
                .popularity(album.getPopularity())
                .releaseDatePrecision(album.getReleaseDatePrecision().name())
                .restrictions(album.getRestrictions())
                .spotifyObjectType(album.getSpotifyObjectType())
                .uri(album.getUri())
                .spotifyArtistIDs(album.getSpotifyArtistIDs())
                .spotifyTrackIDs(album.getSpotifyTrackIDs())
                .copyrights(CopyrightSchema.from(album.getCopyrights()))
                .externalIDs(ExternalIDsSchema.from(album.getExternalIDs()))
                .genres(album.getGenres())
                .label(album.getLabel())
                .popularity(album.getPopularity())
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

        public static ExternalIDsSchema from(Album.ExternalIDs externalIDs) {
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

    @Getter
    @Builder
    public static class CopyrightSchema {

        @JsonProperty("text")
        private String text;

        @JsonProperty("type")
        private String type;

        public static CopyrightSchema from(Album.Copyright copyright) {
            return CopyrightSchema
                    .builder()
                    .text(copyright.getText())
                    .type(copyright.getType())
                    .build();
        }

        public static Collection<CopyrightSchema> from(Collection<Album.Copyright> copyrights) {
            return copyrights
                    .stream()
                    .map(CopyrightSchema::from)
                    .toList();
        }
    }

    @Getter
    @Builder
    public static class ImageSchema {
        @JsonProperty("height")
        private final Integer height;

        @JsonProperty("url")
        private final String url;

        @JsonProperty("width")
        private final Integer width;


        public static List<ImageSchema> from(Collection<Album.Image> images) {
            List<ImageSchema> imageSchemas = new ArrayList<>();
            for (Album.Image image : images) {
                imageSchemas.add(ImageSchema.from(image));
            }
            return imageSchemas;
        }

        public static ImageSchema from(Album.Image image) {
            return ImageSchema
                    .builder()
                    .url(image.getUrl())
                    .width(image.getWidth())
                    .height(image.getHeight())
                    .build();
        }
    }

}


