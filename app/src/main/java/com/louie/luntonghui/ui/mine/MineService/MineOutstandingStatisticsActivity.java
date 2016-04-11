package com.louie.luntonghui.ui.mine.MineService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.MineRecyclerViewAdapter;
import com.louie.luntonghui.model.db.MineWorkQueryDay;
import com.louie.luntonghui.model.db.MineWorkQueryMonth;
import com.louie.luntonghui.model.db.MineWorkQueryYear;
import com.louie.luntonghui.model.db.Order;
import com.louie.luntonghui.model.result.SalesmanStaticDay;
import com.louie.luntonghui.model.result.SalesmanStaticMonth;
import com.louie.luntonghui.model.result.SalesmanStaticYear;
import com.louie.luntonghui.ui.BaseToolbarActivity1;
import com.louie.luntonghui.ui.order.DetailOrderActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.RecyclerItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jack on 16/2/29.
 */
public class MineOutstandingStatisticsActivity extends BaseToolbarActivity1 {
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.mine_month_outstanding_statistics)
    TextView mineMonthOutstandingStatistics;
    @InjectView(R.id.mine_outstanding_statistics)
    TextView mineOutstandingStatistics;
    @InjectView(R.id.mine_order_query)
    TextView mineOrderQuery;

    @InjectView(R.id.spinner_year)
    Spinner spinnerYear;

    @InjectView(R.id.spinner_month)
    Spinner spinnerMonth;

    @InjectView(R.id.spinner_day)
    Spinner spinnerDay;

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    MineRecyclerViewAdapter mAdapter;

    @InjectView(R.id.search_order)
    RelativeLayout searchOrder;
    @InjectView(R.id.iv_item1)
    ImageView ivItem1;
    @InjectView(R.id.iv_item2)
    ImageView ivItem2;
    @InjectView(R.id.iv_item3)
    ImageView ivItem3;
    private int type;
    private List<TextView> tvList;
    private ArrayAdapter<String> mYearAdapter;
    private ArrayAdapter<String> mMonthAdapter;
    private ArrayAdapter<String> mDayAdapter;
    private String[] years;
    private String[] months;
    private String[] days;
    private ProgressDialog mProgressDialog;
    private boolean isRunning = false;
    private boolean isArgument = false;
    private Drawable drawableBottom;
    private List<ImageView> ivList;

    @Override
    protected int getToolBarTitle() {
        return R.string.outstanding_statistics;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_outstanding_statistics;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = MineRecyclerViewAdapter.OUTSTANDING_STATISTICS_MONTH;
        mAdapter = new MineRecyclerViewAdapter(this, type);
        tvList = new ArrayList<>();
        tvList.add(mineMonthOutstandingStatistics);
        tvList.add(mineOutstandingStatistics);
        tvList.add(mineOrderQuery);
        ivList = new ArrayList<>();
        ivList.add(ivItem1);
        ivList.add(ivItem2);
        ivList.add(ivItem3);

        years = Config.getYearItems();
        months = Config.getMonthItems();
        days = Config.getDayItems();

        mYearAdapter = new ArrayAdapter<String>(
                this, R.layout.item_spinner_show, years);
        mMonthAdapter = new ArrayAdapter<String>(
                this, R.layout.item_spinner_show, months);
        mDayAdapter = new ArrayAdapter<String>(
                this, R.layout.item_spinner_show, days);

        mYearAdapter.setDropDownViewResource(R.layout.item_drop_down);

        mMonthAdapter.setDropDownViewResource(R.layout.item_drop_down);
        mDayAdapter.setDropDownViewResource(R.layout.item_drop_down);

        spinnerYear.setAdapter(mYearAdapter);
        spinnerMonth.setAdapter(mMonthAdapter);
        spinnerDay.setAdapter(mDayAdapter);
        mProgressDialog = new ProgressDialog(mContext);


        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        LinearLayoutManager l =
                new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(l);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int position) {
                        if (position == 0) return;//标题item
                        int curType = type;
                        ArrayList list = mAdapter.getList();
                        switch (curType) {
                            case MineRecyclerViewAdapter.ORDER_QUERY:
                                MineWorkQueryDay entityDay = (MineWorkQueryDay) list.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putString(Order.ORDERID, entityDay.order_id);
                                bundle.putInt(Order.QUERY_TYPE, Order.SERVICE_TYPE);
                                bundle.putString(Order.USER_ID, entityDay.user_id);
                                bundle.putInt(Order.DISPATCH_QUERY,Order.DISPATCH_NORMAL);

                                IntentUtil.startActivity(MineOutstandingStatisticsActivity.this,
                                        DetailOrderActivity.class, bundle);
                                break;
                            case MineRecyclerViewAdapter.OUTSTANDING_STATISTICS_MONTH:
                                if (position == 1) {
                                    ToastUtil.showShortToast(mContext, "请选择日期");
                                    return;
                                }
                                MineWorkQueryMonth entityMonth = (MineWorkQueryMonth) list.get(position);
                                String date = getTypeDay(entityMonth.date);
                                if (TextUtils.isEmpty(date)) {
                                    ToastUtil.showShortToast(mContext, "请选择有效时间查看");
                                    return;
                                }
                                onClickDay(date);
                                break;
                            case MineRecyclerViewAdapter.OUTSTANDING_STATISTICS:
                                MineWorkQueryYear entityYear = (MineWorkQueryYear) list.get(position);
                                String date1 = getTypeMonth1(entityYear.date);
                                if (TextUtils.isEmpty(date1)) {
                                    ToastUtil.showShortToast(mContext, "请选择有效时间查看");
                                    return;
                                }
                                onClickMonth(date1);
                                break;
                        }
                    }
                }));
        onCLickMonth();
    }

    public String getTypeDay(String curDate) {
        curDate = Config.getCurrentYear() + "年" + curDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(curDate);
            Date date2 = new Date(System.currentTimeMillis());
            if (date.after(date2)) {
                return "";
            }
            return format1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            ToastUtil.showShortToast(mContext, "数据解析错误");
            return "";
        }
    }

    public String getTypeMonth(String curDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
        try {
            Date date = format.parse(curDate);
            return format1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTypeMonth1(String curDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
        try {
            Date date = format.parse(curDate);
            return format1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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


    @OnClick(R.id.mine_month_outstanding_statistics)
    public void onCLickMonth() {
        if (isRunning) return;

        searchOrder.setVisibility(View.GONE);

        type = MineRecyclerViewAdapter.OUTSTANDING_STATISTICS_MONTH;
        onClickCurrentItem(type);
        mProgressDialog.dismiss();
    }

    @OnClick(R.id.mine_outstanding_statistics)
    public void onClicYear() {
        if (isRunning) return;

        searchOrder.setVisibility(View.GONE);
        type = MineRecyclerViewAdapter.OUTSTANDING_STATISTICS;
        onClickCurrentItem(type);
    }

    @OnClick(R.id.mine_order_query)
    public void onClickDay() {
        if (isRunning) return;

        searchOrder.setVisibility(View.VISIBLE);
        type = MineRecyclerViewAdapter.ORDER_QUERY;
        setSpinner(Config.getCurrentDate());
        onClickCurrentItem(type);
    }

    public void onClickDay(String date) {
        isArgument = true;

        searchOrder.setVisibility(View.VISIBLE);
        type = MineRecyclerViewAdapter.ORDER_QUERY;
        onClickOrderNavigation(type);
        mAdapter.setType(type);

        setSpinner(date);
        onClickSearch();
    }

    private void setSpinner(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String[] args = date.split("-");
            String year = args[0];
            String month = args[1];
            String day = args[2];
            setSpinnerYear(year);
            setSpinnerMonth(month);
            setSpinnerDay(day);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpinnerDay(String day) {
        String[] resource = Config.getDayItems();
        for (int i = 0; i < resource.length - 1; i++) {
            if (day.equals(resource[i])) {
                spinnerDay.setSelection(i, true);
            }
        }
    }

    private void setSpinnerMonth(String month) {
        String[] resource = Config.getMonthItems();
        for (int i = 0; i < resource.length - 1; i++) {
            if (month.equals(resource[i])) {
                spinnerMonth.setSelection(i, true);
            }
        }
    }

    private void setSpinnerYear(String year) {
        String[] resource = Config.getYearItems();
        for (int i = 0; i < resource.length - 1; i++) {
            if (year.equals(resource[i])) {
                spinnerYear.setSelection(i, true);
                return;
            }
        }
    }

    public void onClickMonth(String date) {
        isArgument = true;

        searchOrder.setVisibility(View.GONE);

        type = MineRecyclerViewAdapter.OUTSTANDING_STATISTICS_MONTH;
        //onClickCurrentItem(type);
        onClickOrderNavigation(type);
        mAdapter.setType(type);
        queryMonth(date);
    }

    public void onClickCurrentItem(int type) {
        isRunning = true;
        onClickOrderNavigation(type);
        mAdapter.setType(type);
        mAdapter.clear();
        //mAdapter = new MineRecyclerViewAdapter(this,type);
        switch (type) {
            case MineRecyclerViewAdapter.OUTSTANDING_STATISTICS:
                queryYear(Config.getCurrentYear());
                break;
            case MineRecyclerViewAdapter.ORDER_QUERY:

                queryDay(Config.getCurrentDate());
                break;
            case MineRecyclerViewAdapter.OUTSTANDING_STATISTICS_MONTH:
                queryMonth(Config.getCurrentMonth());
                break;
        }
    }

    private void queryDay(String currentDate) {
        AppObservable.bindActivity(this, mApi.getSalesDay(userId, currentDate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SalesmanStaticDay>() {
                    @Override
                    public void onCompleted() {
                        isRunning = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showLongToast(mContext, "获取数据失败");
                        isRunning = false;
                        if (isArgument) {

                            type = MineRecyclerViewAdapter.OUTSTANDING_STATISTICS_MONTH;
                            onClickOrderNavigation(type);
                            mAdapter.setType(type);
                        }
                        isArgument = false;
                        mAdapter.clear();
                    }

                    @Override
                    public void onNext(SalesmanStaticDay data) {
                        ArrayList list = new ArrayList();
                        if (data != null && data.list.size() == 0) {
                            ToastUtil.showLongToast(mContext, "该日没有数据");
                            mAdapter.clear();
                            return;
                        }
                        MineWorkQueryDay day1 = new MineWorkQueryDay();
                        day1.user_name = "用户";
                        day1.order_sn = "订单号";
                        day1.goods_amount = "金额";
                        day1.user_id = "";
                        day1.order_id = "";
                        list.add(day1);
                        for (int i = 0; i < data.list.size(); i++) {
                            MineWorkQueryDay day = new MineWorkQueryDay();
                            day.goods_amount = data.list.get(i).goods_amount;
                            day.order_sn = data.list.get(i).order_sn;
                            day.order_id = data.list.get(i).order_id;
                            day.user_name = data.list.get(i).user_name;
                            day.user_id = data.list.get(i).user_id;
                            list.add(day);
                        }
                        mAdapter.setList(list);
                    }
                });
    }

    private void queryMonth(String curMonth) {
        AppObservable.bindActivity(this, mApi.getSaleMonth(userId, curMonth))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SalesmanStaticMonth>() {
                    @Override
                    public void onCompleted() {
                        isRunning = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showLongToast(mContext, "获取数据失败");
                        if (isArgument) {
                            isRunning = false;

                            type = MineRecyclerViewAdapter.OUTSTANDING_STATISTICS;
                            onClickCurrentItem(type);
                            mAdapter.setType(type);
                            isArgument = false;
                        }
                        mAdapter.clear();
                    }

                    @Override
                    public void onNext(SalesmanStaticMonth salesmanStaticMonth) {
                        if (salesmanStaticMonth.list == null || salesmanStaticMonth.list.size() == 0) {
                            ToastUtil.showShortToast(mContext, "该月没有数据");
                            mAdapter.clear();
                            return;
                        }

                        ArrayList list = new ArrayList();
                        MineWorkQueryMonth m = new MineWorkQueryMonth();
                        /*m.order_amount = salesmanStaticMonth.list.get(0).order_amount + "";
                        m.no_amount = salesmanStaticMonth.list.get(0).no_amount + "";
                        m.date = salesmanStaticMonth.list.get(0).date + "";
                        m.no_amount_a = salesmanStaticMonth.list.get(0) + "";
                        m.total_order_amount = salesmanStaticMonth.total_order_amount + "";*/
                        m.date = "日期";
                        m.order_amount = "订单总额";
                        m.no_amount_a = "电池";
                        m.no_amount_b = "整车";
                        list.add(m);
                        for (int i = 0; i < salesmanStaticMonth.list.size(); i++) {
                            MineWorkQueryMonth month = new MineWorkQueryMonth();
                            month.order_amount = salesmanStaticMonth.list.get(i).order_amount + "";
                            month.no_amount = salesmanStaticMonth.list.get(i).no_amount + "";
                            month.date = salesmanStaticMonth.list.get(i).date + "";
                            month.no_amount_a = salesmanStaticMonth.list.get(i).no_amount_a + "";
                            month.no_amount_b = salesmanStaticMonth.list.get(i).no_amount_b + "";
                            list.add(month);
                        }
                        mAdapter.setList(list);
                    }
                });
    }

    private void queryYear(String currentYear) {
        AppObservable.bindActivity(this, mApi.getSaleYear(userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SalesmanStaticYear>() {
                    @Override
                    public void onCompleted() {
                        isRunning = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showLongToast(mContext, "获取数据失败");
                        isRunning = false;
                        mAdapter.clear();
                    }

                    @Override
                    public void onNext(SalesmanStaticYear data) {
                        ArrayList list = new ArrayList();
                        if (data != null && data.list.size() == 0) {
                            ToastUtil.showShortToast(mContext, "该年没有数据");
                        }
                        MineWorkQueryYear m = new MineWorkQueryYear();
                        m.date = "日期";
                        m.order_amount = "订单总额";
                        m.no_amount_a = "电池";
                        m.no_amount_b = "整车";
                        list.add(m);

                        for (int i = 0; i < data.list.size(); i++) {
                            MineWorkQueryYear year = new MineWorkQueryYear();
                            year.date = data.list.get(i).date.replace("月","");
                            year.no_amount = data.list.get(i).no_amount + "";
                            year.order_amount = data.list.get(i).order_amount + "";
                            year.no_amount_a = data.list.get(i).no_amount_a + "";
                            year.no_amount_b = data.list.get(i).no_amount_b + "";

                            list.add(year);
                        }
                        mAdapter.setList(list);
                    }
                });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MineOutstandingStatisticsActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.btn_search)
    public void onClickSearch() {
        if (isRunning) return;

        String year = spinnerYear.getSelectedItem().toString();
        String month = spinnerMonth.getSelectedItem().toString();
        String day = spinnerDay.getSelectedItem().toString();

        String date = year + "-" + month + "-" + day;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(System.currentTimeMillis());
        try {
            Date date2 = format.parse(date);
            if (date2.after(date1)) {
                ToastUtil.showShortToast(mContext, "无效查询时间");
                mAdapter.clear();
                return;
            }

            queryDay(format.format(date2));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}