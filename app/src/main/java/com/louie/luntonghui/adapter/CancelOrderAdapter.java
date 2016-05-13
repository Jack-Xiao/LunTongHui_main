package com.louie.luntonghui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.OrderDetailResult;
import com.louie.luntonghui.util.CancelOrderDialogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by Jack on 16/4/29.
 */
public class CancelOrderAdapter extends RecyclerView.Adapter<CancelOrderAdapter.ViewHolder> implements CancelOrderDialogUtil.CancelOrderListener {

    List<OrderDetailResult.GoodsListEntity> goodses;
    Context mContext;
    Map<String,Product> keyMap;
    private int curPosition;


    public CancelOrderAdapter(Context context,List<OrderDetailResult.GoodsListEntity> list) {
        goodses = new ArrayList<>();
        mContext = context;
        keyMap = new HashMap<>();
        for(OrderDetailResult.GoodsListEntity entity : list){
            if(entity.rid.equals(ShoppingCar.NOTGIVEAWAY)){
                goodses.add(entity);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.adapter_cancel_order, parent, false);

        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(mContext)
                .load(goodses.get(position).goods_thumb)
                .crossFade()
                .into(holder.goodsImg);

        holder.goodsName.setText(goodses.get(position).goods_name);
        holder.goodsPrice.setText(goodses.get(position).goods_price);
        holder.goodsCount.setText("已购买: " + goodses.get(position).goods_number);
        if(keyMap.containsKey(goodses.get(position).goods_id)){
            holder.returnGoodsCount.setText(keyMap.get(goodses.get(position).goods_id).returnCount + "");
        }else{
            holder.returnGoodsCount.setText("0");
        }

        holder.returnGoodsCount.setOnClickListener(v -> {
            OrderDetailResult.GoodsListEntity entity = goodses.get(position);
            String returnGoodsCount = "0";
            if(keyMap.containsKey(goodses.get(position).goods_id)){
                returnGoodsCount = keyMap.get(goodses.get(position).goods_id).returnCount;
            }
            CancelOrderDialogUtil.getInstance().show(position,entity,mContext,CancelOrderAdapter.this,returnGoodsCount);
        });
    }

    public Map<String,Product> getMapValue(){
        return keyMap;
    }

    @Override
    public int getItemCount() {
        return goodses == null ? 0 : goodses.size();
    }

    @Override
    public void onConfirm(int position, String returnCount, String reason) {
        Product product = new Product();
        product.reason = reason;
        product.returnCount = returnCount;
        String goodsId = goodses.get(position).goods_id;
        keyMap.put(goodsId,product);
        notifyDataSetChanged(); //直接更新
    }

    public class Product{
        public String returnCount;
        public String reason;
    }


    class ViewHolder extends RecyclerView.ViewHolder  {
        @InjectView(R.id.goods_img)
        ImageView goodsImg;
        @InjectView(R.id.goods_name)
        TextView goodsName;
        @InjectView(R.id.goods_price)
        TextView goodsPrice;
        @InjectView(R.id.goods_count)
        TextView goodsCount;
        @InjectView(R.id.return_goods_count)
        TextView returnGoodsCount;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
