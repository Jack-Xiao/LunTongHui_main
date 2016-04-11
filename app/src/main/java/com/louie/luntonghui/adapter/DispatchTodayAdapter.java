package com.louie.luntonghui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.DispatchToday;
import com.louie.luntonghui.ui.mine.dispatch.DispatchHistoryActivity;
import com.louie.luntonghui.ui.mine.dispatch.DispatchTodayActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jack on 16/4/6.
 */
public class DispatchTodayAdapter extends RecyclerView.Adapter<DispatchTodayAdapter.ViewHolder> {
    private Context mContext;
    private List<DispatchToday.ListEntity> mList;
    private OnConfirmListener mListener;

    public interface OnConfirmListener{
        public void confirmOrder(String orderId,String orderSn,String consignee,String applyMoney);
        public void onClickItemListener(View v,int position);
    }

    public DispatchTodayAdapter(DispatchTodayActivity context) {
        this.mContext = context;
        mList = new ArrayList<>();
        mListener = context;
    }

    public DispatchTodayAdapter(DispatchHistoryActivity context) {
        this.mContext = context;
        mList = new ArrayList<>();
        mListener = context;
    }

    public void setData(List<DispatchToday.ListEntity> list) {
        if(mList == null) mList = new ArrayList<>();
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<DispatchToday.ListEntity> list){
        if(mList == null) mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_dispatch_today, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DispatchToday.ListEntity entity = mList.get(position);
        String addressList = "";
        String consigneeList = "";
        String mobileList = "";

        /*if (entity.address.size() == 1) {
            addressList = entity.address.get(0);
        } else {
            for (int i = 0; i < entity.address.size(); i++) {
                addressList += entity.address.get(i) + "\n";
            }
        }*/

        if(entity.address.size() >0) addressList = entity.address.get(0);
        /*if (entity.consignee.size() == 1) {
            consigneeList = entity.consignee.get(0) + "\n";
        } else {
            for (int i = 0; i < entity.consignee.size(); i++) {
                consigneeList += entity.consignee.get(i) + "\n";
            }
        }*/

        if(entity.consignee.size() >0) consigneeList = entity.consignee.get(0);

        /*if (entity.mobile.size() == 1) {
            mobileList = entity.mobile.get(0);
        } else {
            for (int i = 0; i < entity.mobile.size(); i++) {
                mobileList += entity.mobile.get(i) + "\n";
            }
        }*/
        if(entity.mobile.size() >0) mobileList = entity.mobile.get(0);

        holder.tvSeatValue.setText(entity.deliver_sn);
        holder.tvSalaryValue.setText(entity.total_amount);
        holder.consigneeValue.setText(consigneeList);
        holder.tvAddress.setText(addressList);
        holder.mobileValue.setText(mobileList);

        if (entity.r_status.equals(DispatchTodayActivity.NOT_GET)) {
            holder.state.setVisibility(View.VISIBLE);
            holder.state.setText("未配送收款");
            holder.state.setTextColor(mContext.getResources().getColor(R.color.dispatch_font_color));
            holder.btnDispatch.setVisibility(View.VISIBLE);
            holder.btnDispatch.setOnClickListener(clickListener);
            holder.btnDispatch.setTag(position);
        } else {
            holder.state.setVisibility(View.VISIBLE);
            holder.state.setText("已配送");
            holder.state.setTextColor(mContext.getResources().getColor(R.color.order_font_normal));
            holder.btnDispatch.setVisibility(View.GONE);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickItemListener(v,position);
            }
        });

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag() + "");

           /* DispatchToday.ListEntity entity = mList.get(position);
            DispatchDetailActivity.toStart(mContext,entity);*/

            String orderId= mList.get(position).order_id;
            String orderSn = mList.get(position).deliver_sn;
            String applyMoney = mList.get(position).total_amount;
            String consignee = mList.get(position).consignee.get(0);

            mListener.confirmOrder(orderId,orderSn,consignee,applyMoney);

        }
    };

    public List<DispatchToday.ListEntity> getData(){
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList == null? 0 : mList.size();
    }

    public void updateItems(ArrayList<DispatchToday.ListEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_seat_name)
        TextView tvSeatName;
        @InjectView(R.id.tv_seat_value)
        TextView tvSeatValue;
        @InjectView(R.id.tv_salary_value)
        TextView tvSalaryValue;
        @InjectView(R.id.tv_salary)
        TextView tvSalary;
        @InjectView(R.id.consignee_name)
        TextView consigneeName;
        @InjectView(R.id.consignee_value)
        TextView consigneeValue;
        @InjectView(R.id.state)
        TextView state;
        @InjectView(R.id.btn_dispatch)
        Button btnDispatch;

        @InjectView(R.id.address)
        TextView tvAddress;
        @InjectView(R.id.mobile_value)
        TextView mobileValue;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
