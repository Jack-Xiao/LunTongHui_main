package com.louie.luntonghui.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.louie.luntonghui.R;
import com.louie.luntonghui.rest.RetrofitUtils;
import com.louie.luntonghui.rest.ServiceManager;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.DefaultShared;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Jack on 15/8/12.
 */
public abstract class BaseToolbarActivity  extends AppCompatActivity{
    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;
    protected TabLayout mTabLayout;
    protected boolean ishidden = false;

    protected ServiceManager.LunTongHuiApi mApi;
    protected String userId;
    protected String userType;
    public Context mContext;
    SystemBarTintManager tintManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        mAppBar = (AppBarLayout)findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar)findViewById(R.id.app_toolbar);

        if(mToolbar == null || mAppBar ==null){
            throw new IllegalStateException("no toolbar");
        }
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarClick();
            }
        });
        //mToolbar.setNavigationIcon(R.drawable.actionbar_back);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); //给左上角图标的左边加上一个返回的图标
            actionBar.setTitle(toolbarTitle());
            //actionBar.setDisplayShowHomeEnable(true); //使左上角图标是否显示,显示应用程序图标，对应id 为 android.R.id.home
            //actionBar.setDisplayShowCustomEnabled(true); //使用自定义的普通View能在title栏显示,即setCustomView能起作用
        }

        if(Build.VERSION.SDK_INT >=21){
            mAppBar.setElevation(10.6f);
        }
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.red_primary_dark);
        }*/

        mContext = this;
        mApi = RetrofitUtils.createApi(mContext, ServiceManager.LunTongHuiApi.class);
        userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        userType = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT);
    }

    public void onToolbarClick(){}

    abstract protected int toolbarTitle();
    abstract protected int getLayoutResource();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setAppBarAlpha(float alpha){
        mAppBar.setAlpha(alpha);
    }

    protected void hideOrShowToolbar(){
        mAppBar.animate()
                .translationY(ishidden ? 0 : -mAppBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        ishidden = !ishidden;
    }
}
