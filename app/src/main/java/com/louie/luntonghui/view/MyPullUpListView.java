package com.louie.luntonghui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.louie.luntonghui.R;

/**
 * Created by Jack on 16/2/18.
 */
public class MyPullUpListView extends ListView implements AbsListView.OnScrollListener{
    private Context context;
    private View footerView = null;
    private MyPullUpListViewCallBack mCallBack;
    private int footerViewHeight; // 脚布局的高度

    private int firstVisibleItem;

    public MyPullUpListView(Context context) {
        this(context,null);
    }

    public MyPullUpListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initListView();
        initFooterView();
    }

    private void initFooterView() {
        footerView = View.inflate(getContext(),R.layout.view_footer,null);

        //footerView.measure(0,0);

        footerViewHeight = footerView.getMeasuredHeight();
        //footerView.setPadding(0,-footerViewHeight,0,0);
        footerView.setVisibility(GONE);
        this.addFooterView(footerView);
    }



    private void initListView() {
        setOnScrollListener(this);
        //去掉底部分割线
        setFooterDividersEnabled(false);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == OnScrollListener.SCROLL_STATE_IDLE && firstVisibleItem != 0
                && !isLoadingMore){
            mCallBack.scrollBottomState();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        if(footerView !=null){
            if(getLastVisiblePosition() == totalItemCount - 1){
                footerView.setVisibility(View.VISIBLE);//显示底部布局
                footerView.setPadding(0,0,0,0);
                footerViewHeight = footerView.getMeasuredHeight();
                isLoadingMore = false;
            }else{
                isLoadingMore = true;
                footerView.setVisibility(GONE);//隐藏底部布局
                footerViewHeight = footerView.getMeasuredHeight();
            }
        }
    }

    private boolean isLoadingMore = false;

    public void setMyPullUpListViewCallBack(MyPullUpListViewCallBack callBack){
        this.mCallBack = callBack;
    }

    public void hideFooterView(boolean b) {
        isLoadingMore = b;
        footerView.setVisibility(View.GONE);//隐藏底部布局
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0,-footerViewHeight,0,0);

    }

    public interface MyPullUpListViewCallBack{
        void scrollBottomState();
    }
}
