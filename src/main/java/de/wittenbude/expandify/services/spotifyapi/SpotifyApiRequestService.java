package de.wittenbude.expandify.services.spotifyapi;

import de.wittenbude.expandify.models.SpotifyApiCredential;
import de.wittenbude.expandify.requestscope.CurrentSpotifyData;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.TooManyRequestsException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.requests.IRequest;
import se.michaelthelin.spotify.requests.data.IPagingCursorbasedRequestBuilder;
import se.michaelthelin.spotify.requests.data.IPagingRequestBuilder;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

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

    public <T, BT extends IPagingRequestBuilder<T, BT>> Stream<T> pagingStreamRequest(
            SpotifyApiPagingRequestCaller<T, BT> apiBuilder
    ) throws SpotifyWebApiException {


        Paging<T> firstPage = makeRequest(api -> apiBuilder.callApi(api).offset(0));

        AtomicInteger offset = new AtomicInteger(firstPage.getOffset());
        AtomicReference<String> next = new AtomicReference<>(firstPage.getNext());

        Predicate<T[]> hasNext = _ts -> next.get() != null;
        UnaryOperator<T[]> getNext = _ts -> pageRequest(apiBuilder, offset, next);
        return Stream
                .iterate(firstPage.getItems(), hasNext, getNext)
                .flatMap(Arrays::stream);
    }

    public <T, BT extends IPagingCursorbasedRequestBuilder<T, String, BT>> Stream<T> cursorStreamRequest(
            SpotifyApiCursorRequestCaller<T, String, BT> apiBuilder
    ) throws SpotifyWebApiException {

        PagingCursorbased<T> firstPage = makeRequest(apiBuilder::callApi);

        AtomicReference<String> next = new AtomicReference<>(firstPage.getNext());
        AtomicReference<String> after = new AtomicReference<>(firstPage.getCursors()[0].getAfter());

        Predicate<T[]> hasNext = _ts -> next.get() != null;
        UnaryOperator<T[]> getNext = _ts -> cursorRequest(apiBuilder, after, next);
        return Stream
                .iterate(firstPage.getItems(), hasNext, getNext)
                .flatMap(Arrays::stream);
    }

    @SneakyThrows
    private<T, BT extends IPagingRequestBuilder<T, BT>> T[] pageRequest(
            SpotifyApiPagingRequestCaller<T, BT> apiBuilder,
            AtomicInteger offset,
            AtomicReference<String> next
    ) {
        Paging<T> page = makeRequest(spotifyApi -> apiBuilder.callApi(spotifyApi).offset(offset.get()));
        next.set(page.getNext());
        offset.set(offset.get() + page.getLimit());
        return page.getItems();
    }

    @SneakyThrows
    private<T, BT extends IPagingCursorbasedRequestBuilder<T, String, BT>> T[] cursorRequest(
            SpotifyApiCursorRequestCaller<T, String, BT> apiBuilder,
            AtomicReference<String> after,
            AtomicReference<String> next
    ) {
        PagingCursorbased<T> page = makeRequest(api -> apiBuilder.callApi(api).after(after.get()));
        next.set(page.getNext());
        after.set(page.getCursors()[0].getAfter());
        return page.getItems();
    }
}
