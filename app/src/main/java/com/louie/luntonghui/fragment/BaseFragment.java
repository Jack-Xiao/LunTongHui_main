package com.louie.luntonghui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.louie.luntonghui.R;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.rest.RetrofitUtils;
import com.louie.luntonghui.rest.ServiceManager;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.ToastUtil;

import org.apache.http.HttpStatus;

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
        userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);

        mApi = RetrofitUtils.createApi(getActivity(), ServiceManager.LunTongHuiApi.class);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        /*RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(this);*/
    }

    protected com.android.volley.Response.ErrorListener errorListener() {
        return error -> {
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
        };
    }
    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }
}
