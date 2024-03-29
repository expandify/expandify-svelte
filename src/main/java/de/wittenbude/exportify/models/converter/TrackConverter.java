package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.models.Track;
import de.wittenbude.exportify.spotify.data.SpotifyTrack;

import java.util.Arrays;
import java.util.List;

public class TrackConverter {

    public static Track from(SpotifyTrack spotifyTrack, String spotifyAlbumID, List<String> spotifyArtistIDs) {
        return new Track()
                .setId(null)
                .setDiscNumber(spotifyTrack.getDiscNumber())
                .setDurationMs(spotifyTrack.getDurationMs())
                .setExplicit(spotifyTrack.getExplicit())
                .setHref(spotifyTrack.getHref())
                .setSpotifyID(spotifyTrack.getId())
                .setIsPlayable(spotifyTrack.getIsPlayable())
                .setRestrictions(spotifyTrack.getRestrictions() != null ? spotifyTrack.getRestrictions().getReason() : null)
                .setName(spotifyTrack.getName())
                .setPreviewUrl(spotifyTrack.getPreviewUrl())
                .setTrackNumber(spotifyTrack.getTrackNumber())
                .setSpotifyObjectType(spotifyTrack.getType().getType())
                .setUri(spotifyTrack.getUri())
                .setIsLocal(spotifyTrack.getIsLocal())
                .setExternalIDs(ExternalIDsConverter.from(spotifyTrack.getExternalIDs()))
                .setPopularity(spotifyTrack.getPopularity())
                .setExternalUrls(spotifyTrack.getExternalUrls())
                .setAvailableMarkets(Arrays.asList(spotifyTrack.getAvailableMarkets()))
                .setSpotifyAlbumID(spotifyAlbumID)
                .setSpotifyArtistIDs(spotifyArtistIDs);
    }

}
