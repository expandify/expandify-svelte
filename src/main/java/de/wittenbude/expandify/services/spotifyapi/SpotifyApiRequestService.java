package de.wittenbude.expandify.services.spotifyapi;

import de.wittenbude.expandify.models.SpotifyApiCredential;
import de.wittenbude.expandify.requestscope.CurrentSpotifyData;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.TooManyRequestsException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.IRequest;
import se.michaelthelin.spotify.requests.data.IPagingRequestBuilder;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SpotifyApiRequestService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyApiRequestService.class);
    private final CurrentSpotifyData spotifyData;

    public SpotifyApiRequestService(CurrentSpotifyData spotifyData) {
        this.spotifyData = spotifyData;
    }

    public <T, BT extends IRequest.Builder<T, ?>> T makeRequest(SpotifyApiRequestCaller<T, BT> apiBuilder) throws SpotifyWebApiException {
        try {
            if (spotifyData.isExpired()) {
                AuthorizationCodeCredentials credentials = makeRequest(SpotifyApi::authorizationCodeRefresh);
                spotifyData.refresh(new SpotifyApiCredential(credentials, spotifyData.getCredentials().getId()));
            }
            return apiBuilder.callApi(spotifyData.getApi()).build().execute();
        } catch (TooManyRequestsException e) {
            // Retry with recursion
            waitForRetry(e.getRetryAfter());
            return makeRequest(apiBuilder);
        } catch (IOException | ParseException e) {
            throw new SpotifyWebApiException("", e);
        }
    }

    private void waitForRetry(int retryAfter) throws SpotifyWebApiException {
        try {
            Thread.sleep(retryAfter * 1000L);
        } catch (InterruptedException ex) {
            throw new SpotifyWebApiException("InterruptedException", ex);
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
