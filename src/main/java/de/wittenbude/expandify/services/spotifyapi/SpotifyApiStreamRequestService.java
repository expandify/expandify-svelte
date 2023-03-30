package de.wittenbude.expandify.services.spotifyapi;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.requests.data.IPagingCursorbasedRequestBuilder;
import se.michaelthelin.spotify.requests.data.IPagingRequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Service
public class SpotifyApiStreamRequestService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyApiStreamRequestService.class);
    private final SpotifyApiRequestService spotifyApiRequestService;

    public SpotifyApiStreamRequestService(SpotifyApiRequestService spotifyApiRequestService) {
        this.spotifyApiRequestService = spotifyApiRequestService;
    }

    public <T, BT extends IPagingRequestBuilder<T, BT>> Stream<T> pagingStreamRequest(
            SpotifyApiPagingRequestCaller<T, BT> apiBuilder
    ) throws SpotifyWebApiException {

        Paging<T> firstPage = spotifyApiRequestService.makeRequest(api -> apiBuilder.callApi(api).offset(0));

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

        PagingCursorbased<T> firstPage = spotifyApiRequestService.makeRequest(apiBuilder::callApi);

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
        Paging<T> page = spotifyApiRequestService.makeRequest(spotifyApi -> apiBuilder.callApi(spotifyApi).offset(offset.get()));
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
        PagingCursorbased<T> page = spotifyApiRequestService.makeRequest(api -> apiBuilder.callApi(api).after(after.get()));
        next.set(page.getNext());
        after.set(page.getCursors()[0].getAfter());
        return page.getItems();
    }
}
