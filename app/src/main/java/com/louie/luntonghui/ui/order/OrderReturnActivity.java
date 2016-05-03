package com.louie.luntonghui.ui.order;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.CancelProductAdapter;
import com.louie.luntonghui.model.db.Order;
import com.louie.luntonghui.model.result.ResultObject;
import com.louie.luntonghui.model.result.ReturnProductDetail;
import com.louie.luntonghui.ui.BaseCenterToolbarActivity;
import com.louie.luntonghui.util.BaseAlertDialogUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyListView;

import java.math.BigDecimal;

import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



/**
 * Created by Jack on 16/4/27.
 * 退货单详细
 *
 */
public class OrderReturnActivity extends BaseCenterToolbarActivity implements BaseAlertDialogUtil.BaseAlertDialogListener {
    private LinearLayout linearOrderState, linearOrderSn, linearOrderMoney, linearDownOrderTime,
            linearName,linearAddress,linearPhone;

    private TextView orderStateValue, orderSnValue, orderMoneyValue, downOrderTimeValue,
                     usernameValue,userAddressValue,userPhoneValue;

    @InjectView(R.id.list_view)
    MyListView listView;

    @InjectView(R.id.cancel_return_product)
    Button btnCnacelReturnProduct;

    @InjectView(R.id.return_product_price)
    TextView tvReturnProductPrice;
    private String orderId;
    private double goodsPrice;
    private String returnOrderId;

    public static final String CAN_CANEL_ORDER_STATE = "1";
    String[] cancelOrderState;

    private CancelProductAdapter mAdapter;

    @Override
    protected String getToolBarTitle() {
        return getResources().getString(R.string.order_return_info);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_return;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        cancelOrderState = getResources().getStringArray(R.array.cancel_order_state);
        mAdapter = new CancelProductAdapter(mContext);
        init();

        getCancelOrderInfo();
    }

    private void getCancelOrderInfo() {
        orderId = getIntent().getStringExtra(Order.ORDERID);
        AppObservable.bindActivity(this,mApi.getReturnProductDetail(userId,orderId))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer);
    }

    Observer<ReturnProductDetail> observer = new Observer<ReturnProductDetail>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(mContext,"获取数据失败");
        }

        @Override
        public void onNext(ReturnProductDetail detail) {
            if(detail.rsgcode.equals(ResultObject.SUCCESS)){
                if(detail.data.order.return_status.equals(CAN_CANEL_ORDER_STATE))
                    btnCnacelReturnProduct.setVisibility(View.VISIBLE);

                returnOrderId =detail.data.order.return_id;

                orderStateValue.setText(cancelOrderState[Integer.parseInt(detail.data.order.return_status)]);
                orderSnValue.setText(detail.data.order.order_sn);
                orderMoneyValue.setText(detail.data.order.order_amount);
                downOrderTimeValue.setText(detail.data.order.add_time);

                usernameValue.setText(detail.data.order.consignee);
                userPhoneValue.setText(detail.data.order.mobile);
                userAddressValue.setText(detail.data.order.address);

                for(ReturnProductDetail.DataEntity.GoodsListEntity entity : detail.data.goods_list){
                    goodsPrice += Double.parseDouble(entity.goods_price);
                }

                BigDecimal b =new BigDecimal(goodsPrice);
                goodsPrice = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                tvReturnProductPrice.setText("退货金额: ￥" + goodsPrice);
                mAdapter.setData(detail.data.goods_list);


            }else{
                ToastUtil.showShortToast(mContext,detail.rsgmsg);
            }
        }
    };




    private void init() {
        linearOrderState = (LinearLayout) findViewById(R.id.order_state);
        linearOrderSn = (LinearLayout) findViewById(R.id.order_sn);
        linearOrderMoney = (LinearLayout) findViewById(R.id.order_money);
        linearDownOrderTime = (LinearLayout) findViewById(R.id.down_order_time);

        TextView orderState = (TextView) linearOrderState.findViewById(R.id.name);
        TextView orderSn = (TextView) linearOrderSn.findViewById(R.id.name);
        TextView orderMoney = (TextView) linearOrderMoney.findViewById(R.id.name);
        TextView downOrderTime = (TextView) linearDownOrderTime.findViewById(R.id.name);

        orderStateValue = (TextView) linearOrderState.findViewById(R.id.value);
        orderSnValue = (TextView) linearOrderSn.findViewById(R.id.value);
        orderMoneyValue = (TextView) linearOrderMoney.findViewById(R.id.value);
        downOrderTimeValue = (TextView) linearDownOrderTime.findViewById(R.id.value);

        orderState.setText(R.string.order_state);
        orderSn.setText(R.string.order_serial_number);
        orderMoney.setText(R.string.order_money);
        downOrderTime.setText(R.string.place_an_order);


        linearName = (LinearLayout)findViewById(R.id.user_name);
        linearAddress = (LinearLayout)findViewById(R.id.user_address);
        linearPhone = (LinearLayout)findViewById(R.id.user_phone);

        TextView tvUsername  = (TextView)linearName.findViewById(R.id.name);
        TextView tvUserAddress = (TextView)linearAddress.findViewById(R.id.name);
        TextView tvUserPhone = (TextView)linearPhone.findViewById(R.id.name);

        usernameValue = (TextView)linearName.findViewById(R.id.value);
        userAddressValue = (TextView) linearAddress.findViewById(R.id.value);
        userPhoneValue = (TextView)linearPhone.findViewById(R.id.value);

        tvUsername.setText("姓名: ");
        tvUserAddress.setText("地址: ");
        tvUserPhone.setText("手机: ");
    }

    @OnClick(R.id.cancel_return_product)
    public void onClickCancel(){
        BaseAlertDialogUtil.getInstance()
                .setMessage("确认取消退货吗?")
                .show(mContext,this);


    }

    @Override
    public void confirm() {
        AppObservable.bindActivity(this,mApi.cancelReturnProduct(returnOrderId,orderId,userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShortToast(mContext,"申请取消退货失败");
                    }

                    @Override
                    public void onNext(ResultObject resultObject) {
                        ToastUtil.showShortToast(mContext,resultObject.rsgmsg);
                        if(resultObject.rsgcode.equals(ResultObject.SUCCESS)){
                            finish();
                        }
                    }
                });
    }
}
