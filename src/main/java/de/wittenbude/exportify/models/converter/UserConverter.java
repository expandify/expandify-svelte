package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.dto.PrivateUserSchema;
import de.wittenbude.exportify.dto.PublicUserSchema;
import de.wittenbude.exportify.models.PrivateSpotifyUser;
import de.wittenbude.exportify.models.PublicSpotifyUser;
import de.wittenbude.exportify.spotify.data.SpotifyPrivateUser;
import de.wittenbude.exportify.spotify.data.SpotifyPublicUser;

public class UserConverter {


    public static PrivateUserSchema toDTO(PrivateSpotifyUser privateSpotifyUser, PublicSpotifyUser publicSpotifyUser) {
        return PrivateUserSchema
                .builder()
                .id(privateSpotifyUser.getId())
                .displayName(publicSpotifyUser.getDisplayName())
                .externalUrls(publicSpotifyUser.getExternalUrls())
                .followers(publicSpotifyUser.getFollowers())
                .href(publicSpotifyUser.getHref())
                .spotifyID(publicSpotifyUser.getSpotifyID())
                .images(ImageConverter.toDTOs(publicSpotifyUser.getImages()))
                .type(publicSpotifyUser.getSpotifyObjectType())
                .uri(publicSpotifyUser.getUri())
                .country(privateSpotifyUser.getCountry())
                .email(privateSpotifyUser.getEmail())
                .explicitContentFilterEnabled(privateSpotifyUser.getExplicitContentFilterEnabled())
                .explicitContentFilterLocked(privateSpotifyUser.getExplicitContentFilterLocked())
                .product(privateSpotifyUser.getSpotifyProductType())
                .build();
    }


    public static PublicUserSchema toDTO(PublicSpotifyUser publicSpotifyUser) {
        return PublicUserSchema
                .builder()
                .id(publicSpotifyUser.getId())
                .displayName(publicSpotifyUser.getDisplayName())
                .externalUrls(publicSpotifyUser.getExternalUrls())
                .followers(publicSpotifyUser.getFollowers())
                .href(publicSpotifyUser.getHref())
                .spotifyID(publicSpotifyUser.getSpotifyID())
                .images(ImageConverter.toDTOs(publicSpotifyUser.getImages()))
                .type(publicSpotifyUser.getSpotifyObjectType())
                .uri(publicSpotifyUser.getUri())
                .build();
    }


    public static PrivateSpotifyUser from(SpotifyPrivateUser spotifyPrivateUser) {
        return new PrivateSpotifyUser()
                .setSpotifyID(spotifyPrivateUser.getId())
                .setPublicSpotifyUserID(spotifyPrivateUser.getId())
                .setCountry(spotifyPrivateUser.getCountry())
                .setEmail(spotifyPrivateUser.getEmail())
                .setSpotifyProductType(spotifyPrivateUser.getProduct().getType())
                .setExplicitContentFilterLocked(spotifyPrivateUser.getSpotifyExplicitContent().getFilterLocked())
                .setExplicitContentFilterEnabled(spotifyPrivateUser.getSpotifyExplicitContent().getFilterEnabled());
    }


    public static PublicSpotifyUser from(SpotifyPublicUser spotifyPublicUser) {
        return new PublicSpotifyUser()
                .setDisplayName(spotifyPublicUser.getDisplayName())
                .setExternalUrls(spotifyPublicUser.getExternalUrls())
                .setFollowers(spotifyPublicUser.getSpotifyFollowers().getTotal())
                .setHref(spotifyPublicUser.getHref())
                .setSpotifyID(spotifyPublicUser.getId())
                .setImages(ImageConverter.from(spotifyPublicUser.getSpotifyImages()))
                .setSpotifyObjectType(spotifyPublicUser.getType().getType())
                .setUri(spotifyPublicUser.getUri());
    }
}
