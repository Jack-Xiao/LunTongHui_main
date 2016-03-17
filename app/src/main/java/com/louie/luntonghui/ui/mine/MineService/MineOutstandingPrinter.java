package com.louie.luntonghui.ui.mine.MineService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.MineRecyclerViewAdapter;
import com.louie.luntonghui.model.db.PrinterData;
import com.louie.luntonghui.model.result.PrinterDay;
import com.louie.luntonghui.model.result.PrinterMonth;
import com.louie.luntonghui.ui.BaseToolbarActivity1;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ToastUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jack on 16/3/1.
 */
public class MineOutstandingPrinter extends BaseToolbarActivity1 {
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.mine_printer_today)
    TextView minePrinterToday;
    @InjectView(R.id.mine_printer_month)
    TextView minePrinterMonth;
    @InjectView(R.id.mine_printer_day)
    TextView minePrinterDay;
    @InjectView(R.id.spinner_year)
    Spinner spinnerYear;
    @InjectView(R.id.tv_year)
    TextView tvYear;
    @InjectView(R.id.spinner_month)
    Spinner spinnerMonth;
    @InjectView(R.id.tv_month)
    TextView tvMonth;
    @InjectView(R.id.spinner_day)
    Spinner spinnerDay;
    @InjectView(R.id.tv_day)
    TextView tvDay;
    @InjectView(R.id.btn_search)
    Button btnSearch;
    @InjectView(R.id.search_order)
    RelativeLayout searchOrder;
    @InjectView(R.id.item1)
    TextView item1;
    @InjectView(R.id.item2)
    TextView item2;
    @InjectView(R.id.item3)
    TextView item3;
    @InjectView(R.id.item4)
    TextView item4;
    @InjectView(R.id.item5)
    TextView item5;

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    ProgressDialog mProgress;

    private ArrayList<TextView> tvList;
    private int type;
    public static final int SELECT_MONTH = 1;
    public static final int SELECT_DAY = 2;

    private MineRecyclerViewAdapter mAdapter;
    private ArrayAdapter<String> mYearAdapter;
    private ArrayAdapter<String> mMonthAdapter;
    private ArrayAdapter<String> mDayAdapter;
    private String[] years;
    private String[] months;
    private String[] days;
    private boolean isRunning = false;
    @Override
    protected int getToolBarTitle() {
        return R.string.mine_outstanding_printer;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_outstanding_printer;
    }

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, MineOutstandingPrinter.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);

        mAdapter = new MineRecyclerViewAdapter(this,MineRecyclerViewAdapter.MINE_PRINTER);
        tvList = new ArrayList<TextView>();
        tvList.add(minePrinterToday);
        tvList.add(minePrinterMonth);
        tvList.add(minePrinterDay);

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

        LinearLayoutManager l =
                new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(l);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);
        mProgress = new ProgressDialog(mContext);

        initData();

    }

    private void initData() {
        onClickPrinterToday();

    }

    private void onClickOrderNavigation(int type) {
        for (int i = 0; i < tvList.size(); i++) {
            if (type == i) {
                tvList.get(i).setTextColor(getResources().getColor(R.color.order_font_choose));
            } else {
                tvList.get(i).setTextColor(getResources().getColor(R.color.order_font_normal));
            }
        }
    }

    @OnClick(R.id.mine_printer_today)
    public void onClickPrinterToday(){
        String todayDate = Config.getCurrentDate();
        searchOrder.setVisibility(View.GONE);
        onClickOrderNavigation(0);
        mAdapter.clear();
        searchDay(todayDate);
    }

    @OnClick(R.id.mine_printer_day)
    public void onClickPrinterDay(){
        searchOrder.setVisibility(View.VISIBLE);
        type = 2;
        mAdapter.clear();
        onClickOrderNavigation(type);
        //spinnerDay.setVisibility(View.VISIBLE);1
        spinnerDay.setVisibility(View.VISIBLE);
        tvDay.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.mine_printer_month)
    public void onClickPrinterMonth(){
        searchOrder.setVisibility(View.VISIBLE);
        type = 1; //
        mAdapter.clear();
        onClickOrderNavigation(type);
        spinnerDay.setVisibility(View.GONE);
        tvDay.setVisibility(View.GONE);

        String monthDate = Config.getCurrentMonth();
        setMonth(monthDate);
        searchMonth(monthDate);
    }

    private void setMonth(String date) {
        try {
            String[] args = date.split("-");
            String year = args[0];
            String month = args[1];
            setSpinnerYear(year);
            setSpinnerMonth(month);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDay(String date){
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
        for(int i = 0;i<resource.length - 1;i++){
            if(day.equals(resource[i])){
                spinnerDay.setSelection(i,true);
            }
        }
    }

    private void setSpinnerMonth(String month) {
        String[] resource = Config.getMonthItems();
        for(int i = 0; i<resource.length - 1;i++){
            if(month.equals(resource[i])){
                spinnerMonth.setSelection(i,true);
            }
        }
    }

    private void setSpinnerYear(String year) {
        String [] resource = Config.getYearItems();
        for(int i =0;i<resource.length-1;i++){
            if(year.equals(resource[i])){
                spinnerYear.setSelection(i,true);
                return;
            }
        }
    }

    @OnClick(R.id.btn_search)
    public void onClickSearch(){
        String year = spinnerYear.getSelectedItem().toString();
        String month = spinnerMonth.getSelectedItem().toString();
        String day = spinnerDay.getSelectedItem().toString();

        switch (type){
            case SELECT_DAY:
                String date = year + "-" + month + "-" + day;
                searchDay(date);
                break;
            case SELECT_MONTH:
                String date1 = year + "-" + month;
                searchMonth(date1);
                break;
        }
    }

    private void searchMonth(String date1) {
        showProgress();
        AppObservable.bindActivity(this,mApi.getPrinterMonth(date1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PrinterMonth>() {
                    @Override
                    public void onCompleted() {
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        mAdapter.clear();
                        ToastUtil.showShortToast(mContext,"获取排行榜信息失败");
                    }

                    @Override
                    public void onNext(PrinterMonth printerDay) {
                        if(printerDay == null){
                            mAdapter.clear();
                            return;
                        }
                        ArrayList list = new ArrayList();
                        for(int i =0;i<printerDay.list.size();i++){
                            PrinterData entity = new PrinterData();
                            entity.username = printerDay.list.get(i).user_name;
                            entity.totalOrder = printerDay.total.order_amount + "";
                            entity.orderA = printerDay.list.get(i).goods_amount2_a + "";
                            entity.orderB = printerDay.list.get(i).goods_amount2_b + "";
                            entity.money = printerDay.list.get(i).amount_diff + "";
                            list.add(entity);
                        }
                        mAdapter.setList(list);
                    }
                });

    }

    public void showProgress(){
        if(mProgress !=null ){
            mProgress.show();
        }
    }

    public void hideProgress(){
        if(mProgress !=null && mProgress.isShowing()){
            mProgress.hide();
        }
    }


    private void searchDay(String date) {
        showProgress();
        AppObservable.bindActivity(this,mApi.getPrinterDay(date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PrinterDay>() {
                    @Override
                    public void onCompleted() {
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        mAdapter.clear();
                        ToastUtil.showShortToast(mContext,"获取排行榜信息失败");
                    }

                    @Override
                    public void onNext(PrinterDay printerDay) {
                        if(printerDay == null) return;

                        if(printerDay.list == null){
                            ToastUtil.showShortToast(mContext,"还没有数据");
                            mAdapter.clear();
                            return;
                        }

                        ArrayList list = new ArrayList();
                        for(int i =0;i<printerDay.list.size();i++){
                            PrinterData entity = new PrinterData();
                            entity.username = printerDay.list.get(i).user_name;
                            entity.totalOrder = printerDay.total.order_amount + "";
                            entity.orderA = printerDay.list.get(i).goods_amount2_a + "";
                            entity.orderB = printerDay.list.get(i).goods_amount2_b + "";
                            entity.money = printerDay.list.get(i).amount_diff + "";
                            list.add(entity);
                        }
                        mAdapter.setList(list);
                    }
                });
    }
}
