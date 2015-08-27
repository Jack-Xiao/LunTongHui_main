package com.louie.luntonghui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.GoodsList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/19.
 */
public class GoodsDetailEachAdapter1 extends BaseAdapter {
    private Context mContext;
    private List<GoodsList.Goods_listEntity.Goods_list1Entity.Goods_list2Entity> data
            = new ArrayList<>();

    public GoodsDetailEachAdapter1(Context context,List<GoodsList.Goods_listEntity.Goods_list1Entity
                                        .Goods_list2Entity> list) {
        this.mContext = context;
        data.addAll(list);
    }

/*    public void setData(List<GoodsList.Goods_listEntity.Goods_list1Entity.Goods_list2Entity> list){
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }*/

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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_goods_detail_each,parent,false);
        ViewHolder vh = null;
        if(vh == null){
            vh = new ViewHolder();
            vh.imageView = (ImageView)convertView.findViewById(R.id.goods_detail_item_each_image);
            vh.textView = (TextView)convertView.findViewById(R.id.goods_detail_item_each_text);

        }else{
            convertView.setTag(vh);
        }

        vh.textView.setText(data.get(position).name);

        return convertView;
    }

    static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
