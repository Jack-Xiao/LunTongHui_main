package com.louie.luntonghui.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.HomeAdversion;
import com.louie.luntonghui.ui.category.GoodsDetailActivity;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.view.MyGridView;

import java.util.List;

/**
 * Created by Jack on 15/12/29.
 */
public class HomeAdvertisementArrayAdapter extends BaseAdapter {
    private List<HomeAdversion.GoodsAdvPartEntity> data;
    private LayoutInflater inflater;
    private FragmentActivity mContext;
    private HomeGoodsAdvertisementAdapter adapter;


    public HomeAdvertisementArrayAdapter(FragmentActivity activity, List<HomeAdversion.GoodsAdvPartEntity> list) {
        mContext = activity;
        inflater = LayoutInflater.from(activity);
        data = list;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        //if(position == 1 ||  )
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
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.view_home_adv_array,null);
            viewHolder.mGridView = (MyGridView)convertView.findViewById(R.id.my_gridview);
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(data.get(position).title);

        Glide.with(mContext).load(
                data.get(position).goods_part_adv.img)
                .crossFade()
                .into(viewHolder.image);

        //viewHolder.image.setTag(data.goods_adv_part.get(position).goods_part_adv.cat_id);
        viewHolder.image.setTag(R.string.position,data.get(position).goods_part_adv.cat_id);
        viewHolder.image.setOnClickListener(mOnClickListener);

        adapter = new HomeGoodsAdvertisementAdapter(mContext,
                data.get(position).goods_part_adv_array);

        viewHolder.mGridView.setAdapter(adapter);

        return convertView;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //String catId = v.getTag().toString();
            String catId = v.getTag(R.string.position).toString();
            String url = ConstantURL.CATEGORYGOODS + catId;
            Bundle bundle = new Bundle();
            bundle.putString(GoodsDetailActivity.GOODSDETAILURL, url);
            bundle.putString(GoodsDetailActivity.GOODSDETAILID,catId);
            IntentUtil.startActivity(mContext, GoodsDetailActivity.class, bundle);
        }
    };

    public void setData(List<HomeAdversion.GoodsAdvPartEntity> homeAdversion) {
        if(homeAdversion == null) return;
        data = homeAdversion;
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }


    static class ViewHolder{
        MyGridView mGridView;
        TextView title;
        ImageView image;
    }
}
