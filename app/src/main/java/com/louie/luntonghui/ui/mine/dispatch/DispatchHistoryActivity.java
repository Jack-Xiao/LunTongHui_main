package com.louie.luntonghui.ui.mine.dispatch;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.DispatchTodayAdapter;
import com.louie.luntonghui.model.result.DispatchToday;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.DispatchConfirmGathingDialogUtil;
import com.louie.luntonghui.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class DispatchHistoryActivity extends BaseDispatchActivity
        implements SwipeRefreshLayout.OnRefreshListener, DatePickerDialog.OnDateSetListener,
        DispatchTodayAdapter.OnConfirmListener, DispatchConfirmGathingDialogUtil.AlertDialogListener {
    @InjectView(R.id.dispatch_none)
    TextView dispatchNone;
    @InjectView(R.id.iv_item1)
    ImageView ivItem1;
    @InjectView(R.id.dispatch_has)
    TextView dispatchHas;
    @InjectView(R.id.iv_item2)
    ImageView ivItem2;
    @InjectView(R.id.dispatch_whole)
    TextView dispatchWhole;
    @InjectView(R.id.iv_item3)
    ImageView ivItem3;
    @InjectView(R.id.date_start)
    TextView dateStart;
    @InjectView(R.id.date_break_line)
    TextView dateBreakLine;
    @InjectView(R.id.date_end)
    TextView dateEnd;
    @InjectView(R.id.dispatch_info)
    RelativeLayout dispatchInfo;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private DispatchTodayAdapter mAdapter;
    private int dispatchType;
    private List<ImageView> ivList;
    private List<TextView> tvList;

    private int dateType;

    public static final int START_DATE = 1;
    public static final int END_DATE = 2;
    private ArrayList<DispatchToday.ListEntity> mData;
    public static final int INIT_PAGE = 1;
    private int hasLoadPage = INIT_PAGE;
    private int lastVisibleItem;

    private boolean isAllLoad = false;
    HashMap<String, Integer> mHashMap;
    public static final String STATE = "r_status";
    private boolean isLoadMore;
    private boolean isRunning = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mData = new ArrayList<>();
        mHashMap = new HashMap<>();
        initDatePick();
        init();
    }

    private void initDatePick() {
        String startDate = Config.getDateFormatString(new Date(System.currentTimeMillis()));
        String endDate = Config.getDateFormatString(new Date(System.currentTimeMillis()));

        dateStart.setText(startDate);
        dateEnd.setText(endDate);

    }

    private void init() {
        tvCenter.setText(getResources().getString(R.string.history_dispatch));
        mAdapter = new DispatchTodayAdapter(this);
        dispatchType = DISPATCH_NONE;


        tvList = new ArrayList<>();
        tvList.add(dispatchNone);
        tvList.add(dispatchHas);
        tvList.add(dispatchWhole);

        ivList = new ArrayList<>();
        ivList.add(ivItem1);
        ivList.add(ivItem2);
        ivList.add(ivItem3);
        onClickOrderNavigation(dispatchType);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        //.addOnScrollListener(mOnScrollListener);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);


        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefresh.setOnRefreshListener(this);

        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, HORIZONTAL_LIST,18,getResources().getColor(R.color.break_line_useful_normal)));

        recyclerView.addOnScrollListener(mOnScrollListener);
    }

    @OnClick(R.id.dispatch_whole)
    public void dispatchWhole() {
        isAllLoad = false;
        if(isRunning) return;
        hasLoadPage = INIT_PAGE;
        dispatchType = DISPATCH_WHOLE;
        mData.clear();
        onClickOrderNavigation(dispatchType);
        onSearch();
    }

    @OnClick(R.id.dispatch_none)
    public void dispatchNone() {
        isAllLoad = false;
        if(isRunning) return;
        hasLoadPage = INIT_PAGE;
        dispatchType = DISPATCH_NONE;
        mData.clear();
        onClickOrderNavigation(dispatchType);
        onSearch();
    }

    @OnClick(R.id.dispatch_has)
    public void dispatchHas() {
        isAllLoad = false;
        if(isRunning) return;
        hasLoadPage = INIT_PAGE;
        dispatchType = DISPATCH_HAS;
        mData.clear();
        onClickOrderNavigation(dispatchType);
        onSearch();
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

    public static void toUrl(Context context) {
        context.startActivity(new Intent(context, DispatchHistoryActivity.class));
    }

    @Override
    protected String getToolBarTitle() {
        return "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dispatch_history;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_history) {
            IntentUtil.startActivity(DispatchHistoryActivity.this, DispatchTodayActivity.class);
            this.finish();
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
        mData.clear();
        isAllLoad = false;
        hasLoadPage = 1;
        loadData(INIT_PAGE);
    }

    private void loadData(int page) {
        isRunning = true;
        String startDate = Config.getDispatchSearchDate(dateStart.getText().toString());
        String endDate = Config.getDispatchSearchDate(dateEnd.getText().toString());

        AppObservable.bindActivity(this,mApi.getHistoryDispatchDate(userId,startDate,endDate,
                page,Config.PAGE_SIZE,mHashMap))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private Observer<DispatchToday> observer = new Observer<DispatchToday>() {

        @Override
        public void onCompleted() {
            swipeRefresh.setRefreshing(false);
            isRunning = false;
        }

        @Override
        public void onError(Throwable e) {
            swipeRefresh.setRefreshing(false);
            ToastUtil.showShortToast(mContext, "获取数据失败");
            isRunning = false;
        }

        @Override
        public void onNext(DispatchToday listEntity) {
            if (listEntity.list.size() == Config.PAGE_SIZE) {
                hasLoadPage++;
            } else {
                isAllLoad = true;
            }
            isLoadMore = false;
            mData.addAll(listEntity.list);
            mAdapter.updateItems(mData);
        }
    };

    @OnClick(R.id.date_start)
    public void onClicDateStart() {
        dateType = START_DATE;
        Date date = Config.getDispatchDate(dateStart.getText().toString());
        showDatePick(date);

    }

    @OnClick(R.id.date_end)
    public void onClickDateEnd() {
        dateType = END_DATE;
        Date date = Config.getDispatchDate(dateStart.getText().toString());
        showDatePick(date);
    }

    void showDatePick(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        DatePickerDialog dialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String resultMonth;
        String resultDay;
        monthOfYear += 1;
        //%0-- 代表前面补0, 2 --- 代表总长度   d---整形
        resultMonth = String.format("%02d", monthOfYear);
        resultDay = String.format("%02d", dayOfMonth);

        String result = year + "/" + resultMonth + "/" + resultDay;
        switch (dateType) {
            case START_DATE:
                dateStart.setText(result);
                break;
            case END_DATE:

                dateEnd.setText(result);
                break;
        }

    }

    @OnClick(R.id.btn_search)
    public void onClickSearch() {
        if(isRunning) return;
        hasLoadPage = INIT_PAGE;
        mData.clear();
        onSearch();

    }

    private void onSearch() {
        mHashMap.remove(STATE);
        switch (dispatchType) {
            case DISPATCH_NONE:
                mHashMap.put(STATE, 0);
                break;
            case DISPATCH_HAS:
                mHashMap.put(STATE, 1);
                break;
            case DISPATCH_WHOLE:

                break;
        }
        loadData(hasLoadPage);

    }



    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                swipeRefresh.setRefreshing(true);
                loadMore();
            }
        }

    };

    private void loadMore() {
        if (isLoadMore){
            return;
        }
        if (isAllLoad) {
            Snackbar.make(swipeRefresh, "已全部加载完毕", Snackbar.LENGTH_SHORT)
                    .setAction("知道了", null)
                    .show();
            if(swipeRefresh!=null && swipeRefresh.isRefreshing()){
                swipeRefresh.setRefreshing(false);
            }
            return;
        }
        isLoadMore = true;
        onSearch();
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

                    }

                    @Override
                    public void onNext(Result result) {
                        ToastUtil.showShortToast(mContext,result.rsgmsg);
                        if(result.rsgcode.equals(BaseToolbarActivity.SUCCESS)){
                            for(int i =0;i<mData.size();i++){
                                if(mData.get(i).order_id.equals(orderId)){
                                    mData.get(i).r_status  = HAS_GET;
                                    break;
                                }
                            }
                            mAdapter.updateItems(mData);
                        }
                    }
                });
    }
}
