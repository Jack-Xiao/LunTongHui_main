package com.louie.luntonghui.ui.kill;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.SecondKillAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.ShowCarListEvent;
import com.louie.luntonghui.model.db.Goods;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.SecondKillAdvertResult;
import com.louie.luntonghui.model.result.SecondKillGoodsResult;
import com.louie.luntonghui.net.ImageCacheManager;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyListView;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/7/24.
 */
public class SecondKillActivity extends BaseNormalActivity {

    @InjectView(R.id.empty_rush_goods)
    TextView emptyRushGoods;
    /*@InjectView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;*/
    @InjectView(R.id.main_fab)
    FloatingActionButton mainFab;
    @InjectView(R.id.rush_goods_time)
    TextView rushGoodsTime;
    @InjectView(R.id.line_rush_goods)
    RelativeLayout lineRushGoods;
    private Timer timer = new Timer();
    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.img_advertise)
    ImageView imgAdvertise;
    @InjectView(R.id.second_kill_hour)
    TextView secondKillHour;
    @InjectView(R.id.second_kill_minute)
    TextView secondKillMinute;
    @InjectView(R.id.second_kill_second)
    TextView secondKillSecond;
    @InjectView(R.id.listview)
    MyListView listview;
    @InjectView(R.id.progress)
    ProgressBar progress;
    //public static final

    private String userType;
    private String killUrl;
    private SecondKillAdapter mAdapter;
    private SecondKillActivity mContext;
    private TimerTask task;
    public static final int UPDATETIME = 1;
    private static final int DELAYTIME = 1000;
    private Handler mHandler;
    private Runnable mRunnable;
    private long END_SECOND_KILL;
    private long BEGIN_SECOND_KILL;
    private long ALARM_CLOCK_TIME;
    public ImageLoader.ImageContainer imageRequest;
    private List<Goods> data = new ArrayList<Goods>();
    private String killAdverUrl;

    public int state = 0;

    private static final int NOTBEGIN = 1;
    private static final int BEGINRUSH = 2;
    private static final int FINISHRUSH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_sencond_kill);
        ButterKnife.inject(this);
