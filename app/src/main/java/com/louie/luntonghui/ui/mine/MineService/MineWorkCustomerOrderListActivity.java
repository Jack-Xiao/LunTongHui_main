package com.louie.luntonghui.ui.mine.MineService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.android.volley.Response;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.MineWorkCustomerOrderListAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.MineServiceOrderTable;
import com.louie.luntonghui.model.result.MineServiceOrderListResult;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 15/12/8.
 */
public class MineWorkCustomerOrderListActivity extends BaseNormalActivity {
    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.customer_today_order)
    TextView customerTodayOrder;
    @InjectView(R.id.customer_yesterday_order)
    TextView customerYesterdayOrder;
    @InjectView(R.id.customer_total_order)
    TextView customerTotalOrder;
    @InjectView(R.id.progress)
    ProgressBar progress;
    private MineWorkCustomerOrderListActivity mContext;
    private MineWorkCustomerOrderListAdapter mAdapter;
    private View footView;
    public static final int loadingDataCount = 40;
    private int currentNeedLoadPosition = 40;
    private int index;
    public int totalCount;
    private List<MineServiceOrderTable> list;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_list);
        ButterKnife.inject(this);
        mContext = this;

        mAdapter = new MineWorkCustomerOrderListAdapter(mContext);

        footView = LayoutInflater.from(mContext).inflate(R.layout.view_footer, null);
        listView.setAdapter(mAdapter);

        onClickTodayOrder();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(index !=3) return;
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int last = view.getLastVisiblePosition();
                    //ToastUtil.showShortToast(mContext, "正在加载..");

                    //if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if ( last == totalCount -1 ) {
                        ToastUtil.showShortToast(mContext, R.string.loading_the_end);
                        return;
                    }


                    if (mAdapter != null) {
                        int adapterCnt =listView.getCount() - 1;

                        if (adapterCnt == last) {
                            listView.addFooterView(footView);
                            loadData();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //currentNeedLoadPosition = totalItemCount - 1;
                Log.d("currentNeedLo,,", totalItemCount + "  ");
                //if(currentNeedLoadPosition ==  )
            }
        });
    }

    public synchronized void loadData() {
        currentPage +=1;
        String url = String.format(ConstantURL.MINE_WORK_CUSTOMER_ORDER, userId,3, currentPage, loadingDataCount);
        executeRequest(new GsonRequest(url, MineServiceOrderListResult.class, getWholeRequest(), errorListener()));
    }

    private Response.Listener<MineServiceOrderListResult> getWholeRequest() {
        return new Response.Listener<MineServiceOrderListResult>() {
            @Override
            public void onResponse(final MineServiceOrderListResult mineServiceOrderListResult) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<MineServiceOrderTable>>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        progress.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected List<MineServiceOrderTable> doInBackground(Object... params) {
                        List<MineServiceOrderTable> list = new ArrayList<MineServiceOrderTable>();
                        if (mineServiceOrderListResult != null && mineServiceOrderListResult.mysalelist
                                != null && mineServiceOrderListResult.mysalelist.size() > 0) {
                            try {
                                MineServiceOrderListResult.MysalelistEntity result;
                                for (int i = 0; i < mineServiceOrderListResult.mysalelist.size(); i++) {
                                    result = mineServiceOrderListResult.mysalelist.get(i);
                                    MineServiceOrderTable order = new MineServiceOrderTable();
                                    order.allowToModify = result.allow_to_modify;
                                    order.handler = result.handler;
                                    order.userName = result.user_name;
                                    order.userId = result.user_id;
                                    order.mobilePhone = result.mobile_phone;
                                    order.money = result.money;
                                    order.payName = result.pay_name;
                                    order.orderId = result.order_id;
                                    order.orderSn = result.order_sn;
                                    order.orderAmount = result.order_amount;
                                    order.addTime = result.add_time;
                                    order.totalCount = result.total_count;
                                    order.serviceFee = result.service_fee;
                                    order.save();
                                    list.add(order);
                                }
                            } finally {

                            }
                        }
                        return list;
                    }

                    @Override
                    protected void onPostExecute(List<MineServiceOrderTable> list) {
                        super.onPostExecute(list);
                        //listView.removeFooterView(footView);
                        footView.setVisibility(View.GONE);
                        progress.setVisibility(View.GONE);
                        if (list != null && list.size() > 0) {
                            totalCount = Integer.parseInt(list.get(0).totalCount);
                        }
                        mAdapter.addData(list);
                    }
                });
            }
        };
    }

    @OnClick(R.id.customer_today_order)
    public void onClickTodayOrder() {
        index = 1;
        resetNatFontColor(index);
        queryData(index);

    }

    @OnClick(R.id.customer_yesterday_order)
    public void onClickYesterdayOrder() {
        index = 2;
        resetNatFontColor(index);
        queryData(index);

    }

    @OnClick(R.id.customer_total_order)
    public void onClickTotalOrder() {
        index = 3;
        currentPage = 0;
        mAdapter.clearData();
        resetNatFontColor(index);
        loadData();
    }

    private void queryData(int index) {
        listView.setVerticalScrollbarPosition(0);
        String url;
        if (index == 1 || index == 2) {
            url = String.format(ConstantURL.MINE_WORK_CUSTOMER_ORDER_DAY, userId, index);
            executeRequest(new GsonRequest(url, MineServiceOrderListResult.class, orderRequest(), errorListener()));
        }
    }

    private Response.Listener<MineServiceOrderListResult> orderRequest() {
        return new Response.Listener<MineServiceOrderListResult>() {
            @Override
            public void onResponse(final MineServiceOrderListResult mineServiceOrderListResult) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<MineServiceOrderTable>>() {
                    @Override
                    protected void onPreExecute() {
                        progress.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }

                    @Override
                    protected List<MineServiceOrderTable> doInBackground(Object... params) {

                        list = new ArrayList<>();
                        new Delete()
                                .from(MineServiceOrderTable.class)
                                .execute();
                        if (mineServiceOrderListResult != null && mineServiceOrderListResult.mysalelist
                                != null && mineServiceOrderListResult.mysalelist.size() > 0) {
                            try {

                                MineServiceOrderListResult.MysalelistEntity result;
                                for (int i = 0; i < mineServiceOrderListResult.mysalelist.size(); i++) {
                                    result = mineServiceOrderListResult.mysalelist.get(i);
                                    MineServiceOrderTable order = new MineServiceOrderTable();
                                    order.allowToModify = result.allow_to_modify;
                                    order.handler = result.handler;
                                    order.userName = result.user_name;
                                    order.userId = result.user_id;
                                    order.mobilePhone = result.mobile_phone;
                                    order.money = result.money;
                                    order.payName = result.pay_name;
                                    order.orderId = result.order_id;
                                    order.orderSn = result.order_sn;
                                    order.orderAmount = result.order_amount;
                                    order.addTime = result.add_time;
                                    order.totalCount = result.total_count;
                                    order.serviceFee = result.service_fee;
                                    order.save();
                                    list.add(order);
                                }
                            } finally {
                            }
                        }
                        return list;
                    }

                    @Override
                    protected void onPostExecute(List<MineServiceOrderTable> list) {
                        progress.setVisibility(View.GONE);
                        if (list != null && list.size() > 0) {
                            totalCount = Integer.parseInt(list.get(0).totalCount);
                        }
                        listView.setVisibility(View.VISIBLE);
                        mAdapter.setData(list);
                    }
                });
            }
        };
    }

    public void resetNatFontColor(int index) {
        if (index == 1) {
            customerTodayOrder.setTextColor(getResources().getColor(R.color.order_font_choose));
            customerYesterdayOrder.setTextColor(getResources().getColor(R.color.order_font_normal));
            customerTotalOrder.setTextColor(getResources().getColor(R.color.order_font_normal));
        } else if (index == 2) {
            customerTodayOrder.setTextColor(getResources().getColor(R.color.order_font_normal));
            customerYesterdayOrder.setTextColor(getResources().getColor(R.color.order_font_choose));
            customerTotalOrder.setTextColor(getResources().getColor(R.color.order_font_normal));
        } else if (index == 3) {
            customerTodayOrder.setTextColor(getResources().getColor(R.color.order_font_normal));
            customerYesterdayOrder.setTextColor(getResources().getColor(R.color.order_font_normal));
            customerTotalOrder.setTextColor(getResources().getColor(R.color.order_font_choose));
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
