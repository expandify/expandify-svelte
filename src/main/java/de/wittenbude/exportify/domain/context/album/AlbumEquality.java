package de.wittenbude.exportify.domain.context.album;

import de.wittenbude.exportify.domain.entities.Album;
import de.wittenbude.exportify.domain.utils.CollectionEquality;

import java.util.Objects;

class AlbumEquality {
    public static boolean equals(Album a, Album b) {

        if (a.getId() != null ? a.getId().equals(b.getId()) : b.getId() != null) {
            return true;
        }

        if (!Objects.equals(a.getAlbumType(), b.getAlbumType())) return false;
        if (!Objects.equals(a.getTotalTracks(), b.getTotalTracks())) return false;
        if (!Objects.equals(a.getHref(), b.getHref())) return false;
        if (!Objects.equals(a.getSpotifyID(), b.getSpotifyID())) return false;
        if (!Objects.equals(a.getName(), b.getName())) return false;
        if (!Objects.equals(a.getReleaseDate(), b.getReleaseDate())) return false;
        if (!Objects.equals(a.getReleaseDatePrecision(), b.getReleaseDatePrecision())) return false;
        if (!Objects.equals(a.getRestrictions(), b.getRestrictions())) return false;
        if (!Objects.equals(a.getSpotifyObjectType(), b.getSpotifyObjectType())) return false;
        if (!Objects.equals(a.getUri(), b.getUri())) return false;
        if (!Objects.equals(a.getExternalIDs(), b.getExternalIDs())) return false;
        if (!Objects.equals(a.getLabel(), b.getLabel())) return false;
        if (!Objects.equals(a.getPopularity(), b.getPopularity())) return false;
        if (!CollectionEquality.equals(a.getCopyrights(), b.getCopyrights())) return false;
        if (!CollectionEquality.equals(a.getImages(), b.getImages())) return false;
        if (!CollectionEquality.equals(a.getAvailableMarkets(), b.getAvailableMarkets())) return false;
        if (!CollectionEquality.equals(a.getExternalUrls(), b.getExternalUrls())) return false;
        if (!CollectionEquality.equals(a.getGenres(), b.getGenres())) return false;
        if (!CollectionEquality.equals(a.getSpotifyArtistIDs(), b.getSpotifyArtistIDs())) return false;

        return Objects.equals(a.getSpotifyTrackIDs(), b.getSpotifyTrackIDs());
    }
}
