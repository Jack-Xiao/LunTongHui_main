package com.louie.luntonghui.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.louie.luntonghui.App;

/**
 * Created by Administrator on 2015/6/10.
 */
public class DataProvider extends ContentProvider {
    static final String TAG = DataProvider.class.getSimpleName();
    static final Object DBLock = new Object();
    public static final String SCHEME = "content://";
    public static final String PATH_REGION="/region";
    public static final String AUTHORITY = "com.louie.luntonghui.provider";

    public static final Uri REGION_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY +PATH_REGION);
    private static LocationDBHelper mDBHelper;
    private static final int REGION = 0;

    /*
    * MIME type definitions
    */
    public static final String REGION_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.storm.luntonghui.region";

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, "region", REGION);
    }

    public static LocationDBHelper getDBHelper(){
        if(mDBHelper == null){
            mDBHelper = new LocationDBHelper(App.getContext());
        }
        return mDBHelper;
    }
    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case REGION:
                return REGION_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
