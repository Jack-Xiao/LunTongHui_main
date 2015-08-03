package com.louie.luntonghui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.MineServicePeopleListTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 15/8/1.
 */
public class MineServicePeopleAdapter extends BaseAdapter {
    public List<MineServicePeopleListTable> data;
    private Context mContext;
    private LayoutInflater inflater;

    public MineServicePeopleAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
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

    public void setData(List<MineServicePeopleListTable> list) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mine_service_people_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.account = (TextView) convertView.findViewById(R.id.account_value);
            viewHolder.mobliePhone = (TextView) convertView.findViewById(R.id.mobile_value);
            viewHolder.remark = (TextView) convertView.findViewById(R.id.account_name_value);
            viewHolder.registerTime = (TextView) convertView.findViewById(R.id.reg_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.account.setText(data.get(position).userName);
        viewHolder.mobliePhone.setText(data.get(position).mobilePhone);
        viewHolder.remark.setText(data.get(position).remark);
        viewHolder.registerTime.setText(data.get(position).registerTime);
        return convertView;
    }

    class ViewHolder {
        TextView account;
        TextView remark;
        TextView mobliePhone;
        TextView registerTime;
    }
}
