/* Copyright (C) 2026 piko <https://github.com/crimera/piko>
 * See NOTICE for GPLv3 §7(b) terms. */
package app.morphe.extension.instagram.patches.download;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** Stop after a server refusal; never interpret an invalid header as permission to retry. */
public final class DownloadRatePolicy {
    private DownloadRatePolicy() {}
    public static long cooldownDeadline(long now, String retryAfter) {
        long minimum = now + 15 * 60_000L;
        if (retryAfter == null) return minimum;
        try {
            long seconds = Long.parseLong(retryAfter.trim());
            if (seconds < 0) return minimum;
            if (seconds > (Long.MAX_VALUE - now) / 1000) return Long.MAX_VALUE;
            return Math.max(minimum, now + seconds * 1000);
        } catch (NumberFormatException ignored) {
            try {
                return Math.max(minimum, ZonedDateTime.parse(retryAfter.trim(),
                        DateTimeFormatter.RFC_1123_DATE_TIME).toInstant().toEpochMilli());
            } catch (Exception invalid) { return minimum; }
        }
    }
}
