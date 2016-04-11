package com.louie.luntonghui.ui.mine.dispatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.DispatchDetailAdapter;
import com.louie.luntonghui.model.result.DispatchDetail;
import com.louie.luntonghui.model.result.DispatchToday;
import com.louie.luntonghui.util.ToastUtil;

import butterknife.InjectView;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jack on 16/4/6.
 */
public class DispatchDetailActivity extends BaseDispatchActivity {
    @InjectView(R.id.warehouse)
    TextView warehouse;
    @InjectView(R.id.salary_value)
    TextView salaryValue;
    @InjectView(R.id.consignee_name)
    TextView consigneeName;
    @InjectView(R.id.consignee_value)
    TextView consigneeValue;
    @InjectView(R.id.mobile_value)
    TextView mobileValue;
    @InjectView(R.id.address)
    TextView address;
    @InjectView(R.id.expand_list_view)
    ExpandableListView listView;

    DispatchDetailAdapter mAdapter;
    private String mOrderId;

    public static final String ORDER_ID = "order_id";
    public static final String ORDER_orderId = "";
    public static final String ORDER = "order";
    private String totalAmount = "";

    public static void toStart(Context context, DispatchToday.ListEntity entity) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ORDER,entity);

        Intent intent = new Intent(context, DispatchDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected String getToolBarTitle() {
        return "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dispatch_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        DispatchToday.ListEntity entity = bundle.getParcelable(ORDER);

        tvCenter.setText("详情");
        warehouse.setText("出仓单号 : " + entity.deliver_sn);
        consigneeName.setText("收货人 : " + entity.consignee.get(0));
        mobileValue.setText(entity.mobile.get(0));
        address.setText(entity.address.get(0));
        totalAmount = entity.total_amount;

        mAdapter = new DispatchDetailAdapter(this);
        listView.setAdapter(mAdapter);

        AppObservable.bindActivity(this,mApi.getDispatchDetail(userId,entity.order_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    private Observer<DispatchDetail> observer =new Observer<DispatchDetail>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(mContext,"获取数据失败");
        }

        @Override
        public void onNext(DispatchDetail dispatchDetail) {
            if(dispatchDetail == null) return;
            mAdapter.setData(dispatchDetail);
            String orderCount = dispatchDetail.data.size() + "";
            salaryValue.setText("共"+orderCount+ "单 金额：￥"+totalAmount);
        }
    };
}
