package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.dto.ArtistSchema;
import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.spotify.data.SpotifyArtist;

import java.util.Arrays;

public class ArtistConverter {

    public static ArtistSchema toDTO(Artist artist) {
        return ArtistSchema
                .builder()
                .id(artist.getId())
                .externalUrls(artist.getExternalUrls())
                .followers(artist.getFollowers().getTotal())
                .genres(artist.getGenres())
                .href(artist.getHref())
                .spotifyID(artist.getSpotifyID())
                .spotifyImages(ImageConverter.toDTOs(artist.getImages()))
                .name(artist.getName())
                .popularity(artist.getPopularity())
                .type(artist.getObjectType().getType())
                .uri(artist.getUri())
                .build();
    }

    public static Artist from(SpotifyArtist spotifyArtist) {
        return new Artist()
                .setId(null)
                .setExternalUrls(spotifyArtist.getExternalUrls())
                .setFollowers(FollowersConverter.from(spotifyArtist.getSpotifyFollowers()))
                .setGenres(Arrays.asList(spotifyArtist.getGenres()))
                .setHref(spotifyArtist.getHref())
                .setSpotifyID(spotifyArtist.getId())
                .setImages(ImageConverter.from(spotifyArtist.getSpotifyImages()))
                .setName(spotifyArtist.getName())
                .setPopularity(spotifyArtist.getPopularity())
                .setObjectType(TypeConverter.from(spotifyArtist.getType()))
                .setUri(spotifyArtist.getUri());
    }

}
