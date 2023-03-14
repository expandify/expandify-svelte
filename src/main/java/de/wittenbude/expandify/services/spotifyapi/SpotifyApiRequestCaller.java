package de.wittenbude.expandify.services.spotifyapi;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.IRequest;

import java.io.IOException;

public interface SpotifyApiRequestCaller<T, BT extends IRequest.Builder<T, ?>> {
    IRequest.Builder<T, BT> callApi(SpotifyApi spotifyApi) throws IOException, ParseException, SpotifyWebApiException;
}