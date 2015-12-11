package com.louie.luntonghui.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.louie.luntonghui.R;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.rest.RetrofitUtils;
import com.louie.luntonghui.rest.ServiceManager;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.ToastUtil;

import org.apache.http.HttpStatus;

import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Administrator on 2015/7/1.
 */
public class BaseNormalActivity extends AppCompatActivity implements View.OnClickListener {
    public Context mContext;
    public static final String  SUCCESSCODE="000";

    public static final String SUCCESSCODE1 ="001";

    public static final String DEFAULT_ADDRESS_SELECT = "1";
    protected ServiceManager.LunTongHuiApi mApi;
    protected String userId;
    protected String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mApi = RetrofitUtils.createApi(mContext, ServiceManager.LunTongHuiApi.class);
        userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        userType = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT);
    }


    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse == null){
                    ToastUtil.showShortToast(mContext,R.string.error_network);
                    return;
                }

                switch (error.networkResponse.statusCode){
                    case HttpStatus.SC_NO_CONTENT:
                }
                ToastUtil.showLongToast(BaseNormalActivity.this, error.getMessage());
            }
        };
    }

    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_navigation:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
    }

    @Optional
    @OnClick(R.id.toolbar_navigation)
    public void onNavigationClick(View view){
        this.finish();
    }

    @Optional
    @OnClick(R.id.toolbar_cancel)
    public void onCancelClick(View view){
        this.finish();
    }
}
