package com.louie.luntonghui.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.CancelOrderAdapter;
import com.louie.luntonghui.model.db.ApplyForProduct;
import com.louie.luntonghui.model.db.Order;
import com.louie.luntonghui.model.result.OrderDetailResult;
import com.louie.luntonghui.model.result.ResultObject;
import com.louie.luntonghui.ui.BaseCenterToolbarActivity;
import com.louie.luntonghui.util.BaseAlertDialogUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.ItemDivider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jack on 16/4/27.
 * <p>
 * 申请退货
 */
public class OrderCancelActivity extends BaseCenterToolbarActivity implements BaseAlertDialogUtil.BaseAlertDialogListener{
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.confirm)
    Button confirm;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    CancelOrderAdapter mAdapter;
    List<OrderDetailResult.GoodsListEntity> list;
    private String orderId;

    @Override
    protected String getToolBarTitle() {
        return getResources().getString(R.string.order_detail);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_cancel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = getIntent().getParcelableArrayListExtra(DetailOrderActivity.GOODS_LIST);
        mAdapter = new CancelOrderAdapter(mContext,list);
        orderId = getIntent().getStringExtra(Order.ORDERID);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置默认动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        recyclerView.setHasFixedSize(true);
        //mRecyclerView.addItemDecoration(new RecyclerViewLinearLayoutViewItemDecoration(mContext, HORIZONTAL_LIST));
        recyclerView.addItemDecoration(new ItemDivider(mContext, R.drawable.recyclerview_break_line));
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.confirm)
    public void onClick(){
        if(mAdapter.getMapValue().size() == 0){
            ToastUtil.showShortToast(mContext,"请选择退货商品");
            return;
        }
        BaseAlertDialogUtil.getInstance()
                .setMessage("确认申请退货吗?")
                .setPositiveContent("确定")
                .setNegativeContent("取消")
                .show(mContext,this);
    }

    @Override
    public void confirm() {
        Map<String,CancelOrderAdapter.Product> values = mAdapter.getMapValue();
        ApplyForProduct product = new ApplyForProduct();
        product.user_id = userId;
        product.order_id = orderId;
        List<ApplyForProduct.Product> list = new ArrayList<>();

        //list.add() = li;
        for(String key: values.keySet()){
            ApplyForProduct.Product p =  product.new Product();
            p.goods_id = key;
            p.return_number = values.get(key).returnCount;
            p.return_reason = values.get(key).reason;
            list.add(p);
        }

        product.return_info = new ArrayList<>();
        product.return_info.addAll(list);

        AppObservable.bindActivity(this, mApi.applyForProduct(product))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observable);
    }

    private Observer<ResultObject> observable = new Observer<ResultObject>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(mContext,"获取数据失败");

        }

        @Override
        public void onNext(ResultObject resultObject) {
            ToastUtil.showShortToast(mContext,resultObject.rsgmsg);

            if(resultObject.rsgcode.equals(ResultObject.SUCCESS)) finish();
        }
    };

}
