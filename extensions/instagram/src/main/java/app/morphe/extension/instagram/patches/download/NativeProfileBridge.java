/* Copyright (C) 2026 piko <https://github.com/crimera/piko>
 * See NOTICE for GPLv3 §7(b) terms. */
package app.morphe.extension.instagram.patches.download;

import java.util.List;
import com.instagram.common.session.UserSession;

/** Replaced at patch time with direct calls resolved from the original APK. */
public final class NativeProfileBridge {
    private NativeProfileBridge() {}
    private static UnsupportedOperationException unresolved() { return new UnsupportedOperationException("Profile paging bridge was not patched"); }
    public static int failureStatus(Object error) { throw unresolved(); }
    public static UserSession session(Object fragment) { throw unresolved(); }
    public static Object user(Object fragment) { throw unresolved(); }
    public static boolean isMainGrid(Object source) { throw unresolved(); }
    public static List<?> plainMedia(Object response) { throw unresolved(); }
    public static List<?> mixedItems(Object response) { throw unresolved(); }
    public static Object mixedMedia(Object item) { throw unresolved(); }
    public static String cursor(Object response) { throw unresolved(); }
    public static Boolean hasMore(Object response) { throw unresolved(); }
    public static boolean isCached(Object response) { throw unresolved(); }
    public static void requestNext(Object fragment) { throw unresolved(); }
}
