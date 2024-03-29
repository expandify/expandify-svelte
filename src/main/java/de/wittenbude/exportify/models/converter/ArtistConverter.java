package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.dto.ArtistSchema;
import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.spotify.data.SpotifyArtist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtistConverter {

    public static ArtistSchema toDTO(Artist artist) {
        return ArtistSchema
                .builder()
                .id(artist.getId())
                .versionTimestamp(artist.getVersionTimestamp())
                .externalUrls(artist.getExternalUrls())
                .followers(artist.getFollowers())
                .genres(artist.getGenres())
                .href(artist.getHref())
                .spotifyID(artist.getSpotifyID())
                .spotifyImages(ImageConverter.toDTOs(artist.getImages()))
                .name(artist.getName())
                .popularity(artist.getPopularity())
                .spotifyObjectType(artist.getSpotifyObjectType())
                .uri(artist.getUri())
                .build();
    }

    public static Artist from(SpotifyArtist spotifyArtist) {
        return new Artist()
                .setId(null)
                .setExternalUrls(spotifyArtist.getExternalUrls())
                .setFollowers(spotifyArtist.getSpotifyFollowers().getTotal())
                .setGenres(Arrays.asList(spotifyArtist.getGenres()))
                .setHref(spotifyArtist.getHref())
                .setSpotifyID(spotifyArtist.getId())
                .setImages(ImageConverter.from(spotifyArtist.getSpotifyImages()))
                .setName(spotifyArtist.getName())
                .setPopularity(spotifyArtist.getPopularity())
                .setSpotifyObjectType(spotifyArtist.getType().getType())
                .setUri(spotifyArtist.getUri());
    }

    public static Set<Artist> from(SpotifyArtist[] spotifyArtists) {
        return Arrays
                .stream(spotifyArtists)
                .map(ArtistConverter::from)
                .collect(Collectors.toSet());
    }

    public static Collection<ArtistSchema> toDTOs(Set<Artist> artists) {
        return artists
                .stream()
                .map(ArtistConverter::toDTO)
                .toList();
    }
}
