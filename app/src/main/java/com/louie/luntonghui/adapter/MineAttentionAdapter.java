package com.louie.luntonghui.adapter;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.AttentionGoods;
import com.louie.luntonghui.model.db.Goods;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.AddGoodsResult;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.PictureActivity;
import com.louie.luntonghui.ui.category.GoodsDetailActivity;
import com.louie.luntonghui.ui.category.GoodsDetailBuyActivity;
import com.louie.luntonghui.ui.mine.MineAttentionActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.AlertDialogUtil;
import com.louie.luntonghui.util.BaseAlertDialogUtil;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by Jack on 15/8/12.
 */
public class MineAttentionAdapter extends RecyclerView.Adapter<MineAttentionAdapter.ViewHolder>
        implements AlertDialogUtil.AlertDialogListener, BaseAlertDialogUtil.BaseAlertDialogListener,
        OnClickListener{
    private List<AttentionGoods> data;
    private MineAttentionActivity mContext;

    private List<String> shoppCartGoodsIds;
    private final String userId;
    private final String userType;
    private ShoppingCar car;
    private int curDelPosition;
    private HashMap<String,String> goodsRecIds;

    public MineAttentionAdapter(MineAttentionActivity context) {
        this.mContext = context;
        data = new ArrayList<>();
        shoppCartGoodsIds = new ArrayList<>();
        userId = DefaultShared.getString(RegisterLogin.USERUID,"0");
        userType = DefaultShared.getString(RegisterLogin.USER_TYPE,RegisterLogin.USER_DEFAULT);
        goodsRecIds = new HashMap<>();
    }

    public List<AttentionGoods> getData(){
        return data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_attention_goods_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        AttentionGoods goods = data.get(position);

        Picasso.with(mContext).load(data.get(position).goodsImg)
                .placeholder(R.drawable.default_image_in_no_source) //错误或空白占位
                //.centerCrop()
                .into(viewHolder.goodsImg);

        viewHolder.goodsName.setText(goods.goodsName);
        //viewHolder.mMarketPrice.setMarketPrice(list.get(position).marketPrice);
        viewHolder.marketPrice.setText("￥" + goods.marketPrice + "/" + goods.unit);
        viewHolder.marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.shopPrice.setText("￥" + goods.shopPrice + "/" + goods.unit);

        //仅服务商显示服务费
        if(DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT)
                .equals(RegisterLogin.USER_SERVICE)){
            viewHolder.servicePrice.setText("服务费:￥" + goods.gysMoney);
        }

        if(shoppCartGoodsIds.contains(goods.goodsId)){
            viewHolder.fastBuy.setBackgroundColor(mContext.getResources().getColor(R.color.useful_blue));
        }else{
            viewHolder.fastBuy.setBackgroundResource(R.drawable.category_fast_buy);
        }

        viewHolder.fastBuy.setTag(position);
        //viewHolder.fastBuy.setOnClickListener(fastBuyClickListener);
        viewHolder.fastBuy.setOnClickListener(this);


        viewHolder.imgDelete.setTag(position);
       // viewHolder.imgDelete.setOnClickListener(deleteClickListener);
        viewHolder.imgDelete.setOnClickListener(this);

        viewHolder.goodsImg.setTag(position);
        //viewHolder.goodsImg.setOnClickListener(imageClickListener);
        viewHolder.goodsImg.setOnClickListener(this);

        viewHolder.linearLayout.setTag(position);
        viewHolder.linearLayout.setOnClickListener(this);


        Integer discountType = Integer.parseInt(goods.discountType);
        String birary = Integer.toBinaryString(discountType);
        if(discountType !=0) {
            for(int i = birary.length() -1;i>=0;i--){
                if(i == birary.length() -1){
                    Log.d("length ", birary.substring(birary.length() - 1) + "-1");
                    if(birary.substring(birary.length()-1).equals("1"))
                        viewHolder.discount.setVisibility(View.VISIBLE);
                }else if(i == birary.length() -2){
                    Log.d("length ",birary.substring(birary.length()-2,birary.length() -1) + "-2");
                    if(birary.substring(birary.length()-2,birary.length() -1).equals("1"))
                        viewHolder.present.setVisibility(View.VISIBLE);
                }else{
                    Log.d("length ", birary.substring(i,i+1) + "-3");
                    if(birary.substring(i,i+1).equals("1")){
                        viewHolder.prim.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }
    }

    private boolean isAdd;
    private int curPosition;

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<AttentionGoods> attentionGoodses) {
        data.clear();
        data.addAll(attentionGoodses);

        List<ShoppingCar>  carts = new Select()
                .from(ShoppingCar.class)
                .execute();
        shoppCartGoodsIds.clear();
        for(int i=0;i<carts.size();i++){
            shoppCartGoodsIds.add(carts.get(i).goodsId);
        }
        notifyDataSetChanged();
    }

    @Override
    public void reset(int count) {
        String goodsId = data.get(curPosition).goodsId;
        if(isAdd){
            String url = String.format(ConstantURL.ADD_GOODS,userId,goodsId,count);
            RequestManager.addRequest(new GsonRequest(url, AddGoodsResult.class,
                    addGoodsListener(goodsId, curPosition, count), errorListener()), this);
        }else{
            String carId = car.carId;

            String url = String.format(ConstantURL.EDIT_GOODS,carId,userId,count);
            RequestManager.addRequest(new GsonRequest(url,Result.class,
                    editGoodsListener(carId,count),errorListener()),this);
        }
    }

    private Response.Listener<Result> editGoodsListener(final String carId,final int count) {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(Result result) {
                if(result.rsgcode.equals(ConstantURL.SUCCESSCODE)){
                    ToastUtil.showShortToast(mContext,R.string.edit_cart_success);
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


    private Response.Listener<AddGoodsResult> addGoodsListener(final String goodsId,final int position,final int count) {
        return new Response.Listener<AddGoodsResult>() {
            @Override
            public void onResponse(AddGoodsResult result) {
                if(result.rsgcode.equals(ConstantURL.SUCCESSCODE)){
                    ToastUtil.showShortToast(mContext, R.string.input_cart);

                    shoppCartGoodsIds.add(goodsId);

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

    private Response.ErrorListener errorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.showShortToast(mContext,"出错了~");
            }
        };
    }

    @Override
    public void confirm() {

        String url = String.format(ConstantURL.DELETEATTENTION,userId,data.get(curDelPosition).recId);
        RequestManager.addRequest(new GsonRequest(url,Result.class,deleteAttentionRequest(),errorListener()),this);

    }


    private Response.Listener<Result> deleteAttentionRequest() {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(Result result) {
                if(result.rsgcode.equals(ConstantURL.SUCCESSCODE)){
                    String goodsId = data.get(curDelPosition).goodsId;
                    new Delete()
                            .from(AttentionGoods.class)
                            .where("goods_id=?",goodsId)
                            .execute();
                    data.remove(curDelPosition);
                    notifyDataSetChanged();
                }else{
                    ToastUtil.showShortToast(mContext,result.rsgmsg);
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        int position ;
        switch (v.getId()){
            case R.id.goods_img:
                position = Integer.parseInt(v.getTag().toString());
                String imageUrl = data.get(position).goodsImg;
                Bundle bundle = new Bundle();
                bundle.putString(PictureActivity.EXTRA_IMAGE_URL, imageUrl);

                IntentUtil.startActivityWiehAlpha(mContext, PictureActivity.class, bundle);
                break;
            case R.id.delete:
                curDelPosition = (Integer)v.getTag();
                BaseAlertDialogUtil.getInstance()
                        .setMessage(R.string.attention_delete_warnning)
                        .setCanceledOnTouchOutside(true)
                        .setNegativeContent(R.string.to_think_again)
                        .setPositiveContent(R.string.choose_delete);

                BaseAlertDialogUtil.getInstance().show(mContext, MineAttentionAdapter.this);

                break;
            case R.id.fast_buy:
                isAdd = true;
                position = Integer.parseInt(v.getTag().toString());
                curPosition = position;

                AttentionGoods attentionGoods = data.get(position);
                String goodsId = attentionGoods.goodsId;

                Goods goods1 = new Goods();
                goods1.goodsName = attentionGoods.goodsName;
                goods1.shopPrice = attentionGoods.shopPrice;
                goods1.unit = attentionGoods.unit;
                goods1.guige = attentionGoods.guige;

                int count =1;
                car = new Select()
                        .from(ShoppingCar.class)
                        .where("goods_id = ?",goodsId)
                        .executeSingle();

                if(car != null){
                    count = Integer.parseInt(car.goodsNumber);
                    isAdd = false;
                }
                AlertDialogUtil.getInstance().show(mContext, MineAttentionAdapter.this, goods1,count);
                break;
            default:
                position = Integer.parseInt(v.getTag().toString());
                Bundle bundle1 = new Bundle();
                bundle1.putString(GoodsDetailActivity.GOODSDETAILID, data.get(position).goodsId);
                IntentUtil.startActivity(mContext, GoodsDetailBuyActivity.class, bundle1);
                break;
        }
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'activity_attention_goods_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */

    public class ViewHolder extends RecyclerView.ViewHolder{
        //@InjectView(R.id.goods_img)
        ImageView goodsImg;
        //@InjectView(R.id.goods_name)
        TextView goodsName;
        //@InjectView(R.id.shop_price)
        TextView shopPrice;
        //@InjectView(R.id.market_price)
        TextView marketPrice;
        //@InjectView(R.id.service_price)
        TextView servicePrice;
        //@InjectView(R.id.fast_buy)
        Button fastBuy;
        //@InjectView(R.id.delete)
        ImageView imgDelete;
        LinearLayout linearLayout;

        TextView present;
        TextView discount;
        TextView prim;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            goodsImg = (ImageView)itemView.findViewById(R.id.goods_img);
            goodsName = (TextView)itemView.findViewById(R.id.goods_name);
            shopPrice = (TextView)itemView.findViewById(R.id.shop_price);
            marketPrice = (TextView)itemView.findViewById(R.id.market_price);
            servicePrice = (TextView)itemView.findViewById(R.id.service_price);
            fastBuy = (Button)itemView.findViewById(R.id.fast_buy);
            imgDelete = (ImageView)itemView.findViewById(R.id.delete);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.whole);
            present = (TextView)itemView.findViewById(R.id.present);
            discount = (TextView)itemView.findViewById(R.id.discount);
            prim = (TextView)itemView.findViewById(R.id.prim);
        }

        /*@OnClick(R.id.fast_buy)
        public void onClickFastBuy(View v){

            Log.d("operation : " , " fast_buy1");
        }*/

    }
}
