package com.louie.luntonghui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.MineWorkQueryDay;
import com.louie.luntonghui.model.db.MineWorkQueryMonth;
import com.louie.luntonghui.model.db.MineWorkQueryYear;
import com.louie.luntonghui.model.db.PrinterData;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Jack on 16/2/27.
 */
public class MineRecyclerViewAdapter extends RecyclerView.Adapter<MineRecyclerViewAdapter.ViewHolder> {
    private ArrayList mArrayList;
    private Context context;

    //public static final String
    public static final int OUTSTANDING_STATISTICS_MONTH = 0;
    public static final int OUTSTANDING_STATISTICS = 1;
    public static final int ORDER_QUERY = 2;
    public static final int MINE_PRINTER = 3;
    private int mType;

    public MineRecyclerViewAdapter(Context context, int type){
        this.context = context;
        mArrayList = new ArrayList();
        mType = type;

    }

    public void setType(int type){
        this.mType = type;
    }

    @Override
    public MineRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(mType == MINE_PRINTER){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mine_work_printer_item,null);
        }else if(mType == OUTSTANDING_STATISTICS_MONTH){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mine_outstanding_statistics_item1,null);

        }else if(mType == OUTSTANDING_STATISTICS){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mine_outstanding_statistics_item1,null);

        }else if(mType == ORDER_QUERY){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mine_outstanding_statistics_item1,null);
        }
        return new MineRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MineRecyclerViewAdapter.ViewHolder holder, int position) {
        int type = mType;
        switch (type){
            case OUTSTANDING_STATISTICS:
                this.loadingOutstandingYear(holder,position);
                break;
            case OUTSTANDING_STATISTICS_MONTH:
                this.loadingOutstandingMonth(holder,position);
                break;
            case ORDER_QUERY:
                this.loadingOrderQueryDay(holder,position);
                break;

            case MINE_PRINTER:
                this.loadingMinePrinterData(holder,position);
                break;
        }
    }

    private void loadingMinePrinterData(ViewHolder holder, int position) {

        PrinterData entity = (PrinterData)mArrayList.get(position);
        LinearLayout linearParent = holder.findViewById(R.id.item_parent);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearParent.setLayoutParams(params);

        TextView v1 = holder.findViewById(R.id.item1);
        TextView v2 = holder.findViewById(R.id.item2);
        TextView v3 = holder.findViewById(R.id.item3);
        TextView v4 = holder.findViewById(R.id.item4);
        TextView v5 = holder.findViewById(R.id.item5);

        v1.setText(entity.username);
        v2.setText(entity.totalOrder);
        v3.setText(entity.orderA);
        v4.setText(entity.orderB);
        v5.setText(entity.money);

        /*if(position == 0){
            v1.setBackgroundResource(R.color.background_main_grey);
            v2.setBackgroundResource(R.color.background_main_grey);
            v3.setBackgroundResource(R.color.background_main_grey);
            v4.setBackgroundResource(R.color.background_main_grey);
            v5.setBackgroundResource(R.color.background_main_grey);
        }else{
            v2.setBackgroundResource(R.color.white);
            v3.setBackgroundResource(R.color.white);
            v4.setBackgroundResource(R.color.white);
            v5.setBackgroundResource(R.color.white);
        }*/
        /*v1.setBackgroundResource(R.color.white);
        v2.setBackgroundResource(R.color.white);
        v3.setBackgroundResource(R.color.white);
        v4.setBackgroundResource(R.color.white);
        v5.setBackgroundResource(R.color.white);*/
    }

    private void loadingOutstandingMonth(ViewHolder holder, int position) {
        //if(position == mArrayList.size()) return;   //处理 添加一行时引起的 IndexOutOfBoundsException.
        MineWorkQueryMonth data = (MineWorkQueryMonth) mArrayList.get(position);

        LinearLayout linearParent = holder.findViewById(R.id.item_parent);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearParent.setLayoutParams(params);

        TextView v1 = holder.findViewById(R.id.item1);
        TextView v2 = holder.findViewById(R.id.item2);
        TextView v3 = holder.findViewById(R.id.item3);
        TextView v4 = holder.findViewById(R.id.item4);
        /*if(position == 0){
            v1.setText("合计");
            v2.setText(data.total_order_amount + "");
            v3.setText(data.total_no_amount + "");
            return;
        }*/

        v4.setVisibility(View.VISIBLE);
        v1.setText(data.date +"");
        v2.setText(data.order_amount + "");
        v3.setText(data.no_amount_a + "");
        v4.setText(data.no_amount_b + "");
        if(position == 0){
            v1.setBackgroundResource(R.color.background_main_grey);
            v2.setBackgroundResource(R.color.background_main_grey);
            v3.setBackgroundResource(R.color.background_main_grey);
            v4.setBackgroundResource(R.color.background_main_grey);
        }else{
            v2.setBackgroundResource(R.color.white);
            v3.setBackgroundResource(R.color.white);
            v4.setBackgroundResource(R.color.white);
        }
    }

    private void loadingOrderQueryDay(ViewHolder holder, int position) {
        MineWorkQueryDay data = (MineWorkQueryDay)mArrayList.get(position);

        LinearLayout linearParent = holder.findViewById(R.id.item_parent);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearParent.setLayoutParams(params);

        TextView v1 = holder.findViewById(R.id.item1);
        TextView v2 = holder.findViewById(R.id.item2);
        TextView v3 = holder.findViewById(R.id.item3);
        TextView v4 = holder.findViewById(R.id.item4);

        v4.setVisibility(View.GONE);

        v1.setText(data.user_name);
        v2.setText(data.order_sn);
        v3.setText(data.goods_amount);
        if(position == 0){
            v1.setBackgroundResource(R.color.background_main_grey);
            v2.setBackgroundResource(R.color.background_main_grey);
            v3.setBackgroundResource(R.color.background_main_grey);
        }else{
            v2.setBackgroundResource(R.color.white);
            v3.setBackgroundResource(R.color.white);
        }
    }

    private void loadingOutstandingYear(ViewHolder holder, int position) {
        MineWorkQueryYear data = (MineWorkQueryYear)mArrayList.get(position);
        LinearLayout linearParent = holder.findViewById(R.id.item_parent);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearParent.setLayoutParams(params);

        TextView v1 = holder.findViewById(R.id.item1);
        TextView v2 = holder.findViewById(R.id.item2);
        TextView v3 = holder.findViewById(R.id.item3);
        TextView v4 = holder.findViewById(R.id.item4);

        v4.setVisibility(View.VISIBLE);
        v1.setText(data.date);
        v2.setText(data.order_amount + "");
        v3.setText(data.no_amount_a + "");
        v4.setText(data.no_amount_b + "");

        if(position == 0){
            v1.setBackgroundResource(R.color.background_main_grey);
            v2.setBackgroundResource(R.color.background_main_grey);
            v3.setBackgroundResource(R.color.background_main_grey);
            v4.setBackgroundResource(R.color.background_main_grey);
        }else{
            v2.setBackgroundResource(R.color.white);
            v3.setBackgroundResource(R.color.white);
            v4.setBackgroundResource(R.color.white);
        }
    }

    @Override
    public int getItemCount() {
        return this.mArrayList.size();
    }

    public <T> T getItem(int position){
        return (T)this.mArrayList.get(position);
    }

    public ArrayList getList() {
        return mArrayList;
    }

    public void clear() {
        mArrayList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private View contentView;
        private SparseArray<View> views;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            this.contentView = itemView;
            views = new SparseArray<>();
        }

        public <T extends View> T findViewById(int viewId){
           View view = views.get(viewId);
            if(view == null){
                view = contentView.findViewById(viewId);
                views.put(viewId,view);
            }
            //View view = contentView.findViewById(viewId);
            return (T)view;
        }
    }

    public void setList(ArrayList list){
        if(mArrayList == null) return;
        mArrayList.clear();
        mArrayList.addAll(list);
        notifyDataSetChanged();
    }
}
