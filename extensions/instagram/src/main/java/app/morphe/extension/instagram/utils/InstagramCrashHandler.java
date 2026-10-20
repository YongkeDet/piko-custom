/* Copyright (C) 2026 piko <https://github.com/crimera/piko>
 * See NOTICE for GPLv3 §7(b) terms. */
package app.morphe.extension.instagram.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import app.morphe.extension.shared.Utils;

/** Scoped-storage crash reports, with a pending copy when public storage is unavailable. */
public final class InstagramCrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String FOLDER = "Piko-Instagram";
    private final Context context;
    private final Thread.UncaughtExceptionHandler previous;
    private final File pending;

    public InstagramCrashHandler(Context context) {
        Context app = context.getApplicationContext();
        this.context = app == null ? context : app;
        previous = Thread.getDefaultUncaughtExceptionHandler();
        pending = new File(this.context.getFilesDir(), "piko-pending-crash.txt");
        if (pending.isFile()) {
            try (FileInputStream input = new FileInputStream(pending);
                 ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[4096];
                int count;
                while ((count = input.read(buffer)) != -1) bytes.write(buffer, 0, count);
                if (publish(bytes.toByteArray())) pending.delete();
            } catch (Exception exception) {
                Log.e("PikoCrash", "Pending crash report retained", exception);
            }
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable error) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String report = "--- Device Info ---\nBrand: " + Build.BRAND
                    + "\nOS Version: " + Build.VERSION.RELEASE + "\nSDK Level: " + Build.VERSION.SDK_INT
                    + "\n\n--- App Info ---\nPackage Name: " + context.getPackageName()
                    + "\nVersion Name: " + info.versionName + "\nVersion Code: " + info.versionCode
                    + "\nPatch version: " + Utils.getPatchesReleaseVersion()
                    + "\n\n--- Stack Trace ---\n" + Log.getStackTraceString(error);
            byte[] bytes = report.getBytes(StandardCharsets.UTF_8);
            try (FileOutputStream output = new FileOutputStream(pending)) {
                output.write(bytes);
                output.getFD().sync();
            } catch (Exception exception) {
                Log.e("PikoCrash", "Private storage unavailable; trying public storage", exception);
            }
            if (publish(bytes)) pending.delete();
        } catch (Exception exception) {
            Log.e("PikoCrash", "Could not save crash report", exception);
        } finally {
            if (previous != null) previous.uncaughtException(thread, error);
            else android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private boolean publish(byte[] bytes) {
        String filename = "crash_report_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US).format(new Date()) + ".txt";
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Downloads.DISPLAY_NAME, filename);
                values.put(MediaStore.Downloads.MIME_TYPE, "text/plain");
                values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/" + FOLDER);
                values.put(MediaStore.Downloads.IS_PENDING, 1);
                Uri uri = context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri == null) return false;
                try {
                    try (OutputStream output = context.getContentResolver().openOutputStream(uri)) {
                        if (output == null) throw new java.io.IOException("No output stream for crash report");
                        output.write(bytes);
                    }
                    values.clear();
                    values.put(MediaStore.Downloads.IS_PENDING, 0);
                    if (context.getContentResolver().update(uri, values, null, null) < 1) {
                        throw new java.io.IOException("Crash report could not be published");
                    }
                    return true;
                } catch (Exception exception) {
                    context.getContentResolver().delete(uri, null, null);
                    throw exception;
                }
            }
            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FOLDER);
            if (!folder.isDirectory() && !folder.mkdirs()) return false;
            try (FileOutputStream output = new FileOutputStream(new File(folder, filename))) { output.write(bytes); }
            return true;
        } catch (Exception exception) {
            Log.e("PikoCrash", "Public storage unavailable; keeping the pending report", exception);
            return false;
        }
    }
}
