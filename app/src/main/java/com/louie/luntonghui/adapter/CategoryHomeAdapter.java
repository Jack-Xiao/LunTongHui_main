package com.louie.luntonghui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.louie.luntonghui.R;

import java.util.List;

/**
 * Created by Administrator on 2015/7/5.
 */
public class CategoryHomeAdapter extends ArrayAdapter {
    private int mResourceId;
    private Context mContext;
    private int mPosition;

    public CategoryHomeAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.mResourceId = resource;
        this.mContext = context;
    }

    public void setBackground(int position){
        mPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =LayoutInflater.from(mContext);
        View view = inflater.inflate(mResourceId, null);
        TextView tv = (TextView)view.findViewById(R.id.content);
        tv.setText(getItem(position).toString());
        if(position == mPosition){
            view.setBackgroundResource(R.color.white);
            tv.setTextColor(mContext.getResources().getColor(R.color.order_font_choose));
         }else{
            view.setBackgroundResource(R.color.background_main_grey);
            tv.setTextColor(mContext.getResources().getColor(R.color.useful_grey));
        }
        return view;
    }
}
