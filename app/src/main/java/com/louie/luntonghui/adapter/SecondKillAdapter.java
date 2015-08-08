package com.louie.luntonghui.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.Goods;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.AddGoodsResult;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.receiver.AlarmReceiver;
import com.louie.luntonghui.ui.Home.SecondKillActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.AlertDialogUtil;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.MyAlarmClockDialogUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Administrator on 2015/7/24.
 */
public class SecondKillAdapter extends BaseAdapter implements AlertDialogUtil.AlertDialogListener, MyAlarmClockDialogUtil.AlarmClockListener {
    private List<Goods> mList;
    private SecondKillActivity mContext;
    private LayoutInflater inflater;
    private boolean isAdd = true;
    private int curPosition;
    private ShoppingCar car;
    private final String userId;
    private boolean isUserful = false;
    private String currentClockType = Config.CAN_SET_ALARM_CLOCK;
    private View setAlarmClock;
    private View cancelAlarmClock;
    private Intent intent;
    private PendingIntent sender;
    private long ALARM_TIME;


    public SecondKillAdapter(SecondKillActivity context) {
        mContext = context;
        mList = new ArrayList<Goods>();
        inflater = LayoutInflater.from(mContext);
        userId = DefaultShared.getString(RegisterLogin.USERUID, "0");

        setAlarmClock = LayoutInflater.from(context).inflate(R.layout.view_set_alarm_clock_success, null);
        cancelAlarmClock = LayoutInflater.from(context).inflate(R.layout.view_set_alarm_clock_cancel,null);

        intent = new Intent(mContext, AlarmReceiver.class);
        sender = PendingIntent.getBroadcast(mContext,0,intent,0);

    }

    @Override
    public int getCount() {
        return mList == null ? 0 :mList.size();
    }

