package com.louie.luntonghui.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.louie.luntonghui.R;
import com.louie.luntonghui.rest.RetrofitUtils;
import com.louie.luntonghui.rest.ServiceManager;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.DefaultShared;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jack on 16/2/27.
 */
public abstract class BaseToolbarActivity1 extends AppCompatActivity {
    @InjectView(R.id.app_toolbar)
    Toolbar mToolbar;
    public ActionBar mActionBar;
    public Context mContext;

    protected ServiceManager.LunTongHuiApi mApi;
    protected String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
        mContext = this;

        initView();
        initRetrofit();
    }

    private void initRetrofit() {
        mContext = this;
        mApi = RetrofitUtils.createApi(mContext, ServiceManager.LunTongHuiApi.class);
        userId = DefaultShared.getString(RegisterLogin.USERUID,
                RegisterLogin.DEFAULT_USER_ID);

    }

    private void initView() {

        this.setSupportActionBar(mToolbar);
        mActionBar = this.getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setTitle(getToolBarTitle());
    }


    protected RecyclerView getRecyclerView(RecyclerView recyclerView) {
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return recyclerView;
    }

    protected abstract int getToolBarTitle();


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
