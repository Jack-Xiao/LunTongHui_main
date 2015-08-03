package com.louie.luntonghui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.MineServiceOrderTable;
import com.louie.luntonghui.model.db.Order;
import com.louie.luntonghui.ui.mine.MineService.MineCustomerOrderListActivity;
import com.louie.luntonghui.ui.order.DetailOrderActivity;
import com.louie.luntonghui.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.POST;

/**
 * Created by Jack on 15/7/30.
 */
public class MineCustomerOrderListAdapter extends BaseAdapter {
    private MineCustomerOrderListActivity mContext;
    private List<MineServiceOrderTable> data;
    private String [] orderStates;
    public MineCustomerOrderListAdapter(MineCustomerOrderListActivity context) {
        this.mContext = context;
        orderStates = mContext.getResources().getStringArray(R.array.order_state_list);
    }

    public void setData(List<MineServiceOrderTable> list){
        if( data == null){
            data = new ArrayList<>();
        }
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
     }

    public void addData(List<MineServiceOrderTable> list){
        if( data == null){
            data = new ArrayList<>();
        }
         data.addAll(list);
        notifyDataSetChanged();
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
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.mine_service_order_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tvSerialNumber = (TextView)convertView.findViewById(R.id.serial_number_value);
            viewHolder.tvAccount = (TextView)convertView.findViewById(R.id.account_value);
            viewHolder.tvMoney = (TextView)convertView.findViewById(R.id.money_value);
            viewHolder.tvServiceCost = (TextView) convertView.findViewById(R.id.service_cost_value);
            viewHolder.tvState = (TextView)convertView.findViewById(R.id.tv_order_state);
            viewHolder.btnChat = (Button)convertView.findViewById(R.id.btn_look_over);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tvSerialNumber.setText(data.get(position).orderSn);
        viewHolder.tvAccount.setText(data.get(position).userName +" ( " + data.get(position).mobilePhone + " )");
        viewHolder.tvMoney.setText(data.get(position).orderAmount);
        viewHolder.tvServiceCost.setText(data.get(position).money);
        viewHolder.btnChat.setTag(position);
        viewHolder.btnChat.setOnClickListener(mListener);
        int index = Integer.parseInt(data.get(position).handler)  -1;
        viewHolder.tvState.setText(orderStates[index]);

    return  convertView;
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position =(Integer)v.getTag();
            //ToastUtil.showShortToast(mContext,position + "");
            Bundle bundle = new Bundle();
            String orderId = data.get(position).orderId;
            bundle.putString(Order.ORDERID,orderId);
            bundle.putInt(Order.QUERY_TYPE, Order.SERVICE_TYPE);
            bundle.putString(Order.USER_ID,data.get(position).userId);
            IntentUtil.startActivity(mContext, DetailOrderActivity.class, bundle);

        }
    };

    class ViewHolder{
        TextView tvSerialNumber;
        TextView tvAccount;
        TextView tvMoney;
        TextView tvServiceCost;
        TextView tvState;
        Button btnChat;

    }
}