    @Override
    public Goods getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView  == null){
            convertView = inflater.inflate(R.layout.activity_goods_detail_list_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mShopPrice = (TextView)convertView.findViewById(R.id.shop_price);
            viewHolder.mGoodsImg = (ImageView)convertView.findViewById(R.id.goods_img);
            viewHolder.mGoodsName = (TextView)convertView.findViewById(R.id.goods_name);
            viewHolder.mMarketPrice = (TextView)convertView.findViewById(R.id.market_price);
            viewHolder.servicePrice = (TextView)convertView.findViewById(R.id.service_price);
            viewHolder.btnFastBuy = (Button)convertView.findViewById(R.id.fast_buy);

            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }

        if (viewHolder.imageRequest != null) {
            viewHolder.imageRequest.cancelRequest();
        }
        Picasso.with(mContext).load(mList.get(position).goodsImg)
                //.placeholder(R.drawable.user_placeholder) 错误或空白占位
                //.centerCrop()
                .into(viewHolder.mGoodsImg);
        /*
        viewHolder.imageRequest = ImageCacheManager.loadImage(mList.get(position).goodsImg,
                ImageCacheManager.getImageListener(viewHolder.mGoodsImg));*/

        viewHolder.mGoodsName.setText(mList.get(position).goodsName);
        //viewHolder.mMarketPrice.setMarketPrice(list.get(position).marketPrice);
        viewHolder.mMarketPrice.setText("￥" + mList.get(position).marketPrice + "/" + mList.get(position).unit);
        viewHolder.mMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.mShopPrice.setText("￥" + mList.get(position).shopPrice + "/" + mList.get(position).unit);


        if(mList.get(position).canRushGoods.equals(Config.CAN_SET_ALARM_CLOCK)){
            viewHolder.btnFastBuy.setEnabled(true);
            currentClockType = Config.CAN_SET_ALARM_CLOCK;
            //viewHolder.btnFastBuy.setTag(Config.CAN_SET_ALARM_CLOCK);
            if(mList.get(position).setAlarmClock.equals(Goods.HAS_SET_ALARM_CLOCK)){
                viewHolder.btnFastBuy.setEnabled(true);
                viewHolder.btnFastBuy.setText(R.string.has_set);
                viewHolder.btnFastBuy.setBackgroundResource(R.drawable.panic_buying_green_background);
            }else{
                viewHolder.btnFastBuy.setEnabled(true);
                viewHolder.btnFastBuy.setText(R.string.set_alarm_clock);
                viewHolder.btnFastBuy.setBackgroundResource(R.drawable.panic_buying_green_background);
            }

        }else if(mList.get(position).canRushGoods.equals(Config.CAN_NOT_SET_ALARM_CLOCK)){
            viewHolder.btnFastBuy.setEnabled(false);

        }else if(mList.get(position).canRushGoods.equals(Config.RUSH_GOODS_BEGINNING)){
            viewHolder.btnFastBuy.setEnabled(true);
            currentClockType = Config.RUSH_GOODS_BEGINNING;
            viewHolder.btnFastBuy.setText(R.string.rush_to_purchash);

            if(mList.get(position).isChecked.equals(Goods.GOODS_IS_BUY)){
                viewHolder.btnFastBuy.setBackgroundColor(mContext.getResources().getColor(R.color.useful_blue));
            }else{
                viewHolder.btnFastBuy.setBackgroundResource(R.drawable.panic_buying_red_background);
            }
        }else if(mList.get(position).canRushGoods.equals(Config.RUSH_GOODS_FINISH)){
            viewHolder.btnFastBuy.setText(R.string.hard_work_next_time);
            viewHolder.btnFastBuy.setBackgroundResource(R.drawable.panic_buying_yellow_background);
            viewHolder.btnFastBuy.setEnabled(false);
        }



        //viewHolder.btnFastBuy.setText(R.string.rush_to_purchash);

        /*if(mList.get(position).isChecked.equals(Goods.GOODS_IS_BUY)){
            viewHolder.btnFastBuy.setBackgroundColor(mContext.getResources().getColor(R.color.useful_blue));
        }else{
            viewHolder.btnFastBuy.setBackgroundResource(R.drawable.category_fast_buy);
        }*/
        //viewHolder.btnFastBuy.setEnabled(!list.get(position).isChecked.equals(Goods.GOODS_IS_BUY));
        viewHolder.btnFastBuy.setTag(position);
        viewHolder.btnFastBuy.setOnClickListener(clickListener);

        return convertView;
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int position = Integer.parseInt(v.getTag().toString());
            curPosition = position;
            if(currentClockType.equals(Config.RUSH_GOODS_BEGINNING)){
                isAdd = true;
                Goods goods = mList.get(position);
                String goodsId = goods.goodsId;

                int count =1;
                car = new Select()
                        .from(ShoppingCar.class)
                        .where("goods_id = ?",goodsId)
                        .executeSingle();
                if(car != null){
                    count = Integer.parseInt(car.goodsNumber);
                    isAdd = false;
                }

               AlertDialogUtil.getInstance().show(mContext, SecondKillAdapter.this, goods,count);
            }else if(currentClockType.equals(Config.CAN_SET_ALARM_CLOCK)){
                //MyAlarmClockDialogUtil.getInstance().show(mContext,SecondKillAdapter.this);

                String goodsId = mList.get(position).goodsId;
                int size = new Select()
                        .from(Goods.class)
                        .where("goods_id= ? and setAlarmClock=?", goodsId, Goods.HAS_SET_ALARM_CLOCK)
                        .execute()
                        .size();

                Toast toast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                if (size == 1) {
                    toast.setView(cancelAlarmClock);
                    new Update(Goods.class)
                            .set("setAlarmClock=?", Goods.NOT_SET_ALARM_CLOCK)
                            .where("goods_id=?", goodsId)
                            .execute();
                    mList.get(position).setAlarmClock = Goods.NOT_SET_ALARM_CLOCK;


                } else {
                    toast.setView(setAlarmClock);

                    new Update(Goods.class)
                            .set("setAlarmClock=?", Goods.HAS_SET_ALARM_CLOCK)
                            .where("goods_id=?", goodsId)
                            .execute();
                    mList.get(position).setAlarmClock = Goods.HAS_SET_ALARM_CLOCK;

                }
                int receiverSize = new Select()
                        .from(Goods.class)
                        .where("setAlarmClock = ?", Goods.HAS_SET_ALARM_CLOCK)
                        .execute().size();

                //cancel the alarm clock.
                if(receiverSize == 0){

                    AlarmManager am = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
                    am.cancel(sender);
                }else{
                    //register the alarm clock
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));

                    long speedSecond = ALARM_TIME / 1000L;

                    int intSpeedSecond = (int)speedSecond;
                    //intSpeedSecond = 10;

                    calendar.add(Calendar.SECOND,intSpeedSecond);
                    AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                    manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
                }
                toast.show();
                notifyDataSetChanged();
            }
        }
    };

    public void setData(List<Goods> goodses,long ALARM_TIME) {
        if(goodses == null) return;
        if(mList == null){
            mList = new ArrayList<>();
        }
        this.ALARM_TIME = ALARM_TIME;
        mList.clear();
        mList.addAll(goodses);
        notifyDataSetChanged();
    }

    @Override
    public void reset(int count) {
        String goodsId = mList.get(curPosition).goodsId;
        if (isAdd) {
            String url = String.format(ConstantURL.ADD_GOODS, userId, goodsId, count);
            RequestManager.addRequest(new GsonRequest(url, AddGoodsResult.class,
                    addGoodsListener(goodsId, curPosition, count), errorListener()), this);
        } else {
            String carId = car.carId;
            String url = String.format(ConstantURL.EDIT_GOODS, carId, userId, count);
            RequestManager.addRequest(new GsonRequest(url, Result.class,
                    editGoodsListener(carId, count), errorListener()), this);
        }
    }

    @Override
    public void confirmAlarmColock(int type, String hour) {

    }

    class ViewHolder{
        //SimpleDraweeView mGoodsImg;
        ImageView mGoodsImg;
        TextView mGoodsName;
        TextView mMarketPrice;
        TextView mShopPrice;
        TextView servicePrice;
        Button btnFastBuy;
        public ImageLoader.ImageContainer imageRequest;
    }

    private Response.ErrorListener errorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.showLongToast(mContext, volleyError.getMessage());
            }
        };
    }

    private Response.Listener<AddGoodsResult> addGoodsListener(final String goodsId,final int position,final int count) {
        return new Response.Listener<AddGoodsResult>() {
            @Override
            public void onResponse(AddGoodsResult result) {
                if(result.rsgcode.equals(ConstantURL.SUCCESSCODE)){
                    ToastUtil.showShortToast(mContext, R.string.input_cart);
                    new Update(Goods.class)
                            .set("isChecked = ?",Goods.GOODS_IS_BUY)
                            .where("goods_id=?",goodsId)
                            .execute();
                    mList.get(position).isChecked = Goods.GOODS_IS_BUY;
                    isUserful = false;
                    ShoppingCar car = new ShoppingCar();
                    car.goodsNumber = count + "";
                    car.goodsId = goodsId;
                    car.carId = result.cat_id;
                    car.save();
                    notifyDataSetChanged();
                }else{
                    ToastUtil.showShortToast(mContext,result.rsgmsg);
                }
            }
        };
    }

    private Response.Listener<Result> editGoodsListener(final String carId,final int count) {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(Result result) {
                if(result.rsgcode.equals(ConstantURL.SUCCESSCODE)){
                    ToastUtil.showShortToast(mContext, R.string.edit_cart_success);
                    new Update(ShoppingCar.class)
                            .set("goods_number = ?",count)
                            .where("car_id=?",carId)
                            .execute();
                }else{
                    ToastUtil.showShortToast(mContext,result.rsgmsg);
                }
            }
        };
    }
}
