package com.louie.luntonghui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.OrderAdapter;
import com.louie.luntonghui.model.result.OrderList;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.view.ContentLoaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jack on 16/4/26.
 */
public class EachOrderFragment extends BaseFragment
        implements ContentLoaderView.OnRefreshListener,ContentLoaderView.OnMoreListener {
    @InjectView(R.id.content_loader)
    ContentLoaderView contentLoader;

    @InjectView(R.id.recycler)
    RecyclerView recyclerView;
    protected Context mContext;
    protected OrderAdapter mAdapter;
    protected String orderType;

    public static final String ORDER = "order";
    protected boolean hasLoaded = false;
    private List<OrderList.MysalelistEntity> mOrderList;
    protected int currentPage;
    protected int totalPage;
    public static final String TYPE = "type";

    public static final String ORDERTYPE_WHOLE = "0";
    public static final String ORDERTYPE_WAIT = "1";
    public static final String ORDERTYPE_SONG = "3";
    public static final String ORDERTYPE_FINISH = "11";
    public static final String ORDERTYPE_CANCEL = "9";
    protected boolean isAllLoad = false;
    private int currentPageSize;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mAdapter = new OrderAdapter(getActivity());
        mOrderList = new ArrayList();
        currentPageSize = Config.PAGE_SIZE;
    }

    public static EachOrderFragment newFragment(String type){
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        EachOrderFragment fragment = new EachOrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void loadData(int i) {
        currentPage = i;
        AppObservable.bindFragment(this,mApi.getOrderList(userId, i,currentPageSize,orderType))
                //.map(orderList -> orderList).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Observer<OrderList> observer = new Observer<OrderList>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            contentLoader.notifyLoadFailed(e);
        }

        @Override
        public void onNext(OrderList orderList) {

            int totalPage = (orderList.total_count / Config.PAGE_SIZE) + 1;
            hasLoaded = true;
            //contentLoader.setPage(currentPage, totalPage);
            if(orderList.mysalelist.size() < currentPageSize) isAllLoad = true;
            if(contentLoader == null)return;
            contentLoader.setLoadState(currentPage,isAllLoad);
            mOrderList.addAll(orderList.mysalelist);

            mAdapter.setData(mOrderList);

        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_each, container, false);
        ButterKnife.inject(this,view);

        mAdapter.clear();
        mOrderList.clear();
        isAllLoad = false;

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        contentLoader.setPageSize(currentPageSize);
        contentLoader.setAdapter(mAdapter);
        contentLoader.setOnRefreshListener(this);
        contentLoader.setMoreListener(this);
        parserArgument();
        loadData(1);
        return view;
    }

    protected void parserArgument() {
        Bundle bundle = getArguments();
        orderType = bundle.getString(TYPE);
    }

    @Override
    public void onRefresh(boolean fromSwipe) {
        reloadData();
    }

    protected void reloadData() {
        mOrderList.clear();
        loadData(1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onMore(int page) {
        loadData(page);
    }

}
