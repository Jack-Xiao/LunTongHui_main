package com.louie.luntonghui.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.louie.luntonghui.rest.RetrofitUtils;
import com.louie.luntonghui.rest.ServiceManager;

/**
 * Created by Louie on 2015/5/27.
 */
public class BaseActivity extends AppCompatActivity {
    protected ServiceManager.LunTongHuiApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = RetrofitUtils.createApi(this, ServiceManager.LunTongHuiApi.class);

    }
}
