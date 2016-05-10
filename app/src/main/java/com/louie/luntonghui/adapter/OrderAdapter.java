package com.louie.luntonghui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.Order;
import com.louie.luntonghui.model.result.OrderList;
import com.louie.luntonghui.ui.order.DetailOrderActivity;
import com.louie.luntonghui.ui.order.OrderReturnActivity;
import com.louie.luntonghui.util.IntentUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/7/17.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<OrderList.MysalelistEntity> data;
    private Context mContext;
    private SimpleDateFormat serverFormat;
    private SimpleDateFormat clientFormat;
    private Activity mActivity;
    private String type;
    public static final String CAN_RETURN = "1";
    public static final String CAN_NOT_RETURN = "0";

    public OrderAdapter(Activity activity) {
        this.mContext = activity;
        mActivity = activity;
        serverFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        clientFormat = new SimpleDateFormat("yyyy/MM/dd");
    }

    public void setData(List<OrderList.MysalelistEntity> lists){
        if(data == null){
            data = new ArrayList<>();
        }
        data.clear();
        data.addAll(lists);
        notifyDataSetChanged();

    }

    public void clear(){
        if(data == null) data = new ArrayList<>();
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_order_item, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String serverTime = data.get(position).add_time;
        String clientDate ;
        try {
            Date serverDate = serverFormat.parse(serverTime);
            clientDate = clientFormat.format(serverDate);

        } catch (ParseException e) {
            e.printStackTrace();
            clientDate = serverTime;
        }


        holder.orderSnValue.setText(data.get(position).order_sn);
        holder.orderDateValue.setText(clientDate);
        holder.orderValue.setText("￥" + data.get(position).order_amount);

        holder.lookOver.setTag(position);
        holder.lookOver.setOnClickListener(mListener);

        if(data.get(position).has_return_id.equals(Order.NO_RETURN_ID)){
            holder.returnOrder.setVisibility(View.GONE);
        }else{
            holder.returnOrder.setVisibility(View.VISIBLE);
        }
        holder.returnOrder.setTag(position);
        holder.returnOrder.setOnClickListener(orderReturnClick);
    }


    private View.OnClickListener orderReturnClick = v ->{
        int position =(Integer)v.getTag();
        //ToastUtil.showShortToast(mContext,position + "");
        Bundle bundle = new Bundle();
        String orderId = data.get(position).order_id;
        String handler = data.get(position).handler;
        String returnId = data.get(position).has_return_id;

        bundle.putString(Order.ORDERID,orderId);
        bundle.putString(Order.TYPE,type);
        bundle.putString(Order.HANDLER,handler);
        bundle.putString(Order.RETURN_ORDER_ID,returnId);
        IntentUtil.startActivity(mActivity, OrderReturnActivity.class, bundle);
        //IntentUtil.startActivity(mActivity, DetailOrderActivity.class, bundle);
    };

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position =(Integer)v.getTag();
            //ToastUtil.showShortToast(mContext,position + "");
            Bundle bundle = new Bundle();
            String orderId = data.get(position).order_id;
            String handler = data.get(position).handler;
            String returnId = data.get(position).has_return_id;
            String canReturn = data.get(position).can_return;

            bundle.putString(Order.ORDERID,orderId);
            bundle.putString(Order.TYPE,type);
            bundle.putString(Order.HANDLER,handler);
            bundle.putString(Order.CAN_RETURN_STATE,canReturn);

            IntentUtil.startActivity(mActivity, DetailOrderActivity.class, bundle);

            /*if(handler.equals(Order.RETURN_ORDER_HANDLER)){
                IntentUtil.startActivity(mActivity, OrderReturnActivity.class, bundle);
            }else if(handler.equals(Order.RETURN_HANDLER) && data.get(position).can_return !=null &&
                    data.get(position).can_return.equals(Order.CAN_RETURN)){
                IntentUtil.startActivity(mActivity, OrderReturnActivity.class, bundle);
            }else{
                IntentUtil.startActivity(mActivity, DetailOrderActivity.class, bundle);
            }*/
        }
    };


    @Override
    public int getItemCount() {
        return data == null? 0: data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.order_sn_value)
        TextView orderSnValue;
        @InjectView(R.id.order_date_value)
        TextView orderDateValue;
        @InjectView(R.id.order_value)
        TextView orderValue;
        @InjectView(R.id.look_over)
        Button lookOver;
        @InjectView(R.id.return_order)
        Button returnOrder;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