/*
        swipeContainer.setColorScheme(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/
        //swipeContainer.setOnRefreshListener(this);

        mContext = this;
        toolbarTitle.setText(R.string.today_second_kill);
        userType = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT);
        killUrl = String.format(ConstantURL.SECOND_KILL_GOODS, userType);
        //imageRequest.getRequestUrl();
        String userType = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT);
        String cityId = DefaultShared.getString(App.CITYID, App.DEFAULT_CITYID);

        String display = DefaultShared.getString(App.PROVINCEID, App.DEFAULT_PROVINCEID);

        switch (display) {
            case "6":
                display = "0";
                break;
            case "388":
                display = "1";
                break;
        }

        killAdverUrl = String.format(ConstantURL.SECOND_KILL_ADVERT, userType, display, cityId);

        //initCountDownTime();

        initSecondKillView();
        referenceSencodeKillGoods();

    }

    public void referenceSencodeKillGoods() {
       // lineRushGoods.setVisibility(View.VISIBLE);
        RequestManager.addRequest(new GsonRequest(killAdverUrl, SecondKillAdvertResult.class,
                getSecondKillAdvert(), errorGoodsAdverListener()), mContext);

        RequestManager.addRequest(new GsonRequest(killUrl, SecondKillGoodsResult.class,
                getSecondKillGoods(), errorGoodsAdverListener()), mContext);
    }

    protected Response.ErrorListener errorGoodsAdverListener() {
        Picasso.with(mContext)
                .load(R.drawable.no_second_kill)
                .into(imgAdvertise);
       // lineRushGoods.setVisibility(View.GONE);
        emptyRushGoods.setVisibility(View.VISIBLE);
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    ToastUtil.showShortToast(mContext, R.string.network_connect_fail);
                    return;
                }
                switch (error.networkResponse.statusCode) {
                    case HttpStatus.SC_NO_CONTENT:
                }
            }
        };
    }

    private Response.Listener<SecondKillAdvertResult> getSecondKillAdvert() {
        return new Response.Listener<SecondKillAdvertResult>() {
            @Override
            public void onResponse(SecondKillAdvertResult secondKillAdvertResult) {
                if (secondKillAdvertResult == null) {
                  /*  ImageCacheManager.loadImage(R.drawable.no_second_kill,
                            ImageCacheManager.getImageListener(imgAdvertise))*/
                    Picasso.with(mContext)
                            .load(R.drawable.no_second_kill)
                            .into(imgAdvertise);
                    return;
                }
                String url = secondKillAdvertResult.listallcat.get(0).ad_code;
                ImageCacheManager.loadImage(url, ImageCacheManager.getImageListener(imgAdvertise));
                /*Picasso.with(mContext).
                        load(url)
                        .into(imgAdvertise);*/
            }
        };
    }

    private Response.Listener<SecondKillGoodsResult> getSecondKillGoods() {
        return new Response.Listener<SecondKillGoodsResult>() {
            @Override
            public void onResponse(final SecondKillGoodsResult currentBrandGoodsList) {

                if (currentBrandGoodsList != null && currentBrandGoodsList.spiketime != null
                        && currentBrandGoodsList.spiketime.get(0).start_time != null) {
                    String startTime = currentBrandGoodsList.spiketime.get(0).start_time;
                    String endTime = currentBrandGoodsList.spiketime.get(0).end_time;
                    initSecondTime(startTime, endTime);
                } else {
                    return;
                }


                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<Goods>>() {
                    @Override
                    protected void onPreExecute() {
                        progress.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected List<Goods> doInBackground(Object... params) {
                        List<ShoppingCar> list = new Select()
                                .from(ShoppingCar.class)
                                .execute();
                        List<String>goodsIds = new ArrayList<String>();
                        for(int i = 0;i<list.size();i++){
                            goodsIds.add(list.get(i).goodsId);
                        }

                        data = new ArrayList<Goods>();
                        List<Goods> setClocklist = new Select()
                                .from(Goods.class)
                                .where("setAlarmClock = ?", Goods.HAS_SET_ALARM_CLOCK)
                                .execute();

                        List<String> setClocklistIds = new ArrayList<String>();
                        for (int i = 0; i < setClocklist.size(); i++) {
                            setClocklistIds.add(setClocklist.get(i).goodsId);
                        }
                        new Delete()
                                .from(Goods.class)
                                .where("is_second_kill = ?", Config.SECONDVKILLGOODS)
                                .execute();

                        if (currentBrandGoodsList != null && currentBrandGoodsList.listallcat != null
                                && currentBrandGoodsList.listallcat.size() != 0) {

                            for (int i = 0; i < currentBrandGoodsList.listallcat.size(); i++) {
                                SecondKillGoodsResult.ListallcatEntity entity = currentBrandGoodsList.listallcat.get(i);
                                Goods goods1 = new Goods();
                                goods1.goodsId = entity.goods_id;
                                goods1.goodsName = entity.goods_name;
                                goods1.goodsImg = entity.goods_img;
                                goods1.goodsSN = entity.goods_sn;
                                goods1.goodsNumber = entity.goods_number;
                                goods1.marketPrice = entity.market_price;
                                goods1.shopPrice = entity.shop_price;
                                goods1.gysMoney = entity.gys_money;
                                goods1.promotePrice = entity.promote_price;
                                goods1.goodsBrief = entity.goods_brief;
                                goods1.goodsDesc = entity.goods_desc;
                                goods1.sortOrder = entity.sort_order;
                                goods1.isBest = entity.is_best;
                                goods1.isNew = entity.is_new;
                                goods1.isHot = entity.is_hot;
                                goods1.display = entity.display;
                                goods1.giveIntegral = entity.give_integral;
                                goods1.integral = entity.integral;
                                goods1.isPromote = entity.is_promote;
                                goods1.discounta = entity.discounta;
                                goods1.discount = entity.discount;
                                goods1.discountTime = entity.discount_time;
                                goods1.discountName = entity.discount_name;
                                goods1.guige = entity.guige;
                                goods1.unit = entity.danwei;
                                goods1.canRushGoods = Config.RUSH_GOODS_NOT_BEGIN;
                                goods1.is_second_kill = Config.SECONDVKILLGOODS;
                                if (setClocklistIds.contains(entity.goods_id)) {
                                    goods1.setAlarmClock = Goods.HAS_SET_ALARM_CLOCK;
                                } else {

                                    goods1.setAlarmClock = Goods.NOT_SET_ALARM_CLOCK;
                                }
                                if(goodsIds.contains(entity.goods_id)){
                                    goods1.isChecked = Goods.GOODS_IS_BUY;
                                }else{
                                    goods1.isChecked = Goods.GOODS_IS_NOT_BUY;
                                }

                                goods1.save();
                                //data.add(goods1);
                            }
                            List<Goods> data1 = new Select()
                                    .from(Goods.class)
                                    .where("is_second_kill = ?", Config.SECONDVKILLGOODS)
                                    .execute();
                            data.addAll(data1);
                        } else {


                        }


                        return data;
                    }

                    @Override
                    protected void onPostExecute(List<Goods> list) {
                        progress.setVisibility(View.GONE);
                       // swipeContainer.setRefreshing(false);
                        //if (swipeContainer != null) swipeContainer.setRefreshing(false);
                        if (list != null && list.size() > 0) {
                            emptyRushGoods.setVisibility(View.GONE);
                            listview.setVisibility(View.VISIBLE);
                            mAdapter.setData(list, ALARM_CLOCK_TIME);
                        } else {
                            emptyRushGoods.setVisibility(View.VISIBLE);
                            listview.setVisibility(View.GONE);
                        }
                    }
                });
            }
        };
    }

    private void initSecondTime(String startTime, String endTime) {

        startTime = startTime + ":00";
        endTime = endTime + ":00";
        BEGIN_SECOND_KILL = Config.getTimeMill(startTime);
        END_SECOND_KILL = Config.getTimeMill(endTime);
        ALARM_CLOCK_TIME = BEGIN_SECOND_KILL - Config.MINUTE_MILLIS;
        String beginRushTime = Config.getRushTimeValue(startTime) + " 开抢";
        rushGoodsTime.setText(beginRushTime);
    }

    private void initCountDownTime() {
        END_SECOND_KILL = Config.getTodayEndSecondKillTime();
        BEGIN_SECOND_KILL = Config.getTodayBeginSecondKillTime();
        ALARM_CLOCK_TIME = Config.getTodayAlarmClockTime();

    }

    private void initSecondKillView() {
        mAdapter = new SecondKillAdapter(mContext);
        //executeRequest(new GsonRequest(killUrl, CurrentBrandGoodsList.class, getSecondeKillProduct(), errorListener()));
        listview.setAdapter(mAdapter);
        //queryGoods();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATETIME:
                    long currentTime = System.currentTimeMillis();
                    long surplus = END_SECOND_KILL - currentTime;

                    if (currentTime < BEGIN_SECOND_KILL) {
                        state = NOTBEGIN;
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).canRushGoods = Config.CAN_SET_ALARM_CLOCK;
                        }
                        mAdapter.setData(data, ALARM_CLOCK_TIME);

                    }
                    if (surplus > 0) {
                        if (currentTime < END_SECOND_KILL && currentTime > BEGIN_SECOND_KILL) {
                            if (state != BEGINRUSH) {
                                state = BEGINRUSH;
                                for (int i = 0; i < data.size(); i++) {
                                    data.get(i).canRushGoods = Config.RUSH_GOODS_BEGINNING;
                                }
                                mAdapter.setData(data, ALARM_CLOCK_TIME);
                            }
                        }
                        long hour = surplus / Config.HOUR_MILLIS;
                        surplus = surplus - (hour * Config.HOUR_MILLIS);
                        long minute = surplus / Config.MINUTE_MILLIS;
                        surplus = surplus - (minute * Config.MINUTE_MILLIS);
                        long second = surplus / Config.SECOND_MILLIS;
                        secondKillHour.setText(String.format("%02d", hour) + "");
                        secondKillMinute.setText(String.format("%02d", minute) + "");
                        secondKillSecond.setText(String.format("%02d", second) + "");
                    } else {
                        secondKillHour.setText(R.string.kill_zero);
                        secondKillMinute.setText(R.string.kill_zero);
                        secondKillSecond.setText(R.string.kill_zero);
                        if (state != FINISHRUSH) {
                            state = FINISHRUSH;
                            for (int i = 0; i < data.size(); i++) {
                                data.get(i).canRushGoods = Config.RUSH_GOODS_FINISH;
                            }
                            mAdapter.setData(data, ALARM_CLOCK_TIME);
                        }
                        handler.removeCallbacks(mRunnable);
                        timer.cancel();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //handler.postDelayed(mRunnable, DELAYTIME);
        //new Thread(new MyThread()).start();
        //if(timer == null){
        timer = new Timer();
        //}
        if (task != null) {
            task.cancel();
        }
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(task, DELAYTIME, 10);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //new Thread(new MyThread()
        //handler.removeCallbacks(mRunnable);
        task.cancel();
        timer.cancel();
    }

    @OnClick(R.id.main_fab)
    public void onClickFab() {
        Intent intent = new Intent(SecondKillActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        App.getBusInstance().post(new ShowCarListEvent());
        finish();
    }

   /* @Override
    public void onRefresh() {
        referenceSencodeKillGoods();
        *//*if (swipeContainer != null && !swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(true);

        }*//*
    }*/
}
