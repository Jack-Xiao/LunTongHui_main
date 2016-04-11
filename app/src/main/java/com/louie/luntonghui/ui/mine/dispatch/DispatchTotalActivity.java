package com.louie.luntonghui.ui.mine.dispatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.DispatchTotalAdapter;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.DispatchToday;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.IntentUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 16/4/7.
 */
public class DispatchTotalActivity extends BaseDispatchActivity {
    @InjectView(R.id.today_date)
    TextView todayDate;
    @InjectView(R.id.detail)
    TextView detail;
    @InjectView(R.id.deliveryer)
    TextView deliveryer;
    @InjectView(R.id.phoner)
    TextView phoner;
    @InjectView(R.id.dispatch_finish)
    TextView dispatchFinish;
    @InjectView(R.id.accept_cash)
    TextView acceptCash;
    @InjectView(R.id.dispatch_none)
    TextView dispatchNone;
    @InjectView(R.id.accept_cash_not)
    TextView acceptCashNot;
    @InjectView(R.id.list_view)
    ListView listView;
    private DispatchToday today;
    public static final String TOTAL_LIST = "total_list";
    private List<DispatchToday.ListEntity> mList;
    private DispatchTotalAdapter mAdapter;
    private int finishOrder = 0;
    private int notFinishOrder = 0;

    private double finishOrderPrice = 0;
    private double notFinishOrderPrice = 0;

    @Override
    protected String getToolBarTitle() {
        return "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dispatch_total;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        mList = bundle.getParcelableArrayList(TOTAL_LIST);
        tvCenter.setText("总览");
        initListView();
        init();

    }

    private void init() {
        User user = new Select()
                .from(User.class)
                .where("uid = ?", userId)
                .executeSingle();

        todayDate.setText(Config.getDispatctCurrenthDate());
        String username = "";
        String mobilePhone = "";
        if(user !=null) {
            username = user.username;
            mobilePhone = user.mobilePhone;
        }

        deliveryer.setText("配送员 : "+ username);
        phoner.setText("联系电话 : " + mobilePhone);
        dispatchFinish.setText("完成配送 : " + finishOrder + "单");
        acceptCash.setText("已收现金 : " + finishOrderPrice);

        dispatchNone.setText("尚未配送 : " + notFinishOrder + "单");
        acceptCashNot.setText("未收现金 : " + notFinishOrderPrice);

        listView.setAdapter(mAdapter);

    }

    private void initListView() {
        for(int i =0;i<mList.size();i++){
            if(mList.get(i).r_status.equals(BaseDispatchActivity.HAS_GET)){
                finishOrder++;
                finishOrderPrice += Double.parseDouble(mList.get(i).total_amount);
            }else{
                notFinishOrder++;
                notFinishOrderPrice += Double.parseDouble(mList.get(i).total_amount);
            }
        }
        //DecimalFormat df=new DecimalFormat("#.00");
        //df.format(finishOrderPrice);

        BigDecimal b   =   new   BigDecimal(finishOrderPrice);
        finishOrderPrice =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();

        BigDecimal b1   =   new   BigDecimal(notFinishOrderPrice);
        notFinishOrderPrice =   b1.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();

        mAdapter = new DispatchTotalAdapter(this, mList);

    }

    public static void start(Context context, ArrayList<DispatchToday.ListEntity> list) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TOTAL_LIST, list);
        Intent intent = new Intent(context, DispatchTotalActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @OnClick(R.id.detail)
    public void onClickDetail(){
        IntentUtil.startActivity(DispatchTotalActivity.this,DispatchTodayActivity.class);
        this.finish();
    }
}
