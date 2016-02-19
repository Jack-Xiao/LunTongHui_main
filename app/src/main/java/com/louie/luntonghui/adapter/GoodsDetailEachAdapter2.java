package com.louie.luntonghui.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.GoodsList;
import com.louie.luntonghui.ui.category.GoodsDetailActivity;
import com.louie.luntonghui.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/19.
 */
public class GoodsDetailEachAdapter2 extends BaseAdapter {
    private Activity mContext;
    private List<GoodsList.Goods_listEntity.Goods_list1Entity.Goods_list2Entity> data =
            new ArrayList<>();
    private LayoutInflater inflater;

    public GoodsDetailEachAdapter2(Activity mContext, List<GoodsList.Goods_listEntity.Goods_list1Entity.Goods_list2Entity> goods_list2) {
        this.mContext = mContext;
        data.addAll(goods_list2);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int curPositon = position;

        ViewHolder vh = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_goods_detail_each,parent,false);
            vh = new ViewHolder();
            vh.imageView = (ImageView)convertView.findViewById(R.id.goods_detail_item_each_image);
            vh.textView = (TextView)convertView.findViewById(R.id.goods_detail_item_each_text);
            convertView.setTag(vh);
        }else{
           vh = (ViewHolder)convertView.getTag();
        }

        vh.textView.setText(data.get(position).name);

        Glide.with(mContext).load(data.get(curPositon).img)
                .placeholder(R.drawable.default_image_in_no_source)
                .into(vh.imageView);

        vh.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(GoodsDetailActivity.GOODSDETAILURL, data.get(curPositon).url);
                    bundle.putString(GoodsDetailActivity.GOODSDETAILID,data.get(curPositon).id);
                    IntentUtil.startActivity(mContext,GoodsDetailActivity.class,bundle);
                }
        });

        return convertView;
    }

    static class ViewHolder{
        ImageView imageView;
        TextView textView;

        //public com.android.volley.toolbox.ImageLoader.ImageContainer imageRequest;
    }
}
