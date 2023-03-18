package de.wittenbude.expandify.services.spotifyapi;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.IPagingCursorbasedRequestBuilder;
import se.michaelthelin.spotify.requests.data.IPagingRequestBuilder;

import java.io.IOException;

public interface SpotifyApiCursorRequestCaller<T, A, BT extends IPagingCursorbasedRequestBuilder<T, A, BT>> {
    BT callApi(SpotifyApi spotifyApi) throws IOException, ParseException, SpotifyWebApiException;
}
