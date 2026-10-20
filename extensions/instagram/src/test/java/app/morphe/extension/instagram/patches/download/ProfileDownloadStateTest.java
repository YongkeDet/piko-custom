package app.morphe.extension.instagram.patches.download;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import app.morphe.extension.instagram.entity.MediaData;
import org.junit.Test;
import static org.junit.Assert.*;

/** Catches false completion, dropped concurrent pages, duplicate paging and work continuing after pause. */
public class ProfileDownloadStateTest {
    @Test public void cachedResponsesNeverAuthorizeCompletionOrARequest() {
        ProfilePageState<String> s = new ProfilePageState<>();
        s.accept(true, true, List.of("1"), Function.identity(), false, null, false);
        assertFalse(s.canStart()); assertFalse(s.complete()); assertEquals(0, s.generation);
        s.accept(true, false, List.of("1"), Function.identity(), false, null, false);
        assertTrue(s.canStart()); assertFalse(s.complete());
        s.commit(List.of("1"), Function.identity()); assertTrue(s.complete());
        s.loading = true; assertFalse(s.complete()); s.loading = false;
        s.awaitingNetwork = true; assertFalse(s.complete());
        s.accept(false, true, List.of(), Function.identity(), false, null, true);
        assertTrue(s.awaitingNetwork); assertEquals(1, s.generation);
    }
    @Test public void pagesArrivingDuringAFileTransferArePreserved() {
        ProfilePageState<String> s = new ProfilePageState<>();
        s.accept(true, false, List.of("1"), Function.identity(), true, "next-1", false);
        List<String> inFlight = List.copyOf(s.media.values());
        s.accept(false, false, List.of("1", "2"), Function.identity(), false, null, true);
        s.commit(inFlight, Function.identity());
        assertEquals(List.of("2"), List.copyOf(s.media.values())); assertFalse(s.complete());
        s.commit(List.of("2"), Function.identity()); assertTrue(s.complete());
    }
    @Test public void duplicatePagesAndUnknownEndMarkersFailClosed() {
        ProfilePageState<String> s = new ProfilePageState<>();
        s.accept(true, false, List.of("1"), Function.identity(), true, "next-1", false);
        s.commit(List.of("1"), Function.identity());
        assertThrows(IllegalStateException.class,
                () -> s.accept(false, false, List.of("1"), Function.identity(), true, "next-1", true));
        assertTrue(s.blocked); assertFalse(s.complete());
        s.accept(true, true, List.of("1"), Function.identity(), false, null, false);
        assertTrue(s.blocked);
        s.accept(true, false, List.of(), Function.identity(), null, null, false);
        assertFalse(s.complete());
        assertThrows(IllegalStateException.class,
                () -> s.accept(false, false, List.of("0"), Function.identity(), false, null, true));
        assertFalse(s.canStart());
    }
    @Test public void repeatedOrMissingCursorsNeverLoopEvenWhenMediaChanges() {
        ProfilePageState<String> s = new ProfilePageState<>();
        s.accept(true, false, List.of("1"), Function.identity(), true, "next-1", false);
        assertThrows(IllegalStateException.class,
                () -> s.accept(false, false, List.of("2"), Function.identity(), true, "next-1", true));
        assertTrue(s.blocked);
        assertThrows(IllegalStateException.class,
                () -> s.accept(true, false, List.of("3"), Function.identity(), true, null, false));
        assertFalse(s.canStart());
    }
    @Test public void retryAfterCannotShortenOrOverflowTheRefusalCooldown() {
        long now = Instant.parse("2026-10-18T02:00:00Z").toEpochMilli();
        assertEquals(now + 900_000, DownloadRatePolicy.cooldownDeadline(now, "1"));
        assertEquals(now + 3_600_000, DownloadRatePolicy.cooldownDeadline(now, "3600"));
        assertEquals(now + 3_600_000, DownloadRatePolicy.cooldownDeadline(now, "Sun, 18 Oct 2026 03:00:00 GMT"));
        assertEquals(Long.MAX_VALUE, DownloadRatePolicy.cooldownDeadline(now, Long.toString(Long.MAX_VALUE)));
        for (String invalid : new String[]{null, "", "garbage", "-1"})
            assertEquals(now + 900_000, DownloadRatePolicy.cooldownDeadline(now, invalid));
    }
    @Test public void pausedPreparationStopsBeforeReadingMoreCarouselMetadata() throws Exception {
        AtomicBoolean paused = new AtomicBoolean(true);
        int[] reads = new int[2];
        MediaData media = new MediaData(null) {
            @Override public int getCarouselSize() { reads[0]++; return 1000; }
            @Override public MediaData getMediaAt(int index) {
                reads[1]++;
                paused.set(true); // The user pauses while native media selection is returning.
                return null; // No child metadata may be touched after cancellation.
            }
        };
        assertTrue(DownloadUtils.profileRequests(media, "profile", paused::get).isEmpty());
        assertArrayEquals(new int[]{0, 0}, reads);
        paused.set(false);
        assertTrue(DownloadUtils.profileRequests(media, "profile", paused::get).isEmpty());
        assertArrayEquals(new int[]{1, 1}, reads);
    }
}
