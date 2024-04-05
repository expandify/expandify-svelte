package dev.kenowi.exportify.domain.service.track;

import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.utils.CollectionEquality;

import java.util.Objects;

class TrackEquality {

    public static boolean equals(Track a, Track b) {

        if (a.getId() != null ? a.getId().equals(b.getId()) : b.getId() != null) {
            return true;
        }

        if (!Objects.equals(a.getDiscNumber(), b.getDiscNumber())) return false;
        if (!Objects.equals(a.getDurationMs(), b.getDurationMs())) return false;
        if (!Objects.equals(a.getExplicit(), b.getExplicit())) return false;
        if (!Objects.equals(a.getHref(), b.getHref())) return false;
        if (!Objects.equals(a.getSpotifyID(), b.getSpotifyID())) return false;
        if (!Objects.equals(a.getIsPlayable(), b.getIsPlayable())) return false;
        if (!Objects.equals(a.getRestrictions(), b.getRestrictions())) return false;
        if (!Objects.equals(a.getName(), b.getName())) return false;
        if (!Objects.equals(a.getPreviewUrl(), b.getPreviewUrl())) return false;
        if (!Objects.equals(a.getTrackNumber(), b.getTrackNumber())) return false;
        if (!Objects.equals(a.getSpotifyObjectType(), b.getSpotifyObjectType())) return false;
        if (!Objects.equals(a.getUri(), b.getUri())) return false;
        if (!Objects.equals(a.getIsLocal(), b.getIsLocal())) return false;
        if (!Objects.equals(a.getExternalIDs(), b.getExternalIDs())) return false;
        if (!Objects.equals(a.getPopularity(), b.getPopularity())) return false;
        if (!Objects.equals(a.getSpotifyAlbumID(), b.getSpotifyAlbumID())) return false;
        if (!CollectionEquality.equals(a.getExternalUrls(), b.getExternalUrls())) return false;
        if (!CollectionEquality.equals(a.getAvailableMarkets(), b.getAvailableMarkets())) return false;

        return CollectionEquality.equals(a.getSpotifyArtistIDs(), b.getSpotifyArtistIDs());
    }
}
