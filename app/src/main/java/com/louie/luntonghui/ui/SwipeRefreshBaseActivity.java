package com.louie.luntonghui.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.louie.luntonghui.R;
import com.louie.luntonghui.view.MultiSwipeRefreshLayout;

/**
 * Created by Jack on 15/8/12.
 */
public abstract class SwipeRefreshBaseActivity extends BaseToolbarActivity {
    MultiSwipeRefreshLayout mMultiSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupSwipeRefresh();

    }

    private void setupSwipeRefresh() {
        mMultiSwipeRefreshLayout = (MultiSwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        if(mMultiSwipeRefreshLayout !=null){
            mMultiSwipeRefreshLayout.setColorScheme(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            mMultiSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    requestDataRefresh();
                }
            });
        }
    }

    public void requestDataRefresh(){}
    public void setRefreshing(boolean refreshing){
        if(mMultiSwipeRefreshLayout == null) return;
        if(!refreshing){
            mMultiSwipeRefreshLayout.setRefreshing(false);
        }else{
            mMultiSwipeRefreshLayout.setRefreshing(true);
        }
    }
    public void setProgressViewOffset(boolean scale, int start, int end) {
        mMultiSwipeRefreshLayout.setProgressViewOffset(scale, start, end);
    }

    public void setSwipeableChildren(
            MultiSwipeRefreshLayout.CanChildScrollUpCallback canChildScrollUpCallback) {
        mMultiSwipeRefreshLayout.setCanChildScrollUpCallback(
                canChildScrollUpCallback
        );
    }
}
