package com.louie.luntonghui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.DispatchToday;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 16/4/8.
 */
public class DispatchTotalAdapter extends BaseAdapter {
    private Context mContext;
    private List<DispatchToday.ListEntity> mList;

    public DispatchTotalAdapter(Context context, List<DispatchToday.ListEntity> list){
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_dispatch_total,parent,false);
            viewHolder.orderId =(TextView)convertView.findViewById(R.id.order_id);
            viewHolder.dispatchState = (ImageView)convertView.findViewById(R.id.dispatch_state);
            viewHolder.totalPrice = (TextView)convertView.findViewById(R.id.total_price);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DispatchToday.ListEntity entity = mList.get(position);
        viewHolder.orderId.setText((position+1) + "、" + entity.deliver_sn);
        viewHolder.dispatchState.setImageResource(
                entity.r_status.equals("0")? R.drawable.dispatch_none : R.drawable.dispatch_done);

        viewHolder.totalPrice.setText(entity.total_amount);
        return convertView;
    }

    class ViewHolder{
        TextView orderId;
        ImageView dispatchState;
        TextView totalPrice;
    }
}
