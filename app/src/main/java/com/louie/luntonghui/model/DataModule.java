package com.louie.luntonghui.model;

import android.content.Context;
import android.net.Uri;

import com.louie.luntonghui.R;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Provides;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by Administrator on 2015/6/29.
 */
public class DataModule {

    @Provides
    @Singleton
    Picasso providePicasso(OkHttpClient okHttpClient, Context ctx){
        Picasso.Builder builder = new Picasso.Builder(ctx);
        builder.downloader(new OkHttpDownloader(okHttpClient))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

                    }
                })
                .indicatorsEnabled(false)
                .loggingEnabled(false);
        return builder.build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(Cache cache){
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        return okHttpClient;
    }

    Cache provideHttpCache(@Named("Http") File httpCacheDir){
        int cacheSize = 1024*1024*100;
        try {
            return new Cache(httpCacheDir, cacheSize);
        } catch (Exception e){
            //Timber.e("install http cache false");
        }

        return null;
    }

   /* @Provides
    @Singleton
    DiskLruCache provideDataCache(@Named("Data") File cacheDir){
        DiskLruCache cache = null;
        try {
            //10M
            cache = DiskLruCache.open(cacheDir, BuildConfig.VERSION_CODE, 1, 1024 * 1024 * 10);
        } catch (IOException e) {
            e.printStackTrace();
         }
        return cache;
    }*/
   @Provides
   @Singleton
   ErrorHandler provideErrorHandler(final Context ctx){
       return new ErrorHandler() {
           @Override
           public Throwable handleError(RetrofitError retrofitError) {
              // Timber.e(retrofitError, "请求出现错误:%s", retrofitError.getUrl());
               RetrofitError.Kind kind = retrofitError.getKind();
               String message;
               if(RetrofitError.Kind.NETWORK.equals(kind)){
                   message = ctx.getString(R.string.network_error);
               }else if(RetrofitError.Kind.HTTP.equals(kind)){
                   message = ctx.getString(R.string.http_error);
               }else if(RetrofitError.Kind.CONVERSION.equals(kind)){
                   message = ctx.getString(R.string.conversion_error);
               }else{
                   message = ctx.getString(R.string.unexpected_error);
               }
               return new Exception(message);
           }
       };
   }
}
