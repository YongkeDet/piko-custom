/*
 * Copyright (C) 2026 piko <https://github.com/crimera/piko>
 *
 * See the included NOTICE file for GPLv3 §7(b) terms that apply to this code.
*/


package app.morphe.extension.crimera.legacydownloader;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.database.Cursor;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import app.morphe.extension.crimera.constants.ExtensionStrings;
import app.morphe.extension.crimera.PikoUtils;
import app.morphe.extension.shared.Utils;

public class MediaDownloader {
    /** Optional per-download feedback. Legacy callers keep their existing toasts. */
    public interface Listener {
        boolean isCancelled();
        void onStart(String fileName);
        void onProgress(long received, long total, long bytesPerSecond);
        void onComplete(String fileName, Uri document);
        void onError(Exception error);
    }

    public static final class HttpFailure extends IOException {
        public final int status;
        public final String retryAfter;
        HttpFailure(int status, String retryAfter) {
            super("HTTP " + status + (retryAfter == null ? "" : " · Retry-After: " + retryAfter));
            this.status = status;
            this.retryAfter = retryAfter;
        }
    }

    private static final java.util.concurrent.atomic.AtomicInteger IDS =
            new java.util.concurrent.atomic.AtomicInteger(43000);
    private static final String CHANNEL_ID = "media_download_channel";
    // Separate downloader instances must reserve names before another request can choose them.
    private static final Object CREATE_DOCUMENT_LOCK = new Object();
    private final Context context;
    private final NotificationManager notificationManager;
    private final LinkedBlockingQueue<DownloadRequest> queue = new LinkedBlockingQueue<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private boolean isDownloading = false;

    public MediaDownloader(Context context) {
        Context resolvedContext = context != null ? context : Utils.getContext();
        if (resolvedContext == null) {
            throw new IllegalStateException("Download context is unavailable");
        }

        Context applicationContext = resolvedContext.getApplicationContext();
        this.context = applicationContext != null ? applicationContext : resolvedContext;
        this.notificationManager = this.context.getSystemService(NotificationManager.class);
        if (notificationManager == null) {
            throw new IllegalStateException("Notification service is unavailable");
        }
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Downloads", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void enqueue(DownloadRequest request) {
        if (!StorageUtils.checkStoragePermissions()) {
            StorageUtils.allowStorageAccess();
            return;
        }
        queue.add(request);
        processNext();
    }

    private synchronized void processNext() {
        if (isDownloading || queue.isEmpty()) return;
        isDownloading = true;
        DownloadRequest request = queue.poll();
        if (request != null) {
            executor.execute(() -> runDownloadTask(request));
        }
    }

    private void runDownloadTask(DownloadRequest request) {
        try {
            downloadBlocking(request, null);
        } finally {
            synchronized (this) { isDownloading = false; }
            processNext();
        }
    }

