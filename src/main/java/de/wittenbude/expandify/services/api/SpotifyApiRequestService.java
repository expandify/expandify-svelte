package de.wittenbude.expandify.services.api;

import de.wittenbude.expandify.models.db.SpotifyApiCredentials;
import de.wittenbude.expandify.services.auth.AuthenticatedUserService;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.TooManyRequestsException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.requests.IRequest;
import se.michaelthelin.spotify.requests.data.IPagingCursorbasedRequestBuilder;
import se.michaelthelin.spotify.requests.data.IPagingRequestBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyApiRequestService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyApiRequestService.class);
    private final AuthenticatedUserService authenticatedUserService;

    public SpotifyApiRequestService(AuthenticatedUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
    }

    public <T, BT extends IRequest.Builder<T, ?>> T makeRequest(SpotifyApiRequestCaller<T, BT> apiBuilder) throws SpotifyWebApiException {
        try {
            if (authenticatedUserService.isExpired()) {
                AuthorizationCodeCredentials credentials = authenticatedUserService
                        .getAuthenticatedSpotifyApi()
                        .authorizationCodeRefresh()
                        .build()
                        .execute();

                authenticatedUserService.refreshCredentials(new SpotifyApiCredentials(credentials, authenticatedUserService.getCredentials().getId()));
            }
            return apiBuilder.callApi(authenticatedUserService.getAuthenticatedSpotifyApi()).build().execute();
        } catch (TooManyRequestsException e) {
            // Retry with recursion
            waitForRetry(e.getRetryAfter());
            return makeRequest(apiBuilder);
        } catch (IOException | ParseException e) {
            throw new SpotifyWebApiException("", e);
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

    public <T, BT extends IPagingCursorbasedRequestBuilder<T, String, BT>> List<T> cursorRequest(
            SpotifyApiCursorRequestCaller<T, String, BT> apiBuilder
    ) throws SpotifyWebApiException {

        PagingCursorbased<T> firstPage = makeRequest(apiBuilder::callApi);

        List<T> items = new ArrayList<>(List.of(firstPage.getItems()));
        String next = firstPage.getNext();
        String after = firstPage.getCursors()[0].getAfter();

        while (next != null) {
            String finalAfter = after;
            PagingCursorbased<T> page = makeRequest(api -> apiBuilder.callApi(api).after(finalAfter));
            next = page.getNext();
            after = firstPage.getCursors()[0].getAfter();
            items.addAll(List.of(page.getItems()));
        }

        return items;
    }

    /*
    public <T, BT extends IPagingRequestBuilder<T, BT>> Stream<T> pagingStreamRequest(
            SpotifyApiPagingRequestCaller<T, BT> apiBuilder
    ) throws SpotifyWebApiException {

        Paging<T> firstPage = this.makeRequest(api -> apiBuilder.callApi(api).offset(0));

        AtomicInteger offset = new AtomicInteger(firstPage.getOffset());
        AtomicReference<String> next = new AtomicReference<>(firstPage.getNext());

        // TODO hasNext has to hold for the initial Page
        Predicate<T[]> hasNext = _ts -> next.get() != null;
        UnaryOperator<T[]> getNext = _ts -> pageRequest(apiBuilder, offset, next);
        return Stream
                .iterate(firstPage.getItems(), hasNext, getNext)
                .flatMap(Arrays::stream);
    }

    public <T, BT extends IPagingCursorbasedRequestBuilder<T, String, BT>> Stream<T> cursorStreamRequest(
            SpotifyApiCursorRequestCaller<T, String, BT> apiBuilder
    ) throws SpotifyWebApiException {

        PagingCursorbased<T> firstPage = this.makeRequest(apiBuilder::callApi);

        AtomicReference<String> next = new AtomicReference<>(firstPage.getNext());
        AtomicReference<String> after = new AtomicReference<>(firstPage.getCursors()[0].getAfter());

        // TODO hasNext has to hold for the initial Page
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
        Paging<T> page = this.makeRequest(spotifyApi -> apiBuilder.callApi(spotifyApi).offset(offset.get()));
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
        PagingCursorbased<T> page = this.makeRequest(api -> apiBuilder.callApi(api).after(after.get()));
        next.set(page.getNext());
        after.set(page.getCursors()[0].getAfter());
        return page.getItems();
    }
*/

    private void waitForRetry(int retryAfter) throws SpotifyWebApiException {
        try {
            Thread.sleep(retryAfter * 1000L);
        } catch (InterruptedException ex) {
            throw new SpotifyWebApiException("InterruptedException", ex);
        }
    }


}
