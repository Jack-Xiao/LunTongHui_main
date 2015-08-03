package com.louie.luntonghui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.GoodsList;
import com.louie.luntonghui.util.Utility;
import com.louie.luntonghui.view.MyGridView;
import com.louie.luntonghui.view.MyListView;
import com.louie.luntonghui.view.MyRecyclerView;
import com.louie.luntonghui.view.RecyclerViewGridItemDecoration;
import com.louie.luntonghui.view.RecyclerViewLinearLayoutViewItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.louie.luntonghui.view.RecyclerViewLinearLayoutViewItemDecoration.HORIZONTAL_LIST;

/**
 * Created by Administrator on 2015/6/18.
 */
public class GoodsDetailAdapter extends BaseAdapter {
    private Activity mContext;
    private List<GoodsList.Goods_listEntity.Goods_list1Entity>data
            =new ArrayList<>();
    private LayoutInflater inflater;
    private GoodsDetailEachAdapter mEachAdapter;
    private GoodsDetailEachAdapter1 mAdapter;
    private GoodsDetailEachAdapter2 mAdapter2;

    public GoodsDetailAdapter(Activity context,List<GoodsList.Goods_listEntity.Goods_list1Entity> list) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        data.addAll(list);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_goods_detail, parent,false);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.goods_home_name);


            /*viewHolder.mRecyclerView = (MyRecyclerView) convertView.findViewById(R.id.goods_detail_list);

            //设置默认动画
            viewHolder.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            // 设置固定大小
            viewHolder.mRecyclerView.setHasFixedSize(true);

          *//*  viewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            viewHolder.mRecyclerView.addItemDecoration(new RecyclerViewGridItemDecoration(mContext));*//*

            viewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            viewHolder.mRecyclerView.addItemDecoration(new RecyclerViewLinearLayoutViewItemDecoration(
                    mContext, LinearLayoutManager.HORIZONTAL));*/

            //mList.setAdapter(mEachAdapter);

            //viewHolder.myListView  =  (MyListView) convertView.findViewById(R.id.goods_detail_list);
            //加粗
            //TextPaint paint = name.getPaint();
            //paint.setFakeBoldText(true);

            viewHolder.mGridView = (MyGridView) convertView.findViewById(R.id.goods_detail_list);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        TextPaint paint = viewHolder.mTextView.getPaint();
        paint.setFakeBoldText(true);
        viewHolder.mTextView.setText(data.get(position).name);


        //GoodsList.Goods_listEntity.Goods_list1Entity list1Entity = data.get(position);
        //mAdapter = new GoodsDetailEachAdapter1(mContext,list1Entity.goods_list2);
        //viewHolder.myListView.setAdapter(mAdapter);
        //Utility.setListViewHeightBasedOnChildren(viewHolder.myListView);
        /*mEachAdapter = new GoodsDetailEachAdapter(mContext,data.get(position).goods_list2);
        viewHolder.mRecyclerView.setAdapter(mEachAdapter);*/
        mAdapter2 = new GoodsDetailEachAdapter2(mContext,data.get(position).goods_list);
        viewHolder.mGridView.setAdapter(mAdapter2);
        return convertView;
    }

    static class ViewHolder{
        MyRecyclerView mRecyclerView;
        //MyListView myListView;
        MyGridView mGridView;
        TextView mTextView;

    }

    /*public void setListViewHeight(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {

            return;

        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0);

            totalHeight += listItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);

    }*/
}
