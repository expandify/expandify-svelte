package de.wittenbude.exportify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.Artist;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class ArtistSchema {

    @JsonProperty("id")
    private final UUID id;

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
    private final String type;

    @JsonProperty("uri")
    private final String uri;

    public static ArtistSchema from(Artist artist) {
        return ArtistSchema
                .builder()
                .id(artist.getId())
                .externalUrls(artist.getExternalUrls())
                .followers(artist.getFollowers().getTotal())
                .genres(artist.getGenres())
                .href(artist.getHref())
                .spotifyID(artist.getSpotifyID())
                .spotifyImages(ImageSchema.fromAll(artist.getImages()))
                .name(artist.getName())
                .popularity(artist.getPopularity())
                .type(artist.getObjectType().getType())
                .uri(artist.getUri())
                .build();
    }

}
