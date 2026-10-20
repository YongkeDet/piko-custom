/* Copyright (C) 2026 piko <https://github.com/crimera/piko>
 * See NOTICE for GPLv3 §7(b) terms. */
package app.morphe.extension.instagram.patches.download;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/** Native page bookkeeping independent of Android, transport and UI. */
class ProfilePageState<T> {
    final LinkedHashMap<String, T> media = new LinkedHashMap<>();
    private final Set<String> seen = new HashSet<>();
    private final Set<String> cursors = new HashSet<>();
    boolean initialSeen, ready, loading, blocked, overflow, networkSeen, awaitingNetwork;
    Boolean more;
    long generation;

    void accept(boolean first, boolean cached, List<T> items, Function<T, String> key,
                Boolean next, String cursor, boolean active) {
        if (first) {
            media.clear(); seen.clear(); cursors.clear(); initialSeen = true; overflow = false; networkSeen = false;
            if (!cached) blocked = false;
        }
        int added = 0;
        for (T item : items) {
            String id = key.apply(item);
            if (id == null || id.isEmpty() || "0".equals(id)) {
                blocked = true;
                throw new IllegalStateException("Invalid media ID");
            }
            if (seen.contains(id)) continue;
            if (media.size() >= 5000) { overflow = true; break; }
            seen.add(id); media.put(id, item); added++;
        }
        ready = true;
        if (!cached) {
            more = next; networkSeen = true; awaitingNetwork = false; generation++;
            if (Boolean.TRUE.equals(more) && (cursor == null || cursor.isEmpty()
                    || !cursors.add(cursor) || (active && !first && added == 0))) {
                blocked = true;
                throw new IllegalStateException("Profile pagination did not advance");
            }
        }
    }
    boolean canStart() { return initialSeen && ready && networkSeen && !overflow && !blocked; }
    boolean complete() { return canStart() && !loading && !awaitingNetwork && Boolean.FALSE.equals(more) && media.isEmpty(); }
    void commit(List<T> downloaded, Function<T, String> key) {
        for (T item : downloaded) media.remove(key.apply(item), item);
    }
}
