package com.louie.luntonghui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.OrderConfirm;

import java.util.List;

/**
 * Created by Jack on 16/3/15.
 */
public class FixOrderArrayAdapter extends ArrayAdapter<OrderConfirm.FixGoods> {
    private int resourceId;

    public FixOrderArrayAdapter(Context context, int resource, List<OrderConfirm.FixGoods> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        OrderConfirm.FixGoods goods = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }
        TextView tvUsername = (TextView)convertView.findViewById(R.id.name);
        TextView tvCount = (TextView)convertView.findViewById(R.id.count);

        tvUsername.setText(goods.title);
        tvCount.setText(goods.amount);
        return convertView;
    }
}


