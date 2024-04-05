package dev.kenowi.exportify.domain.service.playlist;

import dev.kenowi.exportify.domain.entities.Playlist;
import dev.kenowi.exportify.domain.utils.CollectionEquality;

import java.util.Objects;

class PlaylistEquality {

    public static boolean equals(Playlist a, Playlist b) {

        if (a.getId() != null ? a.getId().equals(b.getId()) : b.getId() != null) {
            return true;
        }

        if (!Objects.equals(a.getCollaborative(), b.getCollaborative())) return false;
        if (!Objects.equals(a.getDescription(), b.getDescription())) return false;
        if (!Objects.equals(a.getFollowers(), b.getFollowers())) return false;
        if (!Objects.equals(a.getHref(), b.getHref())) return false;
        if (!Objects.equals(a.getSpotifyID(), b.getSpotifyID())) return false;
        if (!Objects.equals(a.getName(), b.getName())) return false;
        if (!Objects.equals(a.getOwnerPublicUserSpotifyID(), b.getOwnerPublicUserSpotifyID())) return false;
        if (!Objects.equals(a.getPublicAccess(), b.getPublicAccess())) return false;
        if (!Objects.equals(a.getSpotifySnapshotId(), b.getSpotifySnapshotId())) return false;
        if (!Objects.equals(a.getTotalTracks(), b.getTotalTracks())) return false;
        if (!Objects.equals(a.getUri(), b.getUri())) return false;
        if (!Objects.equals(a.getSpotifyObjectType(), b.getSpotifyObjectType())) return false;
        if (!CollectionEquality.equals(a.getExternalUrls(), b.getExternalUrls())) return false;
        if (!CollectionEquality.equals(a.getImages(), b.getImages())) return false;
        return CollectionEquality.equals(a.getTracks(), b.getTracks());
    }
}
