package com.louie.luntonghui.ui.car;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.ProduceOrderAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.OrderConfirmEvent;
import com.louie.luntonghui.fragment.CarFragment;
import com.louie.luntonghui.model.db.Goods;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.OrderConfirm;
import com.louie.luntonghui.model.result.ProduceOrder;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.mine.MineAdditionAddressActivity;
import com.louie.luntonghui.ui.mine.MineReceiverAddressActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.BaseAlertDialogUtil;
import com.louie.luntonghui.util.BaseMainAlertDialogUtil;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyListView;
import com.louie.luntonghui.view.SlideSwitch;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/7/1.
 */
public class ProduceOrderActivity extends BaseNormalActivity implements SlideSwitch.SlideListener
                            ,BaseAlertDialogUtil.BaseAlertDialogListener,BaseMainAlertDialogUtil.BaseMainAlertDialogListener,
                                ProduceOrderAdapter.FixOrderListener{

    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.username)
    TextView username;
    @InjectView(R.id.phone_number)
    TextView phoneNumber;
    @InjectView(R.id.region_detail)
    TextView regionDetail;

    @InjectView(R.id.listView)
    MyListView listView;

    @InjectView(R.id.exchange_luntongbi_value)
    TextView exchangeLuntongbiValue;
    @InjectView(R.id.user_feedback)
    EditText userFeedback;
    @InjectView(R.id.goods_value_value)
    TextView goodsValueValue;
    @InjectView(R.id.freight_value)
    TextView freightValue;
    @InjectView(R.id.luntong_money_value)
    TextView luntongMoneyValue;
    @InjectView(R.id.progress)
    ProgressBar progress;

    @InjectView(R.id.use_luntong_money_count)
    EditText useLuntongMoneyCount;
    @InjectView(R.id.goods_total)
    TextView goodsTotal;
    @InjectView(R.id.radio)
    TextView radio;
   /* @InjectView(R.id.toggle)
    SlideSwitch mToggle;*/
    @InjectView(R.id.luntong_exchange_state)
    LinearLayout exchangeState;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;
    @InjectView(R.id.total_price)
    RelativeLayout totalPrice;

    @InjectView(R.id.input_address_present)
    TextView addressPresent;

    @InjectView(R.id.address_select_more)
    ImageView addressSelectMore;

    @InjectView(R.id.prompt)
    TextView giftPrompt;

    @InjectView(R.id.enough_total_deliver)
    RelativeLayout enoughTotalDeliver;

    @InjectView(R.id.enough_total_reduce)
    RelativeLayout enoughTotalReduce;

    @InjectView(R.id.enough_total_reduce_value)
    TextView enoughTotalReduceValue;

    @InjectView(R.id.enough_total_deliver_value)
    TextView enoughTotalDeliverValue;
    @InjectView(R.id.coupon_value)
    TextView couponValue;

    @InjectView(R.id.integral_control_relative)
    RelativeLayout integralControl;

    public static final int DEFAULT_ADDRESS_ID = 0;
    private int addressId = DEFAULT_ADDRESS_ID;
    private static final int REQUESTCODE = 0x1;
    private static final int REQUEST_ADD = 0x2;
    public static final String ADDRESS_SELECT = "address_select";
    public static final String ADDRESS_ADD = "address_add";
    public static final boolean isAddressAdd = true;
    public static final boolean isAddressSelect = true;

    private String userId;
    public Map<String, String> regions;
    private ProduceOrderAdapter mAdapter;
    private int maxLuntongMoney;
    public static final double lunToMoney = 100.0;
    public static final int LUNTOEXCHANGE = 100;
    public double totalOrgValue;
    public static final String CACHPLAY = "2";

    public static final String ENOUGH_TOTAL_REDUCE = "1";
    public static final String ENOUGH_TOTAL_DELIVER = "2";
    public static final String ENOUGH_TOTAL_ALL = "3";
    private int useTotalLunTongMoney;
    private Toast toast;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_confirm_order);

        ButterKnife.inject(this);
        App.getBusInstance().register(this);

        progress.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();

        ProduceOrder order = getIntent().getParcelableExtra(CarFragment.ORDER);
        //ProduceOrder3 order =(ProduceOrder3) getIntent().getSerializableExtra(CarFragment.ORDER);
        mAdapter = new ProduceOrderAdapter(this,this);
        listView.setAdapter(mAdapter);


        toolbarTitle.setText("购物车");
        //最大字符数
        userFeedback.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});

        view = getLayoutInflater().inflate(R.layout.commit_order, null);
        toast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);

        progress.setVisibility(View.VISIBLE);
        initGetInfo();
        initOrderInfo(order);
        focusable();//获取焦点
        progress.setVisibility(View.GONE);

    }

    private void focusable() {
        new Handler().
                post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
        addressPresent.setFocusable(true);
        addressPresent.setFocusableInTouchMode(true);
        addressPresent.requestFocus();
    }

    private void initOrderInfo(ProduceOrder produceOrder) {
        regions = ((App) getApplication()).idNList;

        userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);

        progress.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        totalPrice.setVisibility(View.VISIBLE);

        if(produceOrder.consignee.size() > 0) {
            initAddress();
            addressPresent.setVisibility(View.GONE);
            addressId = Integer.parseInt(produceOrder.consignee.get(0).address_id);
            String province = regions.get(produceOrder.consignee.get(0).province) + "省";
            String city = regions.get(produceOrder.consignee.get(0).city) + "市";
            String district = regions.get(produceOrder.consignee.get(0).district);
            String addressDetail = produceOrder.consignee.get(0).address;

            username.setText(produceOrder.consignee.get(0).consignee);
            phoneNumber.setText(produceOrder.consignee.get(0).mobile);
            regionDetail.setText(province + city + district + addressDetail);
        }else{
            addressPresent.setVisibility(View.VISIBLE);
            username.setVisibility(View.GONE);
            phoneNumber.setVisibility(View.GONE);
            regionDetail.setVisibility(View.GONE);
            addressSelectMore.setVisibility(View.GONE);
        }

        if(produceOrder.total.integral_control.equals(ProduceOrder.CAN_NOT_INTEGRAL)){
            integralControl.setVisibility(View.VISIBLE);
        }else{
            integralControl.setVisibility(View.GONE);
        }

        mAdapter.setData(produceOrder.cart_goods, false);

        goodsValueValue.setText("￥" + produceOrder.total.goods_price + "");
        freightValue.setText("￥" + produceOrder.total.shipping_fee);
        goodsTotal.setText("￥" + produceOrder.total.goods_price);
        couponValue.setText("-￥"+ produceOrder.total.discounts);
        totalOrgValue = produceOrder.total.goods_price;

        String strRadio = produceOrder.total.ratio;
        radio.setText(strRadio);

        Pattern pattern;
        Matcher matcher;
        String strPattern = ".*?([\\d]+).*?([\\d]+).*+";
        pattern = Pattern.compile(strPattern);
        matcher = pattern.matcher(strRadio);
        String grounp = "";
        while (matcher.find()) {
            grounp = matcher.group(1);
        }
        maxLuntongMoney = Integer.parseInt(grounp);

        enoughTotalDeliver.setVisibility(View.GONE);
        enoughTotalReduce.setVisibility(View.GONE);
        giftPrompt.setVisibility(View.GONE);

        switch (produceOrder.total.act_type) {
            //减
            case ENOUGH_TOTAL_REDUCE:
                enoughTotalReduce.setVisibility(View.VISIBLE);
                enoughTotalReduceValue.setText(produceOrder.total.details);
                break;
            //赠
            case ENOUGH_TOTAL_DELIVER:
                enoughTotalDeliver.setVisibility(View.VISIBLE);
                enoughTotalDeliverValue.setText(produceOrder.total.gift);
                break;
            case ENOUGH_TOTAL_ALL:
                enoughTotalDeliver.setVisibility(View.VISIBLE);
                enoughTotalReduce.setVisibility(View.VISIBLE);
                enoughTotalReduceValue.setText(produceOrder.total.details);
                enoughTotalDeliverValue.setText(produceOrder.total.gift);
                break;
        }

        if(!TextUtils.isEmpty(produceOrder.total.prompt)){
            giftPrompt.setVisibility(View.VISIBLE);
            giftPrompt.setText(produceOrder.total.prompt);
        }else{
            giftPrompt.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUESTCODE && resultCode == RESULT_OK){

            initAddress();

            String provinceId = data.getStringExtra(MineReceiverAddressActivity.PROVINCE_ID);
            String cityId = data.getStringExtra(MineReceiverAddressActivity.CITY_ID);
            String districtId = data.getStringExtra(MineReceiverAddressActivity.DISTRICT_ID);
            String strAddressId = data.getStringExtra(MineReceiverAddressActivity.ADDRESS_ID);
            String consigner = data.getStringExtra(MineReceiverAddressActivity.CONSIGNER);
            String detailAddress = data.getStringExtra(MineReceiverAddressActivity.DETAIL_ADDRESS);
            String strPhoneNumber = data.getStringExtra(MineReceiverAddressActivity.PHONE_NUMBER);


            addressId = Integer.parseInt(strAddressId);
            String province = regions.get(provinceId) + "省";
            String city = regions.get(cityId) + "市";
            String district = regions.get(districtId);
            String addressDetail = detailAddress ;

            username.setText(consigner);
            phoneNumber.setText(strPhoneNumber);
            regionDetail.setText(province + city + district + addressDetail);
        }else if(requestCode == REQUEST_ADD && resultCode == RESULT_OK){
            fillAddress = false;
            initAddress();
            String provinceId = data.getStringExtra(MineAdditionAddressActivity.PROVINCE_ID);
            String cityId = data.getStringExtra(MineAdditionAddressActivity.CITY_ID);
            String districtId = data.getStringExtra(MineAdditionAddressActivity.STREE_ID);

            String strAddressId = data.getStringExtra(MineReceiverAddressActivity.ADDRESS_ID);
            String consigner = data.getStringExtra(MineReceiverAddressActivity.CONSIGNER);

            String detailAddress = data.getStringExtra(MineAdditionAddressActivity.DETAIL_ADDRESS);
            String strPhoneNumber = data.getStringExtra(MineReceiverAddressActivity.PHONE_NUMBER);


            addressId = Integer.parseInt(strAddressId);
            String province = regions.get(provinceId) + "省";
            String city = regions.get(cityId) + "市";
            String district = regions.get(districtId);
            String addressDetail = detailAddress ;

            username.setText(consigner);
            phoneNumber.setText(strPhoneNumber);
            regionDetail.setText(province + city + district + addressDetail);
        }
    }

    private void initGetInfo() {
        useLuntongMoneyCount.setText("0");
        luntongMoneyValue.setText("-￥0.00");
        //exchangeLuntongbiValue.setText("0.0元");
        exchangeLuntongbiValue.setText("");
        couponValue.setText("-￥0.00");

        /*mToggle.setState(true);
        mToggle.setSlideListener(this);*/

       /* useLuntongMoneyCount.setBackgroundColor(getResources().getColor(R.color.useful_toggle_off));
        useLuntongMoneyCount.setEnabled(false);*/

        useLuntongMoneyCount.addTextChangedListener(new TextWatcher() {
            private String beforeText = "0";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int value;
                try {
                    if (beforeText.equals("")) {
                        value = 0;
                    } else {
                        value = Integer.parseInt(beforeText);
                    }
                    if (s.toString().equals("")) {
                        value = 0;
                    } else {
                        value = Integer.parseInt(s.toString());
                    }


                    if (maxLuntongMoney < value) {
                        String message = String.format(getResources()
                                .getString(R.string.luntong_money_warning), maxLuntongMoney + "");
                        ToastUtil.showShortToast(mContext, message);
                        useLuntongMoneyCount.setText(beforeText);
                        return;
                    }
                } catch (Exception e) {
                    Log.d("exception e", e.getMessage() + "exception ....");
                    value = 0;

                }

                double tempValue = value;

                //double curUseLunTongValue = (double) (Math.round(value / LUNTOEXCHANGE)/lunToMoney);
                double curUseLunTongValue = tempValue / lunToMoney;

                exchangeLuntongbiValue.setText("抵￥"+curUseLunTongValue + "元");
                luntongMoneyValue.setText("-￥" + curUseLunTongValue);

                double totals = totalOrgValue - curUseLunTongValue;
                BigDecimal bg = new BigDecimal(totals);
                goodsTotal.setText("￥" + bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                useLuntongMoneyCount.setSelection(useLuntongMoneyCount.getText().length());
            }
        });
    }

    public void initAddress(){
        addressPresent.setVisibility(View.GONE);
        username.setVisibility(View.VISIBLE);
        phoneNumber.setVisibility(View.VISIBLE);
        regionDetail.setVisibility(View.VISIBLE);
        addressSelectMore.setVisibility(View.VISIBLE);
    }

    private Response.Listener<ProduceOrder> responseListener() {
        return new Response.Listener<ProduceOrder>() {
            @Override
            public void onResponse(final ProduceOrder produceOrder) {

            }
        };
    }

    private boolean fillAddress = false;
    @OnClick(R.id.submit_order)
    public void onClickSubmitOrder() {

        if(TextUtils.isEmpty(useLuntongMoneyCount.getText() + ""))
            useLuntongMoneyCount.setText("0");

        double tempValue = Double.parseDouble(useLuntongMoneyCount.getText() + "");

        //double curUseLunTongValue = (double) (Math.round(value / LUNTOEXCHANGE)/lunToMoney);
        double curUseLunTongValue = tempValue / lunToMoney;

        if(totalOrgValue < curUseLunTongValue ){
            ToastUtil.showShortToast(mContext,R.string.luntongbi_more_warnning);
            return;
        }

        fillAddress = false;
        if(addressId == DEFAULT_ADDRESS_ID ){
            fillAddress = true;
            BaseAlertDialogUtil.getInstance()
                    .setMessage(R.string.warnning_fill_address)
                    .setCanceledOnTouchOutside(true)
                    .setNegativeContent(R.string.cancel)
                    .setPositiveContent(R.string.goto_addition);
            BaseAlertDialogUtil.getInstance().show(mContext, ProduceOrderActivity.this);

            return;
        }
        BaseMainAlertDialogUtil.getInstance()
                .setCanceledOnTouchOutside(true)
                .setNegativeContent(R.string.cancel)
                .setPositiveContent(R.string.sure);

        BaseMainAlertDialogUtil.getInstance().show(mContext, ProduceOrderActivity.this);
    }

    private Response.Listener<OrderConfirm> confirmOrderListener(){
        return new Response.Listener<OrderConfirm>(){
            @Override
            public void onResponse(OrderConfirm orderConfirm) {
                if(orderConfirm.rsgcode.equals(SUCCESSCODE)){
                    TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                        @Override
                        protected Object doInBackground(Object... params) {
                            new Update(Goods.class)
                                    .set("isChecked = ?", Goods.GOODS_IS_NOT_BUY)
                                    .execute();
                            new Delete()
                                    .from(ShoppingCar.class)
                                    .execute();
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            //ToastUtil.showLongToast(ProduceOrderActivity.this, R.string.order_confirm_success);
                            toast.show();
                            App.getBusInstance().post(new OrderConfirmEvent());
                            finish();
                        }
                    });
                }else{
                    ToastUtil.showShortToast(ProduceOrderActivity.this,orderConfirm.rsgmsg);
                }
            }
        };
    }

    @Override
    public void open() {
        useLuntongMoneyCount.setEnabled(true);
        useLuntongMoneyCount.setBackgroundResource(R.drawable.base_frame);
    }

    @Override
    public void close() {
        useLuntongMoneyCount.setBackgroundColor(getResources().getColor(R.color.useful_toggle_off));
        useLuntongMoneyCount.setText("0");
        useLuntongMoneyCount.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }

    @OnClick(R.id.toolbar_navigation)
    public void onExit(){
        exit();
    }

    @Override
    public void onBackPressed() {
        exit();
    }
    public void exit(){
        BaseAlertDialogUtil.getInstance()
                .setMessage(R.string.cheap_not_alway_wait)
                .setCanceledOnTouchOutside(true)
                .setNegativeContent(R.string.to_think_again)
                .setPositiveContent(R.string.go_alway);

        BaseAlertDialogUtil.getInstance().show(mContext, ProduceOrderActivity.this);
    }

    @Override
    public void confirm() {
        if(fillAddress){

            addressPresent();
            return;
        }
        finish();
    }

    @Override
    public void submitConfirm() {
        String strUserFeedback = userFeedback.getText().toString();
        String uid = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        String integral = useLuntongMoneyCount.getText() + "";
        String payId = CACHPLAY;

        String display = Config.getRenamePlace();
        try {
            strUserFeedback = URLEncoder.encode(strUserFeedback, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(ConstantURL.CONFIRM_ORDER,uid,addressId,payId,strUserFeedback,integral,display);
        executeRequest(new GsonRequest(url, OrderConfirm.class, confirmOrderListener(), errorListener()));
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

    }

    @OnClick(R.id.address_select_more)
    public void addressSelect(){
        Intent intent = new Intent();
        intent.putExtra(ADDRESS_SELECT,isAddressSelect);
        intent.setClass(ProduceOrderActivity.this, MineReceiverAddressActivity.class);
        startActivityForResult(intent, REQUESTCODE);
    }
    @OnClick(R.id.input_address_present)
    public void addressPresent(){
        Intent intent = new Intent();
        intent.putExtra(ADDRESS_ADD,isAddressAdd);
        intent.setClass(ProduceOrderActivity.this, MineAdditionAddressActivity.class);
        startActivityForResult(intent,REQUEST_ADD);
    }
}
