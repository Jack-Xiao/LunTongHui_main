package com.louie.luntonghui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.louie.luntonghui.R;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.rest.RetrofitUtils;
import com.louie.luntonghui.rest.ServiceManager;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.ToastUtil;
import com.squareup.okhttp.Response;

import org.apache.http.HttpStatus;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by Louie on 2015/5/28.
 */
public class BaseFragment extends Fragment {
    protected ServiceManager.LunTongHuiApi mApi;
    protected String userId;
    public static final String SUCCESSCODE="000";


    public <T> T createApi(Class<T> cls) {
        return RetrofitUtils.createApi(getActivity(), cls);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        userId = DefaultShared.getString(RegisterLogin.USERUID,RegisterLogin.DEFAULT_USER_ID);

        mApi = RetrofitUtils.createApi(getActivity(), ServiceManager.LunTongHuiApi.class);
    }

    public static abstract class ResponseCallback<T> implements Callback<T> {
        private final WeakReference<Fragment> mRef;

        public ResponseCallback(Fragment fragment) {
            mRef = new WeakReference<Fragment>(fragment);
        }

        public Fragment getFragment() {
            return mRef.get();
        }

        @Override
        public void failure(RetrofitError error) {
            final Fragment fragment = mRef.get();
            retrofit.client.Response response = error.getResponse();

            ToastUtil.showShortToast(fragment.getActivity(), error.getMessage());

        }
    }


    protected com.android.volley.Response.ErrorListener errorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse == null){
                    if(getActivity()==null) return;
                    ToastUtil.showShortToast(getActivity(),R.string.network_connect_fail);
                    return;
                }
                switch (error.networkResponse.statusCode){
                    case HttpStatus.SC_NO_CONTENT:
                        ToastUtil.showLongToast(getActivity(), R.string.network_connect_fail);
                        break;
                    default:
                        ToastUtil.showLongToast(getActivity(), error.getMessage());
                        break;
                }
            }
        };
    }
    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }

}
