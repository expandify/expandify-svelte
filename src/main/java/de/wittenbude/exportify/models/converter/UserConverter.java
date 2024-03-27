package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.dto.PrivateUserSchema;
import de.wittenbude.exportify.dto.PublicUserSchema;
import de.wittenbude.exportify.models.PrivateUser;
import de.wittenbude.exportify.models.PublicUser;
import de.wittenbude.exportify.spotify.data.SpotifyExplicitContent;
import de.wittenbude.exportify.spotify.data.SpotifyPrivateUser;
import de.wittenbude.exportify.spotify.data.SpotifyPublicUser;

public class UserConverter {


    public static PrivateUserSchema toDTO(PrivateUser privateUser) {
        return PrivateUserSchema
                .builder()
                .id(privateUser.getId())
                .displayName(privateUser.getPublicUser().getDisplayName())
                .externalUrls(privateUser.getPublicUser().getExternalUrls())
                .followers(privateUser.getPublicUser().getFollowers())
                .href(privateUser.getPublicUser().getHref())
                .spotifyID(privateUser.getPublicUser().getSpotifyID())
                .images(ImageConverter.toDTOs(privateUser.getPublicUser().getImages()))
                .type(privateUser.getPublicUser().getSpotifyObjectType())
                .uri(privateUser.getPublicUser().getUri())
                .country(privateUser.getCountry())
                .email(privateUser.getEmail())
                .explicitContentFilterEnabled(privateUser.getExplicitContent().getFilterEnabled())
                .explicitContentFilterLocked(privateUser.getExplicitContent().getFilterLocked())
                .product(privateUser.getSpotifyProductType())
                .build();
    }


    public static PublicUserSchema toDTO(PublicUser publicUser) {
        return PublicUserSchema
                .builder()
                .id(publicUser.getId())
                .displayName(publicUser.getDisplayName())
                .externalUrls(publicUser.getExternalUrls())
                .followers(publicUser.getFollowers())
                .href(publicUser.getHref())
                .spotifyID(publicUser.getSpotifyID())
                .images(ImageConverter.toDTOs(publicUser.getImages()))
                .type(publicUser.getSpotifyObjectType())
                .uri(publicUser.getUri())
                .build();
    }


    public static PrivateUser from(SpotifyPrivateUser spotifyPrivateUser) {
        return new PrivateUser()
                .setPublicUser(UserConverter.from((SpotifyPublicUser) spotifyPrivateUser))
                .setCountry(spotifyPrivateUser.getCountry())
                .setEmail(spotifyPrivateUser.getEmail())
                .setSpotifyProductType(spotifyPrivateUser.getProduct().getType())
                .setExplicitContent(UserConverter.from(spotifyPrivateUser.getSpotifyExplicitContent()));
    }


    public static PublicUser from(SpotifyPublicUser spotifyPublicUser) {
        return new PublicUser()
                .setDisplayName(spotifyPublicUser.getDisplayName())
                .setExternalUrls(spotifyPublicUser.getExternalUrls())
                .setFollowers(spotifyPublicUser.getSpotifyFollowers().getTotal())
                .setHref(spotifyPublicUser.getHref())
                .setSpotifyID(spotifyPublicUser.getId())
                .setImages(ImageConverter.from(spotifyPublicUser.getSpotifyImages()))
                .setSpotifyObjectType(spotifyPublicUser.getType().getType())
                .setUri(spotifyPublicUser.getUri());
    }

    public static PrivateUser.ExplicitContent from(SpotifyExplicitContent spotifyExplicitContent) {
        return new PrivateUser.ExplicitContent()
                .setFilterEnabled(spotifyExplicitContent.getFilterEnabled())
                .setFilterLocked(spotifyExplicitContent.getFilterLocked());
    }
}
