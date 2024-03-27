package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.dto.PrivateUserSchema;
import de.wittenbude.exportify.dto.PublicUserSchema;
import de.wittenbude.exportify.models.PrivateUser;
import de.wittenbude.exportify.models.PublicUser;
import de.wittenbude.exportify.spotify.data.SpotifyPrivateUser;
import de.wittenbude.exportify.spotify.data.SpotifyPublicUser;

public class UserConverter {


    public static PrivateUserSchema toDTO(PrivateUser privateUser) {
        return PrivateUserSchema
                .builder()
                .id(privateUser.getId())
                .displayName(privateUser.getPublicUser().getDisplayName())
                .externalUrls(privateUser.getPublicUser().getExternalUrls())
                .followers(privateUser.getPublicUser().getFollowers().getTotal())
                .href(privateUser.getPublicUser().getHref())
                .spotifyID(privateUser.getPublicUser().getSpotifyID())
                .images(ImageConverter.toDTOs(privateUser.getPublicUser().getImages()))
                .type(privateUser.getPublicUser().getObjectType().getType())
                .uri(privateUser.getPublicUser().getUri())
                .country(privateUser.getCountry())
                .email(privateUser.getEmail())
                .explicitContentFilterEnabled(privateUser.getExplicitContent().getFilterEnabled())
                .explicitContentFilterLocked(privateUser.getExplicitContent().getFilterLocked())
                .product(privateUser.getProduct().getType())
                .build();
    }


    public static PublicUserSchema toDTO(PublicUser publicUser) {
        return PublicUserSchema
                .builder()
                .id(publicUser.getId())
                .displayName(publicUser.getDisplayName())
                .externalUrls(publicUser.getExternalUrls())
                .followers(publicUser.getFollowers().getTotal())
                .href(publicUser.getHref())
                .spotifyID(publicUser.getSpotifyID())
                .images(ImageConverter.toDTOs(publicUser.getImages()))
                .type(publicUser.getObjectType().getType())
                .uri(publicUser.getUri())
                .build();
    }


    public static PrivateUser from(SpotifyPrivateUser spotifyPrivateUser) {
        return new PrivateUser()
                .setPublicUser(UserConverter.from((SpotifyPublicUser) spotifyPrivateUser))
                .setCountry(spotifyPrivateUser.getCountry())
                .setEmail(spotifyPrivateUser.getEmail())
                .setProduct(TypeConverter.from(spotifyPrivateUser.getProduct()))
                .setExplicitContent(ExplicitContentConverter.from(spotifyPrivateUser.getSpotifyExplicitContent()));
    }


    public static PublicUser from(SpotifyPublicUser spotifyPublicUser) {
        return new PublicUser()
                .setDisplayName(spotifyPublicUser.getDisplayName())
                .setExternalUrls(spotifyPublicUser.getExternalUrls())
                .setFollowers(FollowersConverter.from(spotifyPublicUser.getSpotifyFollowers()))
                .setHref(spotifyPublicUser.getHref())
                .setSpotifyID(spotifyPublicUser.getId())
                .setImages(ImageConverter.from(spotifyPublicUser.getSpotifyImages()))
                .setObjectType(TypeConverter.from(spotifyPublicUser.getType()))
                .setUri(spotifyPublicUser.getUri());
    }
}
