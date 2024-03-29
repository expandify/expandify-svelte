package de.wittenbude.exportify.api.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.artist.api.Artist;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Builder
public class ArtistSchema {

    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("version_timestamp")
    private final Instant versionTimestamp;

    @JsonProperty("external_urls")
    private final Map<String, String> externalUrls;

    @JsonProperty("followers")
    private final Integer followers;

    @JsonProperty("genres")
    private final List<String> genres;

    @JsonProperty("href")
    private final String href;

    @JsonProperty("spotify_id")
    private final String spotifyID;

    @JsonProperty("images")
    private final List<ImageSchema> spotifyImages;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("popularity")
    private final Integer popularity;

    @JsonProperty("type")
    private final String spotifyObjectType;

    @JsonProperty("uri")
    private final String uri;

    public static ArtistSchema from(Artist artist) {
        return ArtistSchema
                .builder()
                .id(artist.getId())
                .versionTimestamp(artist.getVersionTimestamp())
                .externalUrls(artist.getExternalUrls())
                .followers(artist.getFollowers())
                .genres(artist.getGenres())
                .href(artist.getHref())
                .spotifyID(artist.getSpotifyID())
                .spotifyImages(ImageSchema.from(artist.getImages()))
                .name(artist.getName())
                .popularity(artist.getPopularity())
                .spotifyObjectType(artist.getSpotifyObjectType())
                .uri(artist.getUri())
                .build();
    }

    public static Collection<ArtistSchema> from(Set<Artist> artists) {
        return artists
                .stream()
                .map(ArtistSchema::from)
                .collect(Collectors.toSet());
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


        public static List<ImageSchema> from(Collection<Artist.Image> images) {
            List<ImageSchema> imageSchemas = new ArrayList<>();
            for (Artist.Image image : images) {
                imageSchemas.add(ImageSchema.from(image));
            }
            return imageSchemas;
        }

        public static ImageSchema from(Artist.Image image) {
            return ImageSchema
                    .builder()
                    .url(image.getUrl())
                    .width(image.getWidth())
                    .height(image.getHeight())
                    .build();
        }
    }
}
