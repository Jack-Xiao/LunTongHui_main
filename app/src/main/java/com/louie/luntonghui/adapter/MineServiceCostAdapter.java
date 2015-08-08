package com.louie.luntonghui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.MineServiceCostTable;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MineServiceCostAdapter extends BaseAdapter {
    private Context mContext;
    private List<MineServiceCostTable> data;
    private LayoutInflater mLayoutInflater;

    public MineServiceCostAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<MineServiceCostTable> list) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.clear();
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
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_mine_service_cost_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mineServiceCost.setText(data.get(position).curProfit);
        viewHolder.mineServiceDate.setText(data.get(position).curDate);

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.mine_service_date)
        TextView mineServiceDate;
        @InjectView(R.id.mine_service_cost)
        TextView mineServiceCost;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
