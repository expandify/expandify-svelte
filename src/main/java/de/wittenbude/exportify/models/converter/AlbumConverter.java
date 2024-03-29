package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.dto.AlbumSchema;
import de.wittenbude.exportify.dto.SavedAlbumSchema;
import de.wittenbude.exportify.models.Album;
import de.wittenbude.exportify.models.Snapshot;
import de.wittenbude.exportify.spotify.data.SpotifyAlbum;

import java.util.Arrays;
import java.util.List;

public class AlbumConverter {

    public static Album from(SpotifyAlbum spotifyAlbum, List<String> spotifyArtistIDs, List<String> spotifyTrackIDs) {
        return new Album()
                .setId(null)
                .setAlbumType(spotifyAlbum.getAlbumType().getType())
                .setTotalTracks(spotifyAlbum.getTotalTracks())
                .setAvailableMarkets(Arrays.asList(spotifyAlbum.getAvailableMarkets()))
                .setExternalUrls(spotifyAlbum.getExternalUrls())
                .setHref(spotifyAlbum.getHref())
                .setSpotifyID(spotifyAlbum.getId())
                .setImages(ImageConverter.from(spotifyAlbum.getImages()))
                .setName(spotifyAlbum.getName())
                .setPopularity(spotifyAlbum.getPopularity())
                .setReleaseDatePrecision(EnumConverters.from(spotifyAlbum.getReleaseDatePrecision()))
                .setRestrictions(spotifyAlbum.getRestrictions() != null ? spotifyAlbum.getRestrictions().getReason() : null)
                .setSpotifyObjectType(spotifyAlbum.getType().getType())
                .setUri(spotifyAlbum.getUri())
                .setSpotifyArtistIDs(spotifyArtistIDs)
                .setSpotifyTrackIDs(spotifyTrackIDs)
                .setCopyrights(CopyrightConverter.from(spotifyAlbum.getCopyrights()))
                .setExternalIDs(ExternalIDsConverter.from(spotifyAlbum.getExternalIDs()))
                .setGenres(Arrays.asList(spotifyAlbum.getGenres()))
                .setLabel(spotifyAlbum.getLabel())
                .setPopularity(spotifyAlbum.getPopularity());
    }

    public static AlbumSchema toDTO(Album album) {
        return AlbumSchema
                .builder()
                .albumType(album.getAlbumType())
                .totalTracks(album.getTotalTracks())
                .availableMarkets(album.getAvailableMarkets())
                .externalUrls(album.getExternalUrls())
                .href(album.getHref())
                .spotifyID(album.getSpotifyID())
                .images(ImageConverter.toDTOs(album.getImages()))
                .name(album.getName())
                .popularity(album.getPopularity())
                .releaseDatePrecision(album.getReleaseDatePrecision().name())
                .restrictions(album.getRestrictions())
                .spotifyObjectType(album.getSpotifyObjectType())
                .uri(album.getUri())
                .spotifyArtistIDs(album.getSpotifyArtistIDs())
                .spotifyTrackIDs(album.getSpotifyTrackIDs())
                .copyrights(CopyrightConverter.toDTOs(album.getCopyrights()))
                .externalIDs(ExternalIDsConverter.toDTO(album.getExternalIDs()))
                .genres(album.getGenres())
                .label(album.getLabel())
                .popularity(album.getPopularity())
                .build();
    }

    public static SavedAlbumSchema toDTO(Snapshot.SavedAlbum album) {
        return SavedAlbumSchema
                .builder()
                .savedAt(album.getSavedAt())
                .album(album.getAlbum())
                .build();
    }

}
