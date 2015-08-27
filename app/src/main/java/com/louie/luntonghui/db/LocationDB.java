package com.louie.luntonghui.db;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.louie.luntonghui.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/6/10.
 */
public class LocationDB {
    private static final int BUFFER_SIZE = 400000;
    public static final String  DB_Location_NAME = "luntonghui_base.db";
    public static final String DBName = "luntonghui.db";


    private LocationDB(){
    }

    public static void initialize(Application app){
         PackageInfo info;

        try {
            info = app.getPackageManager().getPackageInfo(app.getPackageName(), 0);
            String  DBParentPath= app.getDatabasePath(DBName).getPath();
            String curDBLocationPath = DBParentPath + "/" + DB_Location_NAME;
            if(!new File(curDBLocationPath).exists()){
                InputStream is = app.getResources().openRawResource(R.raw.luntonghui_base);
                FileOutputStream fos = new FileOutputStream(curDBLocationPath);
                byte []buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while((count = is.read(buffer))>0){
                    fos.write(buffer,0,count);
                }
                    fos.close();
                    is.close();
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
