package com.louie.luntonghui.ui.mine.MineService;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.MineServiceCostAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.MineServiceCostTable;
import com.louie.luntonghui.model.result.MineServiceCostResult;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.view.MyListView;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 15/7/30.
 */
public class MineServiceCostQueryActivity extends BaseNormalActivity {
    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.current_month_cost)
    TextView currentMonthCost;
    @InjectView(R.id.whole_cost)
    TextView wholeCost;
    @InjectView(R.id.customer_name)
    TextView customerName;
    @InjectView(R.id.customer_value)
    TextView customerValue;
    @InjectView(R.id.list_view)
    MyListView listView;
    private Context mContext;
    private MineServiceCostAdapter mAdapter;
    public static final int WHOLE_COST = 2;
    private ProgressDialog mProgress;
    private String curMonthFirstDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_service_cost);
        ButterKnife.inject(this);

        toolbarTitle.setText(R.string.mine_service_cost);
        mContext = this;

        mAdapter = new MineServiceCostAdapter(mContext);
        listView.setAdapter(mAdapter);
        curMonthFirstDay = Config.getFirstdayofThisMonth();
        String url = String.format(ConstantURL.MINE_SERVICE_COST,userId,WHOLE_COST);

        queryCost(url);

    }

    private void queryCost(String url) {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(getResources().getString(R.string.waiting_while));
        executeRequest(new GsonRequest(
                url, MineServiceCostResult.class, getWholeCostRequest(), errorListener()));
    }

    private Response.Listener<MineServiceCostResult> getWholeCostRequest() {
        return new Response.Listener<MineServiceCostResult>() {
            @Override
            public void onResponse(final MineServiceCostResult mineServiceCostResult) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {

                    @Override
                    protected Object doInBackground(Object... params) {
                        try{
                            new Delete()
                                    .from(MineServiceCostTable.class)
                                    .execute();

                            for(int i =0;i<mineServiceCostResult.listallcat.size();i++){
                                MineServiceCostTable cost = new MineServiceCostTable();
                                cost.curDate = mineServiceCostResult.listallcat.get(i).date;
                                cost.curProfit = mineServiceCostResult.listallcat.get(i).profit;
                                cost.save();
                            }
                        }finally {
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        currentMonthCost();
                    }
                });

            }
        };
    }
    @OnClick(R.id.current_month_cost)
    public void currentMonthCost(){
        wholeCost.setTextColor(getResources().getColor(R.color.order_font_normal));
        currentMonthCost.setTextColor(getResources().getColor(R.color.order_font_choose));
        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<MineServiceCostTable>>() {
            @Override
            protected void onPreExecute() {
                mProgress.show();
            }

            @Override
            protected List<MineServiceCostTable> doInBackground(Object... params) {
                /*List<MineServiceCostTable> list = new Select()
                        .from(MineServiceCostTable.class)
                        .where("date(cur_date) > date(?)", curMonthFirstDay)
                        .execute();*/
                List<MineServiceCostTable> list = new Select()
                        .from(MineServiceCostTable.class)
                        .where("cur_date >= ? ",curMonthFirstDay)
                        .execute();
                return list;
            }

            @Override
            protected void onPostExecute(List<MineServiceCostTable> list) {
                double total = 0.0;
                for(int i =0;i<list.size();i++){
                    total += Double.parseDouble(list.get(i).curProfit);
                }

                mAdapter.setData(list);
                BigDecimal b = new BigDecimal(total);
                double f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                customerName.setText(R.string.current_month_cost_total);
                customerValue.setText("￥"+f1);
                mProgress.dismiss();
            }
        });
    }

    @OnClick(R.id.whole_cost)
    public void onClickWholeCost(){
        wholeCost.setTextColor(getResources().getColor(R.color.order_font_choose));
        currentMonthCost.setTextColor(getResources().getColor(R.color.order_font_normal));
        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<MineServiceCostTable> >() {
            @Override
            protected void onPreExecute() {
                mProgress.show();
            }

            @Override
            protected List<MineServiceCostTable>  doInBackground(Object... params) {
                List<MineServiceCostTable> list = new Select()
                        .from(MineServiceCostTable.class)
                        .execute();
                return list;
            }

            @Override
            protected void onPostExecute(List<MineServiceCostTable> list) {
                double total = 0.0;
                for(int i =0;i<list.size();i++){
                    total += Double.parseDouble(list.get(i).curProfit);
                }
                BigDecimal b = new BigDecimal(total);
                double f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

                mAdapter.setData(list);
                customerName.setText(R.string.whole_cost_total);
                customerValue.setText("￥"+f1);
                mProgress.dismiss();
            }
        });
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
