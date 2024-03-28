package de.wittenbude.exportify.models.comparators;

import de.wittenbude.exportify.models.Artist;

import java.util.Objects;

public class ArtistEquality {
    public static boolean equals(Artist a, Artist b) {

        if (a.getId() != null ? a.getId().equals(b.getId()) : b.getId() != null) {
            return true;
        }

        if (!Objects.equals(a.getFollowers(), b.getFollowers())) return false;
        if (!Objects.equals(a.getHref(), b.getHref())) return false;
        if (!Objects.equals(a.getSpotifyID(), b.getSpotifyID())) return false;
        if (!Objects.equals(a.getName(), b.getName())) return false;
        if (!Objects.equals(a.getPopularity(), b.getPopularity())) return false;
        if (!Objects.equals(a.getSpotifyObjectType(), b.getSpotifyObjectType())) return false;
        if (!Objects.equals(a.getUri(), b.getUri())) return false;
        if (!CollectionEquality.equals(a.getImages(), b.getImages())) return false;
        if (!CollectionEquality.equals(a.getGenres(), b.getGenres())) return false;

        return CollectionEquality.equals(a.getExternalUrls(), b.getExternalUrls());
    }
}
