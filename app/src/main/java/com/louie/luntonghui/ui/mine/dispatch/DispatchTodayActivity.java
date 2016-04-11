package com.louie.luntonghui.ui.mine.dispatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.DispatchTodayAdapter;
import com.louie.luntonghui.model.result.DispatchToday;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.DispatchConfirmGathingDialogUtil;
import com.louie.luntonghui.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.louie.luntonghui.view.RecyclerViewLinearLayoutViewItemDecoration.HORIZONTAL_LIST;

/**
 * Created by Jack on 16/3/28.
 */
public class DispatchTodayActivity extends BaseDispatchActivity
        implements SwipeRefreshLayout.OnRefreshListener,DispatchTodayAdapter.OnConfirmListener,
        DispatchConfirmGathingDialogUtil.AlertDialogListener{
    @InjectView(R.id.iv_item1)
    ImageView ivItem1;

    @InjectView(R.id.iv_item2)
    ImageView ivItem2;

    @InjectView(R.id.iv_item3)
    ImageView ivItem3;
    @InjectView(R.id.tv_gong)
    TextView tvGong;
    @InjectView(R.id.tv_count)
    TextView tvCount;
    @InjectView(R.id.tv_can)
    TextView tvCan;
    @InjectView(R.id.tv_go)
    TextView tvGo;
    @InjectView(R.id.recycler_view)
    android.support.v7.widget.RecyclerView recyclerView;
    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    DispatchTodayAdapter mAdapter;
    DispatchToday data;
    @InjectView(R.id.dispatch_none)
    TextView dispatchNone;
    @InjectView(R.id.dispatch_has)
    TextView dispatchHas;
    @InjectView(R.id.dispatch_whole)
    TextView dispatchWhole;
    @InjectView(R.id.chatMsg)
    TextView chatMsg;
    @InjectView(R.id.dispatch_info)
    RelativeLayout dispatchInfo;

    @InjectView(R.id.progress)
    ProgressBar mProgress;

    private List<ImageView> ivList;
    private List<TextView> tvList;

    private int dispatchType;
    private DispatchToday mCurrentData;
    private List<DispatchToday.ListEntity> notDispatchList;
    private List<DispatchToday.ListEntity> hasDispatchList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvCenter.setText(getResources().getString(R.string.today_dispatch));
        mAdapter = new DispatchTodayAdapter(this);


        tvList = new ArrayList<>();
        tvList.add(dispatchNone);
        tvList.add(dispatchHas);
        tvList.add(dispatchWhole);

        ivList = new ArrayList<>();
        ivList.add(ivItem1);
        ivList.add(ivItem2);
        ivList.add(ivItem3);

        notDispatchList = new ArrayList<>();
        hasDispatchList = new ArrayList<>();

        initRecyclerView();

        mProgress.setVisibility(View.VISIBLE);
        dispatchInfo.setVisibility(View.GONE);
        init();
        if(mProgress!=null) mProgress.setVisibility(View.GONE);
    }

    private void init() {
        dispatchType = DISPATCH_NONE;
        onClickOrderNavigation(dispatchType);
        reloadData();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        //.addOnScrollListener(mOnScrollListener);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);


        /*swipeRefresh.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
*/
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefresh.setOnRefreshListener(this);

        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, HORIZONTAL_LIST,18,getResources().getColor(R.color.break_line_useful_normal)));

        /*recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                DispatchToday.ListEntity entity = mAdapter.getData().get(position);

                DispatchDetailActivity.toStart(mContext, entity);
            }
        }));*/
    }

    Observer<DispatchToday> observable = new Observer<DispatchToday>() {
        @Override
        public void onCompleted() {
            swipeRefresh.setRefreshing(false);
            dispatchInfo.setVisibility(View.VISIBLE);
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(DispatchTodayActivity.this, "网络连接失败");
            swipeRefresh.setRefreshing(false);
            dispatchInfo.setVisibility(View.VISIBLE);
        }

        @Override
        public void onNext(DispatchToday dispatchToday) {
            if (dispatchToday.list.size() == 0) return;
            data = dispatchToday;
            filterData(data.list);
        }
    };

    private void filterData(List<DispatchToday.ListEntity> list) {
        notDispatchList.clear();
        hasDispatchList.clear();

        for(int i =0;i<list.size();i++){
            if(list.get(i).r_status.equals(NOT_GET)){
                notDispatchList.add(list.get(i));
            }else{
                hasDispatchList.add(list.get(i));
            }
        }
        switch (dispatchType){
            case DISPATCH_NONE:
                mAdapter.setData(notDispatchList);
                break;
            case DISPATCH_HAS:
                mAdapter.setData(hasDispatchList);
                break;
            case DISPATCH_WHOLE:
                mAdapter.setData(list);
                break;
        }

        tvCount.setText(list.size() + "");
        tvGo.setText(notDispatchList.size() + "");

    }
    private void onClickOrderNavigation(int type) {
        for (int i = 0; i < tvList.size(); i++) {
            if (type == i) {
                tvList.get(i).setTextColor(getResources().getColor(R.color.order_font_choose));
                ivList.get(i).setVisibility(View.VISIBLE);
            } else {
                tvList.get(i).setTextColor(getResources().getColor(R.color.order_font_normal));
                ivList.get(i).setVisibility(View.GONE);
            }
        }
    }


    @OnClick(R.id.dispatch_whole)
    public void dispatchWhole() {
        dispatchType = DISPATCH_WHOLE;
        onClickOrderNavigation(dispatchType);
        mAdapter.setData(data.list);

    }

    @OnClick(R.id.dispatch_none)
    public void dispatchNone() {
        dispatchType = DISPATCH_NONE;
        onClickOrderNavigation(dispatchType);
        mAdapter.setData(notDispatchList);
    }

    @OnClick(R.id.dispatch_has)
    public void dispatchHas() {
        dispatchType = DISPATCH_HAS;
        onClickOrderNavigation(dispatchType);
        mAdapter.setData(hasDispatchList);
    }

    @OnClick(R.id.chatMsg)
    public void onClickChatMsg(){
        DispatchTotalActivity.start(
                DispatchTodayActivity.this,(ArrayList<DispatchToday.ListEntity>) data.list);

    }

    @Override
    protected String getToolBarTitle() {
        return "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dispatch_today;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_today, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_today) {
            IntentUtil.startActivity(DispatchTodayActivity.this, DispatchHistoryActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        reloadData();
    }

    private void reloadData() {
        swipeRefresh.setRefreshing(true);
        getData();
    }

    private void getData() {
        AppObservable.bindActivity(this, mApi.getTodayDispatchDate(userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observable);
    }




    @Override
    public void confirmGathering(String explain, String applyMoney, final String orderId,String rType) {
        Map<String,String> map = new HashMap<>();
        if(!TextUtils.isEmpty(explain)){
            map.put(REMARK_EXPLAIN,explain);
        }
        AppObservable.bindActivity(this,mApi.confirmDelivery(
                userId,orderId,applyMoney,rType,map))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showLongToast(mContext,"网络连接失败");
            }

            @Override
            public void onNext(Result result) {
                ToastUtil.showShortToast(mContext,result.rsgmsg);
                if(result.rsgcode.equals(BaseToolbarActivity.SUCCESS)){
                    for(int i =0;i<data.list.size();i++){
                        if(data.list.get(i).order_id.equals(orderId)){
                            data.list.get(i).r_status  = HAS_GET;
                            break;
                        }
                    }

                    for(int i =0;i<notDispatchList.size();i++){
                        if(notDispatchList.get(i).order_id.equals(orderId)){
                            notDispatchList.get(i).r_status  = HAS_GET;
                            mAdapter.setData(notDispatchList);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void confirmOrder(String orderId, String orderSn, String consignee, String applyMoney) {
        DispatchConfirmGathingDialogUtil.getInstance()
                .setOrderId(orderId)
                .setConsignne(consignee)
                .setApplyMoney(applyMoney)
                .setOrderSn(orderSn)
                .show(this,this);
    }

    @Override
    public void onClickItemListener(View v, int position) {
        DispatchToday.ListEntity entity = mAdapter.getData().get(position);

        DispatchDetailActivity.toStart(mContext, entity);
    }
}
