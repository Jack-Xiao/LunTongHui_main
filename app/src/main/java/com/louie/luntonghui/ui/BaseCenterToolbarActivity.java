package com.louie.luntonghui.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.rest.RetrofitUtils;
import com.louie.luntonghui.rest.ServiceManager;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.DefaultShared;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jack on 16/4/27.
 */
public abstract class BaseCenterToolbarActivity extends AppCompatActivity {
    @InjectView(R.id.app_toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.tv_toolbar_center)
    TextView tvCenter;

    public ActionBar mActionBar;
    public Context mContext;

    protected ServiceManager.LunTongHuiApi mApi;
    protected String userId;

    public static final String SUCCESS = BaseNormalActivity.SUCCESSCODE;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);

        mApi = RetrofitUtils.createApi(mContext, ServiceManager.LunTongHuiApi.class);
        userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);

        initRetrofit();
        initView();
    }

    private void initRetrofit() {
        mContext = this;
        mApi = RetrofitUtils.createApi(mContext, ServiceManager.LunTongHuiApi.class);
        userId = DefaultShared.getString(RegisterLogin.USERUID,
                RegisterLogin.DEFAULT_USER_ID);

    }

    private void initView() {
        mToolbar.setTitle("");
        this.setSupportActionBar(mToolbar);
        mActionBar = this.getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
        //mActionBar.setTitle(getToolBarTitle());
        tvCenter.setText(getToolBarTitle());
    }


    protected abstract String getToolBarTitle();
    protected abstract int getLayoutId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
