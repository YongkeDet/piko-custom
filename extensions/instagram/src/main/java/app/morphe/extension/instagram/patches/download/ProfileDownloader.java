/* Copyright (C) 2026 piko <https://github.com/crimera/piko>
 * See NOTICE for GPLv3 §7(b) terms. */
package app.morphe.extension.instagram.patches.download;

import static app.morphe.extension.instagram.utils.IgStr.str;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import com.instagram.common.session.UserSession;
import app.morphe.extension.instagram.entity.MediaData;
import app.morphe.extension.instagram.entity.UserData;
import app.morphe.extension.crimera.legacydownloader.DownloadRequest;
import app.morphe.extension.shared.Logger;
import app.morphe.extension.shared.Utils;

/** Reuses the open profile's native pager, one request after the previous page's files finish. */
public final class ProfileDownloader {
    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static final Map<Object, State> STATES = new WeakHashMap<>();
    private static WeakReference<Object> foreground = new WeakReference<>(null);
    private static final long PAGE_DELAY_MS = 30_000;
    private ProfileDownloader() {}
    private static final class State extends ProfilePageState<MediaData> {
        String userId, username, account;
        UserSession session;
        Context context;
        InstagramDownloads.Task task;
        boolean downloading;
    }
    private static State state(Object fragment) { return STATES.computeIfAbsent(fragment, ignored -> new State()); }
    public static void onResume(Object fragment) {
        MAIN.post(() -> {
            try {
                foreground = new WeakReference<>(fragment);
                State s = state(fragment);
                UserSession session = NativeProfileBridge.session(fragment);
                UserData user = new UserData(NativeProfileBridge.user(fragment));
                String account = session.getUserId(), profile = user.getUserId();
                if (s.account != null && (!account.equals(s.account) || !profile.equals(s.userId))) {
                    stop(s, str("piko_profile_download_left"));
                    s = new State(); STATES.put(fragment, s);
                }
                s.session = session; s.userId = profile; s.username = user.getUsername(); s.account = account;
            } catch (Exception | LinkageError e) { Logger.printException(() -> "Profile download context", e); }
        });
    }
    public static void onPause(Object fragment) {
        MAIN.post(() -> {
            if (foreground.get() == fragment) foreground.clear();
            State s = STATES.get(fragment);
            if (s != null && s.task != null) { s.task.pause(); stop(s, str("piko_profile_download_left")); }
        });
    }
    public static void onDestroy(Object fragment) {
        onPause(fragment);
        MAIN.post(() -> STATES.remove(fragment));
    }
    public static void onStart(Object fragment, Object source) {
        MAIN.post(() -> { if (NativeProfileBridge.isMainGrid(source)) state(fragment).loading = true; });
    }
    public static void onFinish(Object fragment, Object source) {
        MAIN.post(() -> {
            if (!NativeProfileBridge.isMainGrid(source)) return;
            State s = state(fragment); s.loading = false;
            if (s.task != null && !s.downloading && s.ready && !s.awaitingNetwork) process(fragment, s);
        });
    }
    public static boolean hasTask(Object fragment) {
        State s = STATES.get(fragment); return s != null && s.task != null;
    }
    public static void onFailure(Object fragment, Object error, Object source) {
        MAIN.post(() -> {
            if (!NativeProfileBridge.isMainGrid(source)) return;
            State s = state(fragment); s.loading = false; s.blocked = true;
            int status = NativeProfileBridge.failureStatus(error);
            if (status == 429 || status == 401 || status == 403)
                InstagramDownloads.pauseForRefusal(Utils.getContext(), null);
            if (s.task != null) { s.task.pause(); stop(s, str("piko_profile_download_native_error") + (status == 0 ? "" : "\nHTTP " + status)); }
        });
    }
    public static void onPage(Object fragment, Object response, Object source, boolean first) {
        MAIN.post(() -> {
            if (!NativeProfileBridge.isMainGrid(source)) return;
            State s = state(fragment);
            try {
                boolean cached = NativeProfileBridge.isCached(response);
                if (first && s.task != null) { s.task.pause(); stop(s, str("piko_profile_download_refreshed")); }
                UserSession session = NativeProfileBridge.session(fragment);
                List<Object> items = new ArrayList<>();
                List<?> plain = NativeProfileBridge.plainMedia(response);
                if (plain != null) items.addAll(plain);
                List<?> mixed = NativeProfileBridge.mixedItems(response);
                if (mixed != null) for (Object item : mixed) {
                    Object media = NativeProfileBridge.mixedMedia(item);
                    if (media != null) items.add(media);
                }
                List<MediaData> media = new ArrayList<>();
                for (Object item : items) media.add(new MediaData(item, session));
                s.accept(first, cached, media, MediaData::getPostID,
                        NativeProfileBridge.hasMore(response), NativeProfileBridge.cursor(response), s.task != null);

            } catch (Exception | LinkageError e) {
                s.blocked = true;
                Logger.printException(() -> "Profile download response", e);
                if (s.task != null) { s.task.pause(); stop(s, String.valueOf(e.getMessage())); }
            }
        });
    }
    public static void start(Context context, UserData selected) {
        TopDownloadNotice.attach(context);
        try {
            Object fragment = foreground.get();
            State s = fragment == null ? null : STATES.get(fragment);
            if (s == null || !selected.getUserId().equals(s.userId) || s.account == null
                    || !s.canStart()) {
                TopDownloadNotice.message(context, str("piko_download_paused"), str("piko_profile_download_refresh_required"));
                return;
            }
            if (s.downloading && s.task == null) {
                TopDownloadNotice.message(context, str("piko_download_paused"), str("piko_profile_download_stopping")); return;
            }
            if (s.task != null) { s.task.status(str("piko_profile_download_already_running")); return; }
            if (!InstagramDownloads.ready(context)) return;
            s.context = context.getApplicationContext();
            s.task = InstagramDownloads.task(context, str("piko_profile_download_title", s.username));
            InstagramDownloads.Task task = s.task;
            task.whenPaused = () -> { if (s.task == task) stop(s, str("piko_profile_download_saved_checkpoint")); };
            process(fragment, s);
        } catch (Exception | LinkageError e) {
            TopDownloadNotice.message(context, str("piko_download_paused"), String.valueOf(e.getMessage()));
        }
    }
    private static void process(Object fragment, State s) {
        if (s.task == null || s.downloading || s.loading || s.awaitingNetwork) return;
        if (s.task.paused || s.blocked || s.overflow || foreground.get() != fragment) {
            stop(s, str("piko_profile_download_saved_checkpoint")); return;
        }
        InstagramDownloads.Task task = s.task;
        List<MediaData> posts = new ArrayList<>(s.media.values());
        if (posts.isEmpty()) { next(fragment, s); return; }
        s.downloading = true;
        // Build/check history on the same serial worker as media transfers, never on the UI thread.
        prepare(s.context, task, posts, s.account, s.userId, s.username, () -> {
            s.downloading = false;
            if (s.task != task) return;
            if (task.failure != null || task.paused) {
                stop(s, str("piko_profile_download_saved_checkpoint")
                        + (task.failure == null ? "" : "\n" + task.failure.getMessage()));
                return;
            }
            s.commit(posts, MediaData::getPostID);
            process(fragment, s);
        });
    }
    private static void prepare(Context context, InstagramDownloads.Task task, List<MediaData> posts,
                                String account, String profile, String username, Runnable finished) {
        task.status(str("piko_download_file_counts", task.completed, task.discovered));
        InstagramDownloads.prepare(() -> {
            boolean submitted = false;
            List<DownloadRequest> requests = new ArrayList<>();
            List<String> keys = new ArrayList<>();
            try {
                if (task.paused) return;
                try (ProfileDownloadStore store = new ProfileDownloadStore(context)) {
                    for (MediaData post : posts) {
                        if (task.paused) return;
                        List<DownloadRequest> children = DownloadUtils.profileRequests(post, username, () -> task.paused);
                        for (int i = 0; i < children.size(); i++) {
                            if (task.paused) return;
                            String key = post.getPostID() + ":" + i;
                            if (!store.isSaved(account, profile, key)) { keys.add(key); requests.add(children.get(i)); }
                        }
                    }
                    if (task.paused) return;
                    task.discovered += requests.size();
                    InstagramDownloads.submit(context, task, requests, (index, uri) -> {
                        try (ProfileDownloadStore checkpoint = new ProfileDownloadStore(context)) {
                            checkpoint.saved(account, profile, keys.get(index), uri);
                        }
                    }, finished);
                    submitted = true;
                }
            } catch (Exception e) { task.failure = e; }
            finally { if (!submitted) MAIN.post(finished); }
        });
    }
    private static void next(Object fragment, State s) {
        if (s.task == null) return;
        if (s.task.paused) { stop(s, str("piko_profile_download_saved_checkpoint")); return; }
        if (s.complete()) {
            s.task.finish(true, str("piko_download_file_counts", s.task.completed, s.task.discovered)
                    + "\n" + str("piko_profile_download_scope"));
            s.task = null; return;
        }
        if (s.more == null) { stop(s, str("piko_profile_download_unknown_page")); return; }
        InstagramDownloads.Task expected = s.task;
        long generation = s.generation;
        expected.status(str("piko_download_file_counts", expected.completed, expected.discovered)
                + "\n" + str("piko_profile_download_waiting"));
        MAIN.postDelayed(() -> {
            if (s.task != expected) return;
            if (expected.paused || foreground.get() != fragment) { stop(s, str("piko_profile_download_saved_checkpoint")); return; }
            if (!Utils.isNetworkConnected()) { stop(s, str("piko_no_internet")); return; }
            if (s.loading || s.generation != generation || !s.media.isEmpty()) { process(fragment, s); return; }
            String cooldown = InstagramDownloads.cooldownReason();
            if (cooldown != null) { stop(s, cooldown); return; }
            s.loading = true; s.awaitingNetwork = true;
            try { NativeProfileBridge.requestNext(fragment); }
            catch (Exception | LinkageError e) { s.loading = false; stop(s, String.valueOf(e.getMessage())); return; }
            MAIN.postDelayed(() -> {
                if (s.task == expected && s.generation == generation) {
                    s.blocked = true; stop(s, str("piko_profile_download_stalled"));
                }
            }, 45_000);
        }, PAGE_DELAY_MS);
    }
    private static void stop(State s, String reason) {
        if (s.task == null) return;
        s.task.pause();
        s.task.finish(false, str("piko_download_file_counts", s.task.completed, s.task.discovered) + "\n" + reason);
        s.task = null;
    }
}