    /** Runs on the caller's worker thread; lets app-specific queues keep one file in flight. */
    public boolean downloadBlocking(DownloadRequest request, Listener listener) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Downloads must run on a worker thread");
        }
        int notificationId = IDS.incrementAndGet();
        Uri outputDocumentUri = null;
        File cacheFile = null;
        boolean downloadCompleted = false;
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context);
        }
        builder.setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle(ExtensionStrings.DOWNLOAD_ONGOING + request.fileName)
                .setOngoing(true) // Keeps notification un-swipable during download execution.
                .setProgress(100, 0, false);

        notificationManager.notify(notificationId, builder.build());

        try {
            if (!StorageUtils.checkStoragePermissions()) throw new IOException("Download folder access is missing");
            checkCancelled(listener);
            synchronized (CREATE_DOCUMENT_LOCK) {
                Uri targetDirectoryUri = getTargetDirectoryUri(request);
                request.fileName = DownloadFileNames.findAvailable(
                        request.fileName, getChildNames(targetDirectoryUri)
                );
                outputDocumentUri = DocumentsContract.createDocument(
                        context.getContentResolver(),
                        targetDirectoryUri,
                        getMimeType(request.fileName),
                        request.fileName
                );
            }
            if (outputDocumentUri == null) {
                throw new IOException("Could not create download file");
            }

            String downloadStartString = ExtensionStrings.DOWNLOAD_ONGOING + request.fileName;
            builder.setContentTitle(downloadStartString);
            notificationManager.notify(notificationId, builder.build());
            if (listener == null) showToast(downloadStartString);
            else listener.onStart(request.fileName);
            HttpURLConnection conn = null;
            try {
                URL url = new URL(request.url);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(20000);
                conn.setReadTimeout(20000);
                conn.connect();
                int status = conn.getResponseCode();
                if (status < 200 || status >= 300) {
                    throw new HttpFailure(status, conn.getHeaderField("Retry-After"));
                }
                long length = conn.getContentLengthLong();
                if (request.metadata == null) {
                    try (InputStream input = new BufferedInputStream(conn.getInputStream());
                         OutputStream output = context.getContentResolver().openOutputStream(outputDocumentUri)) {
                        if (output == null) {
                            throw new IOException("Could not open download file");
                        }
                        copyDownload(input, output, length, builder, notificationId, listener);
                    }
                } else {
                    cacheFile = File.createTempFile("piko-download-", ".mp4", context.getCacheDir());
                    try (InputStream input = new BufferedInputStream(conn.getInputStream());
                         OutputStream output = new BufferedOutputStream(new FileOutputStream(cacheFile))) {
                        copyDownload(input, output, length, builder, notificationId, listener);
                    }

                    try {
                        writeMetadata(cacheFile, outputDocumentUri, request.metadata);
                    } catch (Exception | LinkageError remuxException) {
                        PikoUtils.logger(remuxException);
                        copyRawFile(cacheFile, outputDocumentUri);
                    }
                }
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            checkCancelled(listener);
            downloadCompleted = true;
            if (listener != null) listener.onComplete(request.fileName, outputDocumentUri);

            final int finalNotificationId = notificationId;
            final String finalFileName = request.fileName;
            final String downloadCompletedString = ExtensionStrings.DOWNLOAD_COMPLETED + finalFileName;

            mainHandler.post(() -> {
                builder.setSmallIcon(android.R.drawable.stat_sys_download_done)
                        .setContentTitle(downloadCompletedString)
                        .setContentText(finalFileName)
                        .setOngoing(false) // This unlocks the swipe lock completely
                        .setProgress(0, 0, false); // Wipes the progress track bar layout away entirely
                // Force post the update layout
                notificationManager.notify(finalNotificationId, builder.build());

                try {
                    if (listener == null) PikoUtils.toast(downloadCompletedString);
                } catch (Exception ignored) {}
            });
            return true;
        } catch (Exception e) {
            if (!downloadCompleted && outputDocumentUri != null) {
                try {
                    DocumentsContract.deleteDocument(context.getContentResolver(), outputDocumentUri);
                } catch (Exception ignored) {}
            }
            if (listener == null) showToast(ExtensionStrings.DOWNLOAD_ERROR + e.getMessage());
            else listener.onError(e);
            notificationManager.cancel(notificationId);
            PikoUtils.logger(e);
            return false;
        } finally {
            if (cacheFile != null && cacheFile.exists() && !cacheFile.delete()) {
                PikoUtils.logger(new IOException("Could not delete download cache file"));
            }
        }
    }

    private static void checkCancelled(Listener listener) throws IOException {
        if (listener != null && listener.isCancelled()) throw new java.io.InterruptedIOException("Download paused");
    }

    private void copyDownload(
            InputStream input,
            OutputStream output,
            long length,
            Notification.Builder builder,
            int notificationId,
            Listener listener
    ) throws IOException {
        byte[] buffer = new byte[8192];
        long total = 0;
        int count;
        long started = SystemClock.elapsedRealtime();
        long lastUpdateTime = 0;
        while ((count = input.read(buffer)) != -1) {
            checkCancelled(listener);
            total += count;
            output.write(buffer, 0, count);
            long now = SystemClock.elapsedRealtime();
            if (now - lastUpdateTime >= 250) {
                int percent = length > 0 ? (int) Math.min(99, total * 100 / length) : 0;
                builder.setProgress(100, percent, length <= 0);
                notificationManager.notify(notificationId, builder.build());
                if (listener != null) listener.onProgress(total, length,
                        total * 1000 / Math.max(1, now - started));
                lastUpdateTime = now;
            }
        }
        if (length >= 0 && total != length) throw new IOException("Incomplete download: " + total + "/" + length);
        if (listener != null) listener.onProgress(total, length,
                total * 1000 / Math.max(1, SystemClock.elapsedRealtime() - started));
        output.flush();
    }

    private void writeMetadata(File inputFile, Uri outputUri, DownloadMetadata metadata)
            throws IOException {
        ParcelFileDescriptor descriptor = context.getContentResolver()
                .openFileDescriptor(outputUri, "rwt");
        if (descriptor == null) {
            throw new IOException("Could not open download file");
        }
        MetadataMuxer.write(
                inputFile,
                new ParcelFileDescriptor.AutoCloseOutputStream(descriptor),
                metadata
        );
    }

    private void copyRawFile(File inputFile, Uri outputUri) throws IOException {
        try (InputStream input = new BufferedInputStream(new FileInputStream(inputFile))) {
            ParcelFileDescriptor descriptor = context.getContentResolver()
                    .openFileDescriptor(outputUri, "rwt");
            if (descriptor == null) {
                throw new IOException("Could not reopen download file");
            }
            try (OutputStream output = new BufferedOutputStream(
                    new ParcelFileDescriptor.AutoCloseOutputStream(descriptor)
            )) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = input.read(buffer)) != -1) {
                    output.write(buffer, 0, count);
                }
                output.flush();
            }
        }
    }

    private void showToast(String msg) {
        mainHandler.post(() -> PikoUtils.toast(msg));
    }

    private Uri getTargetDirectoryUri(DownloadRequest request) throws Exception {
        Uri treeUri = StorageUtils.getDownloadTreeUri();
        if (treeUri == null) {
            throw new IOException("Download folder access is missing");
        }

        Uri parentUri = DocumentsContract.buildDocumentUriUsingTree(
                treeUri,
                DocumentsContract.getTreeDocumentId(treeUri)
        );

        if (request.subFolder == null || request.subFolder.isBlank()) {
            return parentUri;
        }

        for (String folderName : request.subFolder.split("/")) {
            if (!folderName.isBlank()) {
                parentUri = getOrCreateDirectory(parentUri, folderName);
            }
        }

        return parentUri;
    }

    private Uri getOrCreateDirectory(Uri parentUri, String folderName) throws Exception {
        Uri existingDirectoryUri = findChildDocument(parentUri, folderName, DocumentsContract.Document.MIME_TYPE_DIR);
        if (existingDirectoryUri != null) {
            return existingDirectoryUri;
        }

        Uri directoryUri = DocumentsContract.createDocument(
                context.getContentResolver(),
                parentUri,
                DocumentsContract.Document.MIME_TYPE_DIR,
                folderName
        );
        if (directoryUri == null) {
            throw new IOException("Could not create folder " + folderName);
        }

        return directoryUri;
    }

    private Set<String> getChildNames(Uri parentUri) throws IOException {
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                parentUri, DocumentsContract.getDocumentId(parentUri)
        );
        String[] projection = {DocumentsContract.Document.COLUMN_DISPLAY_NAME};
        Set<String> names = new HashSet<>();
        try (Cursor cursor = context.getContentResolver().query(childrenUri, projection, null, null, null)) {
            if (cursor == null) throw new IOException("Could not read download folder");
            while (cursor.moveToNext()) names.add(cursor.getString(0));
        }
        return names;
    }

    private Uri findChildDocument(Uri parentUri, String displayName, String mimeType) {
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                parentUri,
                DocumentsContract.getDocumentId(parentUri)
        );
        String[] projection = new String[]{
                DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                DocumentsContract.Document.COLUMN_MIME_TYPE
        };

        try (Cursor cursor = context.getContentResolver().query(childrenUri, projection, null, null, null)) {
            if (cursor == null) {
                return null;
            }

            while (cursor.moveToNext()) {
                String childDocumentId = cursor.getString(0);
                String childDisplayName = cursor.getString(1);
                String childMimeType = cursor.getString(2);
                boolean nameMatches = displayName.equals(childDisplayName);
                boolean mimeTypeMatches = mimeType == null || mimeType.equals(childMimeType);
                if (nameMatches && mimeTypeMatches) {
                    return DocumentsContract.buildDocumentUriUsingTree(parentUri, childDocumentId);
                }
            }
        } catch (Exception e) {
            PikoUtils.logger(e);
        }

        return null;
    }

    private String getMimeType(String fileName) {
        String mimeType = URLConnection.guessContentTypeFromName(fileName);
        return mimeType == null ? "application/octet-stream" : mimeType;
    }
}
