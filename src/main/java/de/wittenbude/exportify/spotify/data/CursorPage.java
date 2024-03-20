package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.util.Streamable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
public class CursorPage<T> implements Streamable<T> {


    @JsonProperty("href")
    private String href;
    @JsonProperty("limit")
    private int limit;
    @JsonProperty("next")
    private String next;
    @JsonProperty("cursors")
    private Cursors cursors;
    @JsonProperty("total")
    private int total;
    @JsonProperty("items")
    private List<T> items;

    public static <T> CursorPage<T> empty() {
        CursorPage<T> cursorPage = new CursorPage<>();
        cursorPage.href = null;
        cursorPage.limit = 0;
        cursorPage.next = null;
        cursorPage.cursors = new Cursors();
        cursorPage.cursors.after = null;
        cursorPage.cursors.before = null;
        cursorPage.total = 0;
        cursorPage.items = new ArrayList<>();
        return cursorPage;
    }

    @Override
    @NonNull
    public Iterator<T> iterator() {
        return items.iterator();
    }

    public boolean hasNext() {
        return next != null && cursors != null && cursors.after != null;
    }

    public boolean hasContent() {
        return total > 0 && items != null && !items.isEmpty();
    }

    @Getter
    @Setter
    public static class Cursors {
        @JsonProperty("after")
        private String after;

        @JsonProperty("before")
        private String before;
    }
}
