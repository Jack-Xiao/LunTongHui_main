package com.louie.luntonghui.ui.car;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.ProduceOrderAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.LoginSuccessEvent;
import com.louie.luntonghui.event.OrderConfirmEvent;
import com.louie.luntonghui.model.db.Goods;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.OrderConfirm;
import com.louie.luntonghui.model.result.ProduceOrder;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.ui.mine.MineAdditionAddressActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.BaseAlertDialogUtil;
import com.louie.luntonghui.util.BaseMainAlertDialogUtil;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyListView;
import com.louie.luntonghui.view.SlideSwitch;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.http.DELETE;

/**
 * Created by Administrator on 2015/7/1.
 */
public class ProduceOrderActivity extends BaseNormalActivity implements SlideSwitch.SlideListener
                            ,BaseAlertDialogUtil.BaseAlertDialogListener,BaseMainAlertDialogUtil.BaseMainAlertDialogListener{

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
    @InjectView(R.id.radio_cash_deliver)
    RadioButton radioCashDeliver;
    @InjectView(R.id.radio_bank_deliver)
    RadioButton radioBankDeliver;
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
    @InjectView(R.id.toggle)
    SlideSwitch mToggle;
    @InjectView(R.id.luntong_exchange_state)
    LinearLayout exchangeState;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;
    @InjectView(R.id.total_price)
    RelativeLayout totalPrice;
    private int addressId;
    private static final int REQUESTCODE = 0x1;

    private String userId;
    public Map<String, String> regions;
    private ProduceOrderAdapter mAdapter;
    private int maxLuntongMoney;
    public static final double lunToMoney = 100.0;
    public static final int LUNTOEXCHANGE = 100;
    public double totalOrgValue;
    public static final String CACHPLAY = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_confirm_order);
        ButterKnife.inject(this);
        App.getBusInstance().register(this);
        mAdapter = new ProduceOrderAdapter(this);
        listView.setAdapter(mAdapter);


        toolbarTitle.setText("购物车");
        //最大字符数
        userFeedback.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        initGetInfo();
        initNet();
    }

    private void initNet() {

        //use_luntong_money_count.
        regions = ((App) getApplication()).idNList;

        userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        progress.setVisibility(View.VISIBLE);
        execConfirmOrder();
    }

    private void execConfirmOrder() {
        List<ShoppingCar> list = new Select()
                .from(ShoppingCar.class)
                .execute();
        StringBuilder ids = new StringBuilder("");
        for (int i = 0; i < list.size(); i++) {
            ids.append(list.get(i).carId + "," + list.get(i).goodsNumber + ":");
        }
        String new_number = ids.toString();
        if (new_number.length() > 0) {
            new_number = new_number.substring(0, new_number.length() - 1);
        }

        String url = String.format(ConstantURL.PRODUCE_ORDER, userId, new_number);
        executeRequest(new GsonRequest(url, ProduceOrder.class, responseListener(), errorListener()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUESTCODE){

        }
    }

    private void initGetInfo() {
        useLuntongMoneyCount.setText("0");
        luntongMoneyValue.setText("-￥:0.00");
        exchangeLuntongbiValue.setText("0.0元");
        mToggle.setState(true);
        mToggle.setSlideListener(this);
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
                    if (s.toString().equals("")){
                        value = 0;
                    }else{
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

                exchangeLuntongbiValue.setText(curUseLunTongValue + "元");
                luntongMoneyValue.setText("-￥" + curUseLunTongValue);

                double totals = totalOrgValue - curUseLunTongValue;
                BigDecimal bg = new BigDecimal(totals);
                goodsTotal.setText("￥" +  bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                useLuntongMoneyCount.setSelection(useLuntongMoneyCount.getText().length());
            }
        });
    }

    private Response.Listener<ProduceOrder> responseListener() {
        return new Response.Listener<ProduceOrder>() {
            @Override
            public void onResponse(final ProduceOrder produceOrder) {

                /*if(produceOrder.consignee.size() == 0){
                    MaterialDialog materialDialog = new MaterialDialog(ProduceOrderActivity.this);
                    materialDialog.setCanceledOnTouchOutside(false)
                            .setMessage(R.string.no_address_info)
                            .setNegativeButton(R.string.sure, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ProduceOrderActivity.this,
                                            MineAdditionAddressActivity.class);
                                    startActivityForResult(intent,REQUESTCODE);
                                    finish();
                                }
                            })
                            .setPositiveButton(R.string.cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                    return;
                }*/

                progress.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                totalPrice.setVisibility(View.VISIBLE);

                addressId = Integer.parseInt(produceOrder.consignee.get(0).address_id);
                String province = regions.get(produceOrder.consignee.get(0).province) + "省";
                String city = regions.get(produceOrder.consignee.get(0).city) + "市";
                String district = regions.get(produceOrder.consignee.get(0).district);
                String addressDetail = produceOrder.consignee.get(0).address;

                Log.d("province..", province + city + district + addressDetail);

                username.setText(produceOrder.consignee.get(0).consignee);
                phoneNumber.setText(produceOrder.consignee.get(0).mobile);
                regionDetail.setText(province + city + district + addressDetail);
                mAdapter.setData(produceOrder.cart_goods);

                goodsValueValue.setText("￥" + produceOrder.total.goods_price + "");
                freightValue.setText("￥" + produceOrder.total.shipping_fee);
                goodsTotal.setText("￥" + produceOrder.total.goods_price);
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
            }
        };
    }


    @OnClick(R.id.submit_order)
    public void onClickSubmitOrder() {
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
                            ToastUtil.showLongToast(ProduceOrderActivity.this, R.string.order_confirm_success);
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

        BaseAlertDialogUtil.getInstance().show(mContext,ProduceOrderActivity.this);
    }

    @Override
    public void confirm() {
        finish();
    }

    @Override
    public void submitConfirm() {
        String strUserFeedback = userFeedback.getText().toString();
        String uid = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        String integral = useLuntongMoneyCount.getText() + "";
        String payId = CACHPLAY;
        String display = DefaultShared.getString(App.PROVINCEID,App.DEFAULT_PROVINCEID);
        try {
            strUserFeedback = URLEncoder.encode(strUserFeedback, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        switch (display){
            case "6":
                display = "0";
                break;
            case "388":
                display = "1";
                break;
        }

        String url = String.format(ConstantURL.CONFIRM_ORDER,uid,addressId,payId,strUserFeedback,integral,display);
        executeRequest(new GsonRequest(url,OrderConfirm.class,confirmOrderListener(),errorListener()));
    }
}
