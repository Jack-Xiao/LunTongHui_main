package com.louie.luntonghui.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by Administrator on 2015/6/10.
 */
public class LocationDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;


    /*    public LocationDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, LocationDB.DBName, factory, version);
        }*/
    public LocationDBHelper(Context context) {
        super(context, LocationDB.DBName, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
