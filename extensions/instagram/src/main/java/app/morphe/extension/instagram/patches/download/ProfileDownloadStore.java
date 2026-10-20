/* Copyright (C) 2026 piko <https://github.com/crimera/piko>
 * See NOTICE for GPLv3 §7(b) terms. */
package app.morphe.extension.instagram.patches.download;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.DocumentsContract;

/** Only committed SAF documents are resumable; accounts and target profiles never share keys. */
final class ProfileDownloadStore extends SQLiteOpenHelper {
    private final Context context;
    ProfileDownloadStore(Context context) {
        super(context, "piko-profile-downloads.db", null, 1);
        this.context = context;
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE saved (account TEXT NOT NULL, profile TEXT NOT NULL, media TEXT NOT NULL, uri TEXT NOT NULL, PRIMARY KEY(account, profile, media))");
    }
    public void onUpgrade(SQLiteDatabase db, int from, int to) { throw new IllegalStateException("Unsupported download history migration"); }
    boolean isSaved(String account, String profile, String media) {
        String uri;
        try (Cursor c = getReadableDatabase().query("saved", new String[]{"uri"},
                "account=? AND profile=? AND media=?", new String[]{account, profile, media}, null, null, null)) {
            if (!c.moveToFirst()) return false;
            uri = c.getString(0);
        }
        try (Cursor c = context.getContentResolver().query(Uri.parse(uri),
                new String[]{DocumentsContract.Document.COLUMN_SIZE}, null, null, null)) {
            return c != null && c.moveToFirst() && !c.isNull(0) && c.getLong(0) > 0;
        } catch (Exception removedOrRevoked) { return false; }
    }
    void saved(String account, String profile, String media, Uri document) {
        ContentValues row = new ContentValues();
        row.put("account", account); row.put("profile", profile); row.put("media", media); row.put("uri", document.toString());
        if (getWritableDatabase().insertWithOnConflict("saved", null, row, SQLiteDatabase.CONFLICT_REPLACE) < 0)
            throw new android.database.sqlite.SQLiteException("Could not record saved media");
    }
}
