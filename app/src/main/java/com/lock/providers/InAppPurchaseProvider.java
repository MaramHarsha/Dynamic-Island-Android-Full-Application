package com.lock.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import java.util.HashMap;

public class InAppPurchaseProvider extends ContentProvider {
    private static HashMap<String, String> BirthMap = new HashMap<>();
    public static final String URL = "content://com.jalan.control.center.android12/purchase";
    public static final Uri CONTENT_URI = Uri.parse(URL);
    static final String CREATE_TABLE = " CREATE TABLE purchaseTable (id INTEGER);";
    static final String DATABASE_NAME = "BirthdayReminder";
    static final int DATABASE_VERSION = 1;
    public static final String ID = "id";
    static final String PROVIDER_NAME = "com.jalan.control.center.android12";
    static final int PURCHASE = 1;
    static final String TABLE_NAME = "purchaseTable";

    static final UriMatcher uriMatcher;
    private SQLiteDatabase database;
    DBHelper dbHelper;

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    static {
        UriMatcher uriMatcher2 = new UriMatcher(-1);
        uriMatcher = uriMatcher2;
        uriMatcher2.addURI(PROVIDER_NAME, "purchase", 1);
        BirthMap.put(ID, ID);
    }

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, InAppPurchaseProvider.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(InAppPurchaseProvider.CREATE_TABLE);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            Log.w(DBHelper.class.getName(), "Upgrading database from version " + i + " to " + i2 + ". Old data will be destroyed");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS purchaseTable");
            onCreate(sQLiteDatabase);
        }
    }

    public boolean onCreate() {
        DBHelper dBHelper = new DBHelper(getContext());
        this.dbHelper = dBHelper;
        SQLiteDatabase writableDatabase = dBHelper.getWritableDatabase();
        this.database = writableDatabase;
        return writableDatabase != null;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        sQLiteQueryBuilder.setStrict(true);
        sQLiteQueryBuilder.setTables(TABLE_NAME);
        if (uriMatcher.match(uri) == 1) {
            sQLiteQueryBuilder.setProjectionMap(BirthMap);
            Cursor query = sQLiteQueryBuilder.query(this.database, strArr, str, strArr2, (String) null, (String) null, str2);
            query.setNotificationUri(getContext().getContentResolver(), uri);
            return query;
        }
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        long insert = this.database.insert(TABLE_NAME, "", contentValues);
        if (insert > 0) {
            Uri withAppendedId = ContentUris.withAppendedId(CONTENT_URI, insert);
            getContext().getContentResolver().notifyChange(withAppendedId, (ContentObserver) null);
            return withAppendedId;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    public String getType(Uri uri) {
        if (uriMatcher.match(uri) == 1) {
            return "vnd.android.cursor.dir/vnd.example.friends";
        }
        throw new IllegalArgumentException("Unsupported URI: " + uri);
    }
}
