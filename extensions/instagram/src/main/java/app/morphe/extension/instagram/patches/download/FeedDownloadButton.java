/* Copyright (C) 2026 piko <https://github.com/crimera/piko>
 * See NOTICE for GPLv3 §7(b) terms. */
package app.morphe.extension.instagram.patches.download;

import android.content.Context;
import android.view.View;
import java.util.List;
import kotlin.jvm.functions.Function1;
import com.instagram.common.session.UserSession;
import app.morphe.extension.instagram.utils.Pref;
import app.morphe.extension.shared.Utils;

/** The patch supplies native component construction and model access through direct bridges. */
public final class FeedDownloadButton {
    private static final int BUTTON_ID = View.generateViewId();
    private FeedDownloadButton() {}

    public static int buttonId() { return BUTTON_ID; }

    public static void add(Object saveIcon, List<Object> buttons, UserSession session, Object state, Object componentContext) {
        if (!Pref.enableDownload()) return;
        Object button = create(saveIcon, ignored -> {
            if (!Pref.enableDownload()) return Boolean.TRUE;
            Context context = ignored instanceof View ? ((View) ignored).getContext() : context(componentContext);
            if (context == null) return Boolean.TRUE;
            Object media = media(state);
            if (media != null) DownloadUtils.downloadPost(context, session, media, currentIndex(state));
            return Boolean.TRUE;
        });
        if (button == null) throw new IllegalStateException("Feed download component bridge was not bound");
        buttons.add(button);
    }

    // Replaced at patch time. Obfuscated app types never enter the extension's signatures.
    public static Object create(Object icon, Function1<Object, Object> click) { return null; }
    public static Object media(Object state) { return null; }
    public static int currentIndex(Object state) { return 0; }
    public static Context context(Object componentContext) { return null; }
}
