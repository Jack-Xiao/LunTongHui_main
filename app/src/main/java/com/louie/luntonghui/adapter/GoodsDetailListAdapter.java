package com.louie.luntonghui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.Goods;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.AddGoodsResult;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.rest.RetrofitUtils;
import com.louie.luntonghui.rest.ServiceManager;
import com.louie.luntonghui.ui.category.GoodsDetailActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.AlertDialogUtil;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

//import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2015/6/23.
 */
public class GoodsDetailListAdapter extends BaseAdapter implements AlertDialogUtil.AlertDialogListener{
    private List<Goods> list;
    private LayoutInflater inflater;
    private Context mContext;
    private ServiceManager.LunTongHuiApi mApi;
    private final String userId;
    private boolean isUserful = false;
    private int mCurrentResetCount ;
    private int curPosition;
    private boolean isAdd = true;
    private ShoppingCar car;
    public ReferenceBadgeListener mListener;

    public interface ReferenceBadgeListener{
        public void referenceBadge();
    }
    public GoodsDetailListAdapter(GoodsDetailActivity goodsDetailActivity) {
        list = new ArrayList<>();
        mContext = goodsDetailActivity;
        mListener = goodsDetailActivity;
        inflater = LayoutInflater.from(goodsDetailActivity);
        mApi = RetrofitUtils.createApi(mContext, ServiceManager.LunTongHuiApi.class);
        userId = DefaultShared.getString(RegisterLogin.USERUID,"0");
    }


    public List<Goods> getData(){
        return list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            viewHolder.present = (TextView)convertView.findViewById(R.id.present);
            viewHolder.discount = (TextView)convertView.findViewById(R.id.discount);
            viewHolder.prim = (TextView)convertView.findViewById(R.id.prim);
            viewHolder.mTeJia = (ImageView)convertView.findViewById(R.id.tiejia);

            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }

        Glide.with(mContext).load(list.get(position).goodsImg)
                .placeholder(R.drawable.default_image_in_no_source) //错误或空白占位
                .into(viewHolder.mGoodsImg);

        viewHolder.mGoodsName.setText(list.get(position).goodsName);
        //viewHolder.mMarketPrice.setMarketPrice(list.get(position).marketPrice);
        viewHolder.mMarketPrice.setText("￥" + list.get(position).marketPrice + "/" + list.get(position).unit);
        viewHolder.mMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.mShopPrice.setText("￥" + list.get(position).shopPrice + "/" +list.get(position).unit);

        //仅服务商显示服务费
        if(DefaultShared.getString(RegisterLogin.USER_TYPE,RegisterLogin.USER_DEFAULT)
                .equals(RegisterLogin.USER_SERVICE)){
            viewHolder.servicePrice.setText("服务费:￥" + list.get(position).gysMoney);
        }

        if(list.get(position).isChecked.equals(Goods.GOODS_IS_BUY)){
            viewHolder.btnFastBuy.setBackgroundColor(mContext.getResources().getColor(R.color.useful_blue));
        }else{
            viewHolder.btnFastBuy.setBackgroundResource(R.drawable.category_fast_buy);
        }
        //viewHolder.btnFastBuy.setEnabled(!list.get(position).isChecked.equals(Goods.GOODS_IS_BUY));
        viewHolder.btnFastBuy.setTag(position);
        viewHolder.btnFastBuy.setOnClickListener(clickListener);
        Integer discountType = Integer.parseInt(list.get(position).discountType);

        viewHolder.prim.setVisibility(View.GONE);
        viewHolder.discount.setVisibility(View.GONE);
        viewHolder.present.setVisibility(View.GONE);
        viewHolder.mTeJia.setVisibility(View.GONE);

        String birary = Integer.toBinaryString(discountType);
        if(discountType !=0) {
            for(int i =birary.length() -1;i>=0;i--){
               if(i== birary.length() -1){
                   if(birary.substring(birary.length()-1).equals("1")){
                       viewHolder.discount.setVisibility(View.VISIBLE);
                       viewHolder.mTeJia.setVisibility(View.VISIBLE);
                   }
               }else if(i == birary.length() -2){
                   if(birary.substring(birary.length()-2,birary.length() -1).equals("1"))
                    viewHolder.present.setVisibility(View.VISIBLE);
               }else{
                   if(birary.substring(i,i+1).equals("1")){
                       viewHolder.prim.setVisibility(View.VISIBLE);
                       viewHolder.mTeJia.setVisibility(View.VISIBLE);
                       break;
                   }
               }
            }
        }


