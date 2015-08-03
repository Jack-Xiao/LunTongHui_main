package com.louie.luntonghui.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.louie.luntonghui.App;

/**
 * Created by Administrator on 2015/6/2.
 */
public class RequestManager {
    public static RequestQueue mRequestQueue = Volley.newRequestQueue(App.getContext());

    private RequestManager(){
        // no instance
    }

    public static void addRequest(Request<?> request,Object tag){
        if(tag !=null){
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }
    public static void cancelAll(Object tag){
        mRequestQueue.cancelAll(tag);
    }

}
