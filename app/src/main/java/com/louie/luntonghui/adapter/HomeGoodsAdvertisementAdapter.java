package com.louie.luntonghui.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.HomeAdversion;
import com.louie.luntonghui.ui.category.GoodsDetailActivity;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 15/10/23.
 */
public class HomeGoodsAdvertisementAdapter extends BaseAdapter{

    public List<HomeAdversion.GoodsAdvPartEntity.GoodsPartAdvArrayEntity> data;
    private Activity mContext;

    public HomeGoodsAdvertisementAdapter(Activity context){
        this.mContext = context;
    }

    public HomeGoodsAdvertisementAdapter(Activity context,
                                         List<HomeAdversion.GoodsAdvPartEntity.GoodsPartAdvArrayEntity> entity){
        this.mContext = context;
        if(data == null){
            data = new ArrayList<>();
        }
        data.clear();
        data.addAll(entity);
        notifyDataSetChanged();
    }

    public void setData(List<HomeAdversion.GoodsAdvPartEntity.GoodsPartAdvArrayEntity> entity){
        if(data == null){
            data = new ArrayList<>();
        }
        data.clear();
        data.addAll(entity);
        notifyDataSetChanged();
    }

    public void addData(List<HomeAdversion.GoodsAdvPartEntity.GoodsPartAdvArrayEntity> entity){
        if(data == null){
            data = new ArrayList<>();
        }
        data.clear();
        data.addAll(entity);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return (data == null)? 0 : data.size();
    }

    @Override
    public HomeAdversion.GoodsAdvPartEntity.GoodsPartAdvArrayEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_home_adver_part_item,parent,false);
            vh = new ViewHolder();
            vh.imageView = (ImageView)convertView.findViewById(R.id.goods_adv_item_each_image);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        Glide.with(mContext)
                .load(data.get(position).img)
                .placeholder(R.drawable.default_image_in_no_source)
                .crossFade()
                .into(vh.imageView);

        //vh.imageView.setTag(position);
        vh.imageView.setTag(R.string.position,position);
        vh.imageView.setOnClickListener(mListener);

        return convertView;
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //String url = v.getTag().toString();
            //int position = (int) v.getTag();
            int position = (int) v.getTag(R.string.position);
            String id = data.get(position).cat_id;
            String url = ConstantURL.CATEGORYGOODS + id;

            Bundle bundle = new Bundle();
            bundle.putString(GoodsDetailActivity.GOODSDETAILURL, url);
            bundle.putString(GoodsDetailActivity.GOODSDETAILID,id);
            IntentUtil.startActivity(mContext,GoodsDetailActivity.class,bundle);
        }
    };

    static class ViewHolder{
        ImageView imageView;
        //public ImageLoader.ImageContainer imageRequest;

    }
}
