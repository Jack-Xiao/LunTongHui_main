package com.louie.luntonghui.net;

import android.content.Context;

import com.louie.luntonghui.util.Config;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/6/3.
 */
public class OkHttpUtils {
    private static OkHttpClient sigleton;

    public static OkHttpClient getInstance(Context context) {
        if (sigleton == null) {
            synchronized (OkHttpUtils.class) {
                if (sigleton == null) {
                    File cacheDir = new File(context.getCacheDir(), Config.RESPONSE_CACHE);
                    sigleton = new OkHttpClient();
                    sigleton.setCache(new Cache(cacheDir, Config.RESPONSE_CACHE_SIZE));
                    sigleton.setConnectTimeout(Config.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
                    sigleton.setReadTimeout(Config.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);

                    //if(BuildConfig.DEBUG)sigleton.networkInterceptors().add(new StethoInterceptor());
                }
            }
        }
        return sigleton;
    }
}