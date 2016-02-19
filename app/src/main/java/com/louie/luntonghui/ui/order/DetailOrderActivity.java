package com.louie.luntonghui.ui.order;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.ProduceOrderAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.Order;
import com.louie.luntonghui.model.result.OrderDetailResult;
import com.louie.luntonghui.model.result.ProduceOrder;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.car.ProduceOrderActivity;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
/**
 * Created by Administrator on 2015/7/17.
 */
public class DetailOrderActivity extends BaseNormalActivity implements ProduceOrderAdapter.FixOrderListener{
    public static final int NOT_ORDER_CONFIRM = 0;

    @InjectView(R.id.lv_goodslist)
    MyListView lvGoodslist;
    @InjectView(R.id.list_view_order_state)
    MyListView listViewOrderState;
    @InjectView(R.id.cancel_order)
    Button cancelOrder;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;

    @InjectView(R.id.fix_order)
    TextView fixOrder;

    @InjectView(R.id.enough_total_deliver)
    RelativeLayout enoughTotalDeliver;

    @InjectView(R.id.enough_total_reduce)
    RelativeLayout enoughTotalReduce;

    @InjectView(R.id.enough_total_reduce_value)
    TextView enoughTotalReduceValue;

    @InjectView(R.id.enough_total_deliver_value)
    TextView enoughTotalDeliverValue;


    ProgressDialog mProgressDialog;
    public String queryUserId;

    private String orderId;
    private ProduceOrderAdapter mGoodsAdapter;
    public Map<String, String> regions;
    public int queryType  = Order.DEFAULT_QUERY_TYPE;
    public String[] orderStates;
    public boolean isFixOrder = false;

    private LinearLayout linearOrderState, linearOrderSn, linearOrderMoney, linearDownOrderTime,
            linearPayMethod, linearGoodsValue, linearFreight, linearSalesVolume, linearNeedPayValue,
            linearHasPayValue, linearPayState, linearConsignee, linearDeliveryAddress, linearPhoneNumber;

    private TextView orderStateValue, orderSnValue, orderMoneyValue, downOrderTimeValue,
            payMethodValue, goodsValueValue, freightValue, salesVolumeValue, needPayValueValue,
            hasPayValueValue, payStateValue, consigneeValue, deliveryAddressValue, phoneNumberValue;

    private TextView businessMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.inject(this);

        toolbarTitle.setText(R.string.order_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString(Order.ORDERID);
            queryType = bundle.getInt(Order.QUERY_TYPE);
            queryUserId = bundle.getString(Order.USER_ID);
            if(queryUserId != null){
                userId = queryUserId;
            }
        }

        regions = ((App) getApplication()).idNList;

        mGoodsAdapter = new ProduceOrderAdapter(mContext,this);
        lvGoodslist.setAdapter(mGoodsAdapter);
        mProgressDialog = new ProgressDialog(mContext);

        if(queryType == Order.SERVICE_TYPE){
            cancelOrder.setVisibility(View.GONE);
        }
        orderStates = getResources().getStringArray(R.array.order_state_list);
        initView();

        queryOrderInfo();
    }

    private void initView() {
        //base info
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

        //pay info
        linearPayMethod = (LinearLayout) findViewById(R.id.pay_method);
        linearGoodsValue = (LinearLayout) findViewById(R.id.goods_value);
        linearFreight = (LinearLayout) findViewById(R.id.freight_value);
        linearSalesVolume = (LinearLayout) findViewById(R.id.sales_volume);
        linearNeedPayValue = (LinearLayout) findViewById(R.id.need_pay_value);
        linearHasPayValue = (LinearLayout) findViewById(R.id.has_pay_value);
        linearPayState = (LinearLayout) findViewById(R.id.pay_state);

        payMethodValue = (TextView) linearPayMethod.findViewById(R.id.value);
        goodsValueValue = (TextView) linearGoodsValue.findViewById(R.id.value);
        freightValue = (TextView) linearFreight.findViewById(R.id.value);
        salesVolumeValue = (TextView) linearSalesVolume.findViewById(R.id.value);
        needPayValueValue = (TextView) linearNeedPayValue.findViewById(R.id.value);
        hasPayValueValue = (TextView) linearHasPayValue.findViewById(R.id.value);
        payStateValue = (TextView) linearPayState.findViewById(R.id.value);


        TextView payMethod = (TextView) linearPayMethod.findViewById(R.id.name);
        TextView goodsValue = (TextView) linearGoodsValue.findViewById(R.id.name);
        TextView freight = (TextView) linearFreight.findViewById(R.id.name);
        TextView salesVolume = (TextView) linearSalesVolume.findViewById(R.id.name);
        TextView needPayValue = (TextView) linearNeedPayValue.findViewById(R.id.name);
        TextView hasPayValue = (TextView) linearHasPayValue.findViewById(R.id.name);
        TextView payState = (TextView) linearPayState.findViewById(R.id.name);

        payMethod.setText(R.string.play_method);
        goodsValue.setText(R.string.goods_value1);
        freight.setText(R.string.freight_value);
        salesVolume.setText(R.string.favorable_value);
        needPayValue.setText(R.string.need_pay_value);
        hasPayValue.setText(R.string.has_pay_value);
        payState.setText(R.string.pay_state);

        //delivery info
        linearConsignee = (LinearLayout) findViewById(R.id.consignee);
        linearDeliveryAddress = (LinearLayout) findViewById(R.id.delivery_address);
        linearPhoneNumber = (LinearLayout) findViewById(R.id.phone_number);

        TextView consignee = (TextView) linearConsignee.findViewById(R.id.name);
        TextView deliveryAddress = (TextView) linearDeliveryAddress.findViewById(R.id.name);
        TextView phoneNumber = (TextView) linearPhoneNumber.findViewById(R.id.name);

        consigneeValue = (TextView) linearConsignee.findViewById(R.id.value);
        deliveryAddressValue = (TextView) linearDeliveryAddress.findViewById(R.id.value);
        phoneNumberValue = (TextView) linearPhoneNumber.findViewById(R.id.value);

        consignee.setText(R.string.consignee);
        deliveryAddress.setText(R.string.deliveryAddress);
        phoneNumber.setText(R.string.phone_number1);

        businessMessage = (TextView)findViewById(R.id.business_message);



    }

    private void queryOrderInfo() {
        getOrderInfo();

        /*TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Order>() {
            @Override
            protected Order doInBackground(Object... params) {
                Order order = new Select()
                        .from(Order.class)
                        .executeSingle();
                return null;
            }

            @Override
            protected void onPostExecute(Order order) {
                if (order != null) {
                    mProgressDialog.dismiss();



                } else {
                }
            }
        });*/

    }

    private void getOrderInfo() {
        mProgressDialog.show();

        String url = String.format(ConstantURL.GET_ORDER_DETAIL, userId, orderId);
        executeRequest(new GsonRequest(url, OrderDetailResult.class, orderDetailRequest(), errorListener()));
    }

    private Response.Listener<OrderDetailResult> orderDetailRequest() {
        return new Response.Listener<OrderDetailResult>() {
            @Override
            public void onResponse(OrderDetailResult orderDetailResult) {
                mProgressDialog.dismiss();
                OrderDetailResult.OrderEntity order = orderDetailResult.order;

                int orderState = Integer.parseInt(order.order_status);
                if(orderState == NOT_ORDER_CONFIRM){
                    fixOrder.setVisibility(View.VISIBLE);
                }

                orderStateValue.setText(orderStates[orderState]);
                orderSnValue.setText(order.order_sn);
                orderMoneyValue.setText("￥" + order.order_amount); //..
                downOrderTimeValue.setText(order.formated_add_time);

                //payMethodValue.setText(orderDetailResult.payment_list.get(0).pay_name);
                payMethodValue.setText(order.pay_name);
                goodsValueValue.setText("￥" + orderDetailResult.order.goods_amount);
                freightValue.setText("￥" + order.shipping_fee);

                salesVolumeValue.setText("￥" + order.integral_money + ", 注:[花费轮通币" + order.integral + "]");
                needPayValueValue.setText("￥" + order.goods_amount);
                hasPayValueValue.setText("￥" + order.pay_fee);
                payStateValue.setText(order.pay_status + "[" + order.shipping_status + "]");

                try{
                    String province = regions.get(order.province) + "省";
                    String city = regions.get(order.city) + "市";
                    String district = regions.get(order.district);
                    String addressDetail = order.address;
                    deliveryAddressValue.setText(province + city + district + addressDetail);
                }catch (Exception e){

                }
                consigneeValue.setText(order.consignee);
                phoneNumberValue.setText(order.mobile);

                businessMessage.setText(order.postscript+"");

                List<OrderDetailResult.GoodsListEntity> lists = orderDetailResult.goods_list;
                List<ProduceOrder.CartGoodsEntity> cart_goods = new ArrayList<>();
                ProduceOrder produceOrder = new ProduceOrder();
                for (int i = 0; i < lists.size(); i++) {
                    ProduceOrder.CartGoodsEntity car = new ProduceOrder.CartGoodsEntity();
                    car.goods_thumb = lists.get(i).goods_thumb;
                    car.goods_name = lists.get(i).goods_name;
                    car.goods_price = lists.get(i).goods_price;
                    car.goods_number = lists.get(i).goods_number;
                    car.discount_type = lists.get(i).discount_type;
                    //car.rec_id = orderDetailResult.order.order_id;

                    car.orderid = orderDetailResult.order.order_id;
                    car.rec_id = lists.get(i).rec_id;

                    car.rid = lists.get(i).rid;
                    car.guige = lists.get(i).guige;
                    car.danwei = lists.get(i).danwei;
                    car.goods_id = lists.get(i).goods_id;
                    car.discount = lists.get(i).discount;
                    cart_goods.add(car);
                }
                mGoodsAdapter.setData(cart_goods, isFixOrder);

                enoughTotalDeliver.setVisibility(View.GONE);
                enoughTotalReduce.setVisibility(View.GONE);

                switch (orderDetailResult.order.act_type) {
                    //减
                    case ProduceOrderActivity.ENOUGH_TOTAL_REDUCE:
                        enoughTotalReduce.setVisibility(View.VISIBLE);
                        enoughTotalReduceValue.setText(order.details);
                        break;
                    //赠
                    case ProduceOrderActivity.ENOUGH_TOTAL_DELIVER:
                        enoughTotalDeliver.setVisibility(View.VISIBLE);
                        enoughTotalDeliverValue.setText(order.gift);
                        break;
                    case ProduceOrderActivity.ENOUGH_TOTAL_ALL:
                        enoughTotalDeliver.setVisibility(View.VISIBLE);
                        enoughTotalReduce.setVisibility(View.VISIBLE);
                        enoughTotalReduceValue.setText(order.details);
                        enoughTotalDeliverValue.setText(order.gift);
                        break;
                }
            }
        };
    }

    @OnClick(R.id.fix_order)
    public void onClickFixOrder(View v){
        isFixOrder = !isFixOrder;

        if(isFixOrder){
            fixOrder.setText("修改完成");
        }else{
            fixOrder.setText("修改订单");
        }

        mGoodsAdapter.fixOrder(isFixOrder,orderId);

    }



    @OnClick(R.id.cancel_order)
    public void onCancelOrderClick() {
        String url = String.format(ConstantURL.CANCEL_ORDER, userId, orderId);
        mProgressDialog.show();
        executeRequest(new GsonRequest(url, Result.class, cancelOrderRequest(), errorListener()));
    }

    private Response.Listener<Result> cancelOrderRequest() {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(Result result) {
                mProgressDialog.dismiss();
                ToastUtil.showShortToast(mContext, result.rsgmsg);
            }
        };
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

    @Override
    public void reference() {
        getOrderInfo();
    }
}
