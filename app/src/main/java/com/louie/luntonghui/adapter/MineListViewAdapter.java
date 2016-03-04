package com.louie.luntonghui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Jack on 16/3/1.
 */
public class MineListViewAdapter extends BaseAdapter {
    private ArrayList mArraylist;
    private Context mContext;
    private int mType;

    public MineListViewAdapter(Context context,int type){
        this.mContext = context;
        this.mType = type;
        mArraylist = new ArrayList();
    }

    @Override
    public int getCount() {
        return mArraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mArraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
