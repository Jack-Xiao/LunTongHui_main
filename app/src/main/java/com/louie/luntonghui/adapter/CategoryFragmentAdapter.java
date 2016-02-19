package com.louie.luntonghui.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.GoodsList;
import com.louie.luntonghui.view.MyGridView;

import java.util.List;

/**
 * Created by Jack on 15/12/30.
 */
public class CategoryFragmentAdapter extends BaseAdapter{
    public static final String CATEGORY_TITLE = "0";
    public static final String CATEGORY_GOODS = "1";
    private FragmentActivity context;
    private List<GoodsList.Goods_listEntity.Goods_list1Entity> data;
    private LayoutInflater inflater;

    public CategoryFragmentAdapter(FragmentActivity activity) {
        inflater = LayoutInflater.from(activity);
        this.context = activity;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size() * 2;
    }

    @Override
    public Object getItem(int position) {
        return (position % 2 == 0? data.get(position).goods_list : data.get(position).name);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int index = position  / 2;
        if(position % 2 == 0){
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView  = inflater.inflate(R.layout.view_category_title,null);
                holder.title = (TextView)convertView.findViewById(R.id.goods_home_name);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.title.setText(data.get(index).name);
        }else{
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView  = inflater.inflate(R.layout.view_category_grid,null);
                holder.myGridView = (MyGridView)convertView.findViewById(R.id.goods_detail_list);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.myGridView.setAdapter(new GoodsDetailEachAdapter2(context,
                    data.get(index).goods_list));
        }

        return convertView;
    }

    public void setData(List<GoodsList.Goods_listEntity.Goods_list1Entity> goods_list1) {
        this.data = goods_list1;
    }

    class ViewHolder{
        TextView title;
        MyGridView myGridView;

    }
}
