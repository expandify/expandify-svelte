package de.wittenbude.exportify.models.comparators;

import de.wittenbude.exportify.models.PrivateSpotifyUser;
import de.wittenbude.exportify.models.PublicSpotifyUser;

import java.util.Objects;

public class UserEquality {

    public static boolean equals(PublicSpotifyUser a, PublicSpotifyUser b) {

        if (a.getId() != null ? a.getId().equals(b.getId()) : b.getId() != null) {
            return true;
        }

        if (!Objects.equals(a.getDisplayName(), b.getDisplayName())) return false;
        if (!Objects.equals(a.getFollowers(), b.getFollowers())) return false;
        if (!Objects.equals(a.getHref(), b.getHref())) return false;
        if (!Objects.equals(a.getSpotifyID(), b.getSpotifyID())) return false;
        if (!Objects.equals(a.getSpotifyObjectType(), b.getSpotifyObjectType())) return false;
        if (!Objects.equals(a.getUri(), b.getUri())) return false;
        if (!CollectionEquality.equals(a.getImages(), b.getImages())) return false;

        return CollectionEquality.equals(a.getExternalUrls(), b.getExternalUrls());
    }

    public static boolean equals(PrivateSpotifyUser a, PrivateSpotifyUser b) {

        if (a.getId() != null ? a.getId().equals(b.getId()) : b.getId() != null) {
            return true;
        }

        if (!Objects.equals(a.getSpotifyID(), b.getSpotifyID())) return false;
        if (!Objects.equals(a.getCountry(), b.getCountry())) return false;
        if (!Objects.equals(a.getEmail(), b.getEmail())) return false;
        if (!Objects.equals(a.getSpotifyProductType(), b.getSpotifyProductType())) return false;
        if (!Objects.equals(a.getExplicitContentFilterEnabled(), b.getExplicitContentFilterEnabled())) return false;
        if (!Objects.equals(a.getExplicitContentFilterLocked(), b.getExplicitContentFilterLocked())) return false;

        return Objects.equals(a.getPublicSpotifyUserID(), b.getPublicSpotifyUserID());
    }
}
