package com.louie.luntonghui.task;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack on 15/12/10.
 */
public class FileService {
    private DBOpenHelper openHelper;

    public FileService(Context context) {
        openHelper = new DBOpenHelper(context);
    }

    /**
     * »ñÈ¡Ã¿ÌõÏß³ÌÒÑ¾­ÏÂÔØµÄÎÄ¼þ³¤¶È
     * @param path
     * @return
     */
    public Map<Integer, Integer> getData(String path){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select threadid, downlength from filedownlog where downpath=?", new String[]{path});
        Map<Integer, Integer> data = new HashMap<Integer, Integer>();

        while(cursor.moveToNext()){
            data.put(cursor.getInt(0), cursor.getInt(1));
        }

        cursor.close();
        db.close();
        return data;
    }

    /**
     * ±£´æÃ¿ÌõÏß³ÌÒÑ¾­ÏÂÔØµÄÎÄ¼þ³¤¶È
     * @param path
     * @param map
     */
    public void save(String path,  Map<Integer, Integer> map){//int threadid, int position
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();

        try{
            for(Map.Entry<Integer, Integer> entry : map.entrySet()){
                db.execSQL("insert into filedownlog(downpath, threadid, downlength) values(?,?,?)",
                        new Object[]{path, entry.getKey(), entry.getValue()});
            }

            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }

        db.close();
    }

    /**
     * ÊµÊ±¸üÐÂÃ¿ÌõÏß³ÌÒÑ¾­ÏÂÔØµÄÎÄ¼þ³¤¶È
     * @param path
     * @param map
     */
    public void update(String path, Map<Integer, Integer> map){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();

        try{
            for(Map.Entry<Integer, Integer> entry : map.entrySet()){
                db.execSQL("update filedownlog set downlength=? where downpath=? and threadid=?",
                        new Object[]{entry.getValue(), path, entry.getKey()});
            }

            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }

        db.close();
    }

    /**
     * µ±ÎÄ¼þÏÂÔØÍê³Éºó£¬É¾³ý¶ÔÓ¦µÄÏÂÔØ¼ÇÂ¼
     * @param path
     */
    public void delete(String path){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.execSQL("delete from filedownlog where downpath=?", new Object[]{path});
        db.close();
    }

}
