package com.louie.luntonghui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.HistorySearchTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/13.
 */
public class HistorySearchAdapter extends ArrayAdapter {
    private int mResourceId;
    private Context mContext;

    public HistorySearchAdapter(Context context, int resource) {
       // init(context, resource, 0, new ArrayList<T>());
        super(context,resource);
        this.mContext = context;
        this.mResourceId = resource;
    }

    public HistorySearchAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.mResourceId = resource;
        this.mContext = context;
    }

    public void insertData(String value){
        //add(value);
        insert(value,0);
        notifyDataSetChanged();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =LayoutInflater.from(mContext);
        View view = inflater.inflate(mResourceId, null);
        TextView tv = (TextView)view.findViewById(R.id.content);
        tv.setText(getItem(position).toString());
        return view;
    }

    public void clearData(){
        new Delete()
                .from(HistorySearchTable.class)
                .execute();
        clear();
    }
}
