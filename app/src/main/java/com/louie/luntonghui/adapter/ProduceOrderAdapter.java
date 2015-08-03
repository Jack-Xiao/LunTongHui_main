package com.louie.luntonghui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.ProduceOrder;
import com.louie.luntonghui.model.result.Test;
import com.louie.luntonghui.net.ImageCacheManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/8.
 */
public class ProduceOrderAdapter extends BaseAdapter {
    public List<ProduceOrder.Cart_goodsEntity> data;
    private LayoutInflater inflater;
    private Context mContext;
    public ProduceOrderAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<ProduceOrder.Cart_goodsEntity> cart_goods){
        if(data == null ){
            data = new ArrayList<>();
        }
        data.addAll(cart_goods);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data == null? 0 : data.size();
    }

    @Override
    public ProduceOrder.Cart_goodsEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.car_produce_order_item,null);
            viewHolder = new ViewHolder();

            viewHolder.goodsImg = (ImageView)convertView.findViewById(R.id.goods_img);
            viewHolder.goodsName =(TextView)convertView.findViewById(R.id.goods_name);
            viewHolder.goodsPrice = (TextView)convertView.findViewById(R.id.goods_price);
            viewHolder.goodsNumber = (TextView)convertView.findViewById(R.id.goods_number);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        //        viewHolder.goodsImg = data.get(position).
   /*     Uri uri = Uri.parse(data.get(position).goods_thumb);
        viewHolder.goodsImg.setImageURI(uri);*/

        if(viewHolder.imageRequest !=null){
            viewHolder.imageRequest.cancelRequest();
        }
        viewHolder.imageRequest = ImageCacheManager.loadImage(data.get(position).goods_thumb
            ,ImageCacheManager.getImageListener(viewHolder.goodsImg));
        
        viewHolder.goodsName.setText(data.get(position).goods_name);
        viewHolder.goodsPrice.setText("价格:￥" + data.get(position).goods_price +"/件");
        viewHolder.goodsNumber.setText("数量:"+data.get(position).goods_number);
        return convertView;
    }

    class ViewHolder{
        ImageView goodsImg;
        TextView goodsName;
        TextView goodsPrice;
        TextView goodsNumber;
        public ImageLoader.ImageContainer imageRequest;
    }
}
