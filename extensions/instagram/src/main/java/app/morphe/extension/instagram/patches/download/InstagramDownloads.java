/* Copyright (C) 2026 piko <https://github.com/crimera/piko>
 * See NOTICE for GPLv3 §7(b) terms. */
package app.morphe.extension.instagram.patches.download;

import static app.morphe.extension.instagram.utils.IgStr.str;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import app.morphe.extension.crimera.legacydownloader.DownloadRequest;
import app.morphe.extension.crimera.legacydownloader.MediaDownloader;
import app.morphe.extension.crimera.legacydownloader.StorageUtils;

/** Every Instagram entry point shares this worker; each user operation keeps its own notice. */
public final class InstagramDownloads {
    private static final ExecutorService WORKER = Executors.newSingleThreadExecutor();
    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static volatile long cooldownUntil;
    private InstagramDownloads() {}

    public static final class Task {
        public final long id = TopDownloadNotice.nextId();
        public final String title;
        public volatile boolean paused;
        public volatile Exception failure;
        private final java.util.concurrent.atomic.AtomicBoolean terminal = new java.util.concurrent.atomic.AtomicBoolean();
        public int completed;
        public int discovered;
        public String lastFile = "";
        private String folder = "";
        private long currentBytes, savedBytes, transferMillis, fileStarted;
        Task(String title) { this.title = title; }
        Runnable whenPaused;
        public void pause() {
            if (paused) return;
            paused = true;
            MAIN.post(() -> {
                if (whenPaused != null) whenPaused.run();
                else finish(false, str("piko_download_file_counts", completed, discovered));
            });
        }
        public void status(String detail) {
            if (!terminal.get()) TopDownloadNotice.progress(id, title, detail, -1, this::pause,
                    () -> !terminal.get());
        }
        public void finish(boolean success, String detail) {
            if (!terminal.compareAndSet(false, true)) return;
            TopDownloadNotice.finish(id, str(success ? "piko_download_finished" : "piko_download_paused"),
                    title + "\n" + detail + (lastFile.isEmpty() ? "" : "\n" + lastFile)
                            + (savedBytes == 0 ? "" : "\n" + size(savedBytes) + " · "
                            + size(savedBytes * 1000 / Math.max(1, transferMillis)) + "/s")
                            + (folder.isEmpty() ? "" : "\n" + folder), !success);
        }
    }

    public interface Saved { void saved(int index, Uri document) throws Exception; }
    public static boolean ready(Context context) {
        TopDownloadNotice.attach(context);
        restoreLimits(context);
        String cooldown = cooldownReason();
        if (cooldown != null) {
            TopDownloadNotice.message(context, str("piko_download_paused"),
                    cooldown);
            return false;
        }
        if (!StorageUtils.checkStoragePermissions()) {
            StorageUtils.allowStorageAccess();
            TopDownloadNotice.message(context, str("piko_download_paused"), str("piko_download_folder_required"));
            return false;
        }
        return true;
    }
    public static Task task(Context context, String title) {
        TopDownloadNotice.attach(context);
        return new Task(title);
    }
    public static void download(Context context, List<DownloadRequest> requests) {
        if (requests.isEmpty() || !ready(context)) return;
        Task task = task(context, str("piko_category_downloads"));
        task.discovered = requests.size();
        submit(context, task, requests, (index, uri) -> {}, () -> task.finish(
                task.failure == null && !task.paused,
                str("piko_download_file_counts", task.completed, task.discovered)
                        + (task.failure == null ? "" : "\n" + task.failure.getMessage())));
    }
    public static void submit(Context context, Task task, List<DownloadRequest> requests, Saved saved, Runnable finished) {
        Context app = context.getApplicationContext();
        task.status(str("piko_download_file_counts", task.completed, task.discovered));
        WORKER.execute(() -> {
            try {
                MediaDownloader downloader = new MediaDownloader(app);
                for (int i = 0; i < requests.size(); i++) {
                    if (task.paused || task.failure != null) break;
                    String cooldown = cooldownReason();
                    if (cooldown != null) throw new java.io.IOException(cooldown);
                    final int index = i;
                    boolean success = downloader.downloadBlocking(requests.get(i), new MediaDownloader.Listener() {
                        public boolean isCancelled() { return task.paused; }
                        public void onStart(String name) {
                            task.lastFile = name; task.currentBytes = 0; task.fileStarted = SystemClock.elapsedRealtime();
                            Uri tree = StorageUtils.getDownloadTreeUri();
                            String folder = tree == null ? "" : DocumentsContract.getTreeDocumentId(tree);
                            String sub = requests.get(index).subFolder;
                            task.folder = folder + (sub == null || sub.isEmpty() ? "" : "/" + sub);
                            task.status(name);
                        }
                        public void onProgress(long bytes, long total, long speed) {
                            task.currentBytes = bytes;
                            int percent = total > 0 ? (int) Math.min(99, bytes * 100 / total) : -1;
                            String stats = (percent < 0 ? str("piko_download_unknown_size") : percent + "%")
                                    + " · " + size(bytes) + " / " + (total > 0 ? size(total) : "—")
                                    + " · " + size(speed) + "/s";
                            if (!task.terminal.get()) TopDownloadNotice.progress(task.id, task.title,
                                    str("piko_download_file_counts", task.completed, task.discovered)
                                            + "\n" + task.lastFile + "\n" + stats, percent, task::pause,
                                    () -> !task.terminal.get());
                        }
                        public void onComplete(String name, Uri document) {
                            try {
                                saved.saved(index, document); task.completed++;
                                task.savedBytes += task.currentBytes;
                                task.transferMillis += SystemClock.elapsedRealtime() - task.fileStarted;
                            }
                            catch (Exception e) { task.failure = e; task.paused = true; }
                        }
                        public void onError(Exception error) {
                            task.failure = error;
                            if (error instanceof MediaDownloader.HttpFailure) {
                                MediaDownloader.HttpFailure http = (MediaDownloader.HttpFailure) error;
                                if (http.status == 429 || http.status == 401 || http.status == 403) {
                                    pauseForRefusal(app, http.retryAfter);
                                }
                            }
                        }
                    });
                    if (!success) break;
                }
            } catch (Exception e) { task.failure = e; }
            finally { MAIN.post(finished); }
        });
    }
    static String cooldownReason() {
        long until = cooldownUntil;
        return System.currentTimeMillis() < until
                ? str("piko_download_cooldown", new java.util.Date(until).toString()) : null;
    }
    public static synchronized void pauseForRefusal(Context context, String retryAfter) {
        cooldownUntil = Math.max(cooldownUntil,
                DownloadRatePolicy.cooldownDeadline(System.currentTimeMillis(), retryAfter));
        context.getSharedPreferences("piko_download_limits", 0).edit().putLong("until", cooldownUntil).commit();
    }
    static void prepare(Runnable work) { WORKER.execute(work); }
    public static synchronized void restoreLimits(Context context) {
        cooldownUntil = Math.max(cooldownUntil,
                context.getSharedPreferences("piko_download_limits", 0).getLong("until", 0));
    }
    private static String size(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format(Locale.getDefault(), "%.1f KiB", bytes / 1024d);
        if (bytes < 1024L * 1024 * 1024) return String.format(Locale.getDefault(), "%.1f MiB", bytes / (1024d * 1024));
        return String.format(Locale.getDefault(), "%.1f GiB", bytes / (1024d * 1024 * 1024));
    }
}
