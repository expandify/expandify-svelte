package de.wittenbude.expandify.services.spotifyapi;

import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.TooManyRequestsException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.IRequest;
import se.michaelthelin.spotify.requests.data.IPagingRequestBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class SpotifyApiRequest {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyApiRequest.class);

    protected SpotifyApi spotifyApi;

    public SpotifyApiRequest(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    protected void preMakeRequest() throws SpotifyWebApiException {}

    public <T, BT extends IRequest.Builder<T, ?>> T makeRequest(
            SpotifyApiRequestCaller<T, BT> apiBuilder
    ) throws SpotifyWebApiException {
        try {

            return makeRateLimitingRequest(apiBuilder);

        }  catch (IOException e) {
            throw new SpotifyWebApiException("IOException", e);
        } catch (ParseException e) {
            throw new SpotifyWebApiException("ParseException", e);
        } catch (InterruptedException e) {
            throw new SpotifyWebApiException("InterruptedException", e);
        }
    }


    private <T, BT extends IRequest.Builder<T, ?>> T makeRateLimitingRequest(
            SpotifyApiRequestCaller<T, BT> apiBuilder
    ) throws SpotifyWebApiException, IOException, ParseException, InterruptedException {
        try {
            preMakeRequest();
            IRequest<T> builtRequest = apiBuilder.callApi(this.spotifyApi).build();
            return builtRequest.execute();
        } catch (TooManyRequestsException e) {
            int timeout = e.getRetryAfter() * 1000;
            Thread.sleep(timeout);
            // Retry without recursion
            IRequest<T> builtRequest = apiBuilder.callApi(this.spotifyApi).build();
            return builtRequest.execute();
        }
    }


    public <T, BT extends IPagingRequestBuilder<T, BT>> List<T> pagingRequest(
            SpotifyApiPagingRequestCaller<T, BT> apiBuilder
    ) throws SpotifyWebApiException {

        List<T> items = new ArrayList<>();
        String next;
        int offset = 0;

        do {
            final int finalOffset = offset;
            Paging<T> page = makeRequest(api -> apiBuilder.callApi(api).offset(finalOffset));
            next = page.getNext();
            offset += page.getLimit();
            items.addAll(List.of(page.getItems()));
        } while (next != null);

        return items;
    }
}
