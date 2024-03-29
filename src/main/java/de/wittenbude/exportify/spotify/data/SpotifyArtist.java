package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.artist.api.Artist;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

import static de.wittenbude.exportify.utils.Converters.ifNotNull;

@Getter
@Setter
public class SpotifyArtist extends SpotifyArtistSimplified {

    @JsonProperty("followers")
    private SpotifyFollowers spotifyFollowers;

    @JsonProperty("genres")
    private String[] genres;

    @JsonProperty("images")
    private SpotifyImage[] spotifyImages;

    @JsonProperty("popularity")
    private Integer popularity;

    public Artist convert() {
        return new Artist()
                .setId(null)
                .setExternalUrls(this.getExternalUrls())
                .setFollowers(this.getSpotifyFollowers().getTotal())
                .setGenres(Arrays.asList(this.getGenres()))
                .setHref(this.getHref())
                .setSpotifyID(this.getId())
                .setImages(ifNotNull(this.getSpotifyImages(), SpotifyImage::convert))
                .setName(this.getName())
                .setPopularity(this.getPopularity())
                .setSpotifyObjectType(this.getType().getType())
                .setUri(this.getUri());
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

        public static List<Artist.Image> convert(SpotifyImage[] spotifyImages) {
            return Arrays
                    .stream(spotifyImages)
                    .map(SpotifyImage::convert)
                    .toList();
        }

        public Artist.Image convert() {
            return new Artist.Image()
                    .setUrl(this.getUrl())
                    .setWidth(this.getWidth())
                    .setHeight(this.getHeight());
        }
    }
}
