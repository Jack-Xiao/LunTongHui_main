package com.louie.luntonghui.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2015/7/16.
 */
public class ClientUtil {

    public static String getRootDir(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getDownDir(){
        String path = getRootDir() + "/luntonghui/apk";

        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static File createApkFile() {
        File newAPKFile = null;
        try {
            String mDirPath = getDownDir();
            newAPKFile = new File(mDirPath, Config.newApkName);
            if (newAPKFile.exists()) {
                newAPKFile.delete();
            }
            newAPKFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newAPKFile;
    }

    public static void installApk(){


    }
}