        String inventory = list.get(position).inventory;
        if(inventory.equals(Goods.NO_GOODS)){
            viewHolder.btnFastBuy.setEnabled(false);
            viewHolder.btnFastBuy.setText("缺货");
            //@drawable/category_fast_buy;
            viewHolder.btnFastBuy.setBackgroundResource(R.drawable.category_fast_buy_grey);
        }else{
            viewHolder.btnFastBuy.setEnabled(true);
            viewHolder.btnFastBuy.setText("快订");
            viewHolder.btnFastBuy.setBackgroundResource(R.drawable.category_fast_buy);
        }

        return convertView;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isAdd = true;

            int position = Integer.parseInt(v.getTag().toString());
            curPosition = position;
            Goods goods = list.get(position);
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

            AlertDialogUtil.getInstance().show(mContext, GoodsDetailListAdapter.this, goods,count);

            if(!isUserful) return;

            /*Log.d("imgBuy.,", "mContext........  2 ");

            ToastUtil.showShortToast(mContext, "购买成功..."  + goodsId);
            Log.d("imgBuy.,","mContext........   3");
            Map<String,String> args = new HashMap<String, String>();
            Log.d("imgBuy.,", "mContext........   4");
            args.put("goods_id", goodsId);
            Log.d("imgBuy.,", "mContext........  5");
            args.put("number", "1");
            Log.d("imgBuy.,", "mContext........   6");
            args.put("userid", userId);
            ToastUtil.showShortToast(mContext, "userId..." + userId);
            Result result = mApi.addCarGoods(args);
            ToastUtil.showShortToast(mContext, "userId..." + result);*/

            //RequestManager.addRequest();
            /*Log.d("imgBuy.,","mContext........   7");
            if(result.rsgcode.equals(ConstantURL.SUCCESSCODE)){
                ToastUtil.showShortToast(mContext,"购买成功");
            }else{
                ToastUtil.showShortToast(mContext,result.rsgmsg);
            }*/
        }
    };

    private Response.ErrorListener errorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.showLongToast(mContext,volleyError.getMessage());
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
                    list.get(position).isChecked = Goods.GOODS_IS_BUY;
                    isUserful = false;
                    ShoppingCar car = new ShoppingCar();
                    car.goodsNumber = count + "";
                    car.goodsId = goodsId;
                    car.carId = result.cat_id;
                    car.save();
                    mListener.referenceBadge();
                    notifyDataSetChanged();
                }else{
                    ToastUtil.showShortToast(mContext,result.rsgmsg);
                }
            }
        };
    }

    public void setData(List<Goods> goodses) {
        list.clear();
        list.addAll(goodses);
        notifyDataSetChanged();
    }

    public void addData(List<Goods> goodses){
        if(goodses ==null) return;
        list.addAll(goodses);
        notifyDataSetChanged();
    }

    @Override
    public void reset(int count) {
        String goodsId = list.get(curPosition).goodsId;
        if(isAdd){
            String url = String.format(ConstantURL.ADD_GOODS,userId,goodsId,count);
            RequestManager.addRequest(new GsonRequest(url,AddGoodsResult.class,
                    addGoodsListener(goodsId,curPosition,count),errorListener()),this);
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
                    mListener.referenceBadge();
                }else{
                    ToastUtil.showShortToast(mContext,result.rsgmsg);
                }
            }
        };
    }

    class ViewHolder{
        //SimpleDraweeView mGoodsImg;
        ImageView mGoodsImg;
        TextView mGoodsName;
        TextView mMarketPrice;
        TextView mShopPrice;
        TextView servicePrice;
        Button btnFastBuy;
        ImageView mTeJia;

        TextView present;
        TextView discount;
        TextView prim;

        public ImageLoader.ImageContainer imageRequest;
    }
}
