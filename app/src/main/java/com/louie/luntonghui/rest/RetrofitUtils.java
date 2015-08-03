package com.louie.luntonghui.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.louie.luntonghui.R;
import com.louie.luntonghui.net.OkHttpUtils;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;

import javax.inject.Singleton;

import dagger.Provides;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Administrator on 2015/6/18.
 */
public class RetrofitUtils {
    private static RestAdapter singleton;


    public static <T> T createApi(final Context context,Class<T> clazz){
        if(singleton == null){
            synchronized (RetrofitUtils.class){
                if(singleton == null){
                    RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(ConstantURL.HOST)
                    .setConverter(new GsonConverter(GsonUtils.newInstance())) //反射
                    /*.setErrorHandler(new ErrorHandler() {
                        @Override
                        public Throwable handleError(RetrofitError cause) {
                            RetrofitError.Kind kind = retrofitError.getKind();
                            String message;
                            if(RetrofitError.Kind.NETWORK.equals(kind)){
                                message = context.getString(R.string.network_error);
                            }else if(RetrofitError.Kind.HTTP.equals(kind)){
                                message = context.getString(R.string.http_error);
                            }else if(RetrofitError.Kind.CONVERSION.equals(kind)){
                                message = context.getString(R.string.conversion_error);
                            }else{
                                message = context.getString(R.string.unexpected_error);
                            }
                            return new Exception(message);
                        }
                    })*/
                    .setLogLevel(Config.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);
                    builder.setClient(new OkClient(OkHttpUtils.getInstance(context)));

                    singleton = builder.build();
                }
            }
        }
        return singleton.create(clazz);
    }
/*    @Provides
    @Singleton
    ErrorHandler provideErrorHandler(final Context ctx){
        return new ErrorHandler() {
            @Override
            public Throwable handleError(RetrofitError retrofitError) {
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
    }*/

}
