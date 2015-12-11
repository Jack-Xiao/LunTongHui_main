package com.louie.luntonghui.data;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.louie.luntonghui.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * Created by Administrator on 2015/6/3.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson mGson = new Gson();
    private final Class<T> mClazz;
    private final Response.Listener<T> mListener;
    private final Map<String,String> mHeaders;

    public static final int DEFAULT_TIMEOUT_MS = 10 * 1000;
    public static final int DEFAULT_MAX_RETRIES = 5;
    public static final float DEFAULT_BACKOFF_MULT = 5.0F;

    public GsonRequest(String url,Class<T> clazz,Response.Listener<T> listener,Response.ErrorListener errorListener){
        this(Method.GET,url,clazz,null,listener,errorListener);
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.mClazz = clazz;
        this.mHeaders = headers;
        this.mListener = listener;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        try{
            mListener.onResponse(response);
        }catch (Exception e){
            if(BuildConfig.DEBUG) {
                //Log.d("GsonRequest", e == null ? "" : e.getMessage());
            }
        }
    }

    // post
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(mGson.fromJson(json,mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    //超时设置
    @Override
    public RetryPolicy getRetryPolicy() {
        /*RetryPolicy retryPolicy = new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS,
                                 DEFAULT_MAX_RETRIES,DEFAULT_BACKOFF_MULT);*/

        RetryPolicy retryPolicy = new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS,
                                      DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                      DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return retryPolicy;
    }
}
