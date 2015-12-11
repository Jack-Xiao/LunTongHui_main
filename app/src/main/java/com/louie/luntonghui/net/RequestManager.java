package com.louie.luntonghui.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.louie.luntonghui.App;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/2.
 */
public class RequestManager {
    public static RequestQueue mRequestQueue = Volley.newRequestQueue(App.getContext());
    /*public static RequestQueue mRequestQueue = Volley.newRequestQueue(App.getContext(),
            new OkHttpStack(OkHttpUtils.getInstance(App.getContext())));*/

    private static ArrayList<Object> tags = new ArrayList<>();

    private RequestManager(){
        // no instance
    }

    public static void addRequest(Request<?> request,Object tag){
        if(tag !=null){
            request.setTag(tag);
            tags.add(tag);
        }
        mRequestQueue.add(request);
    }

    public static void cancelAll(Object tag){
        mRequestQueue.cancelAll(tag);
    }

    public static void cancelAll(){
        for(int i =0;i<tags.size();i++){
            mRequestQueue.cancelAll(tags.get(i));
        }
    }
}