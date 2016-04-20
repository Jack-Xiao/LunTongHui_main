package com.louie.luntonghui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.DispatchDetail;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.view.MyListView;

import java.text.DecimalFormat;

/**
 * Created by Jack on 16/4/8.
 */
public class DispatchDetailAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private DispatchDetail mDetail;
    public static final int LAST_CHILD = 1;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_LAST = 1;
    private DecimalFormat format;


    public DispatchDetailAdapter(Context context){
        mContext = context;
    }

    public void setData(DispatchDetail detail){
        mDetail = detail;
        format = new DecimalFormat("#.##");
        notifyDataSetChanged();
    }


    @Override
    public int getGroupCount() {
        return (mDetail == null) ? 0 : mDetail.data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDetail.data.get(groupPosition).goods_list.size() + 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDetail.data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDetail.data.get(groupPosition);
    }

    private int total = 1;
    @Override
    public int getChildType(int groupPosition,int childPosition){
        total =+1;
        System.out.println(total + "");
        if(childPosition == mDetail.data.get(groupPosition).goods_list.size()){
            return TYPE_LAST;
        }
        return TYPE_NORMAL;
    }
    @Override
    public int getChildTypeCount() {
        return 2;

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //设置父item组件
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentView view = null;
        if(convertView == null){
            view = new ParentView();
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.adapter_dispatch_detail_parent,null);
            view.tvDate = (TextView)convertView.findViewById(R.id.order_sn);
            view.tvOrderId = (TextView) convertView.findViewById(R.id.order_time);
            view.orderPrice = (TextView) convertView.findViewById(R.id.order_price);
            convertView.setTag(view);
        }else{
            view =(ParentView)convertView.getTag();
        }

        view.tvOrderId.setText("订单号 : " + mDetail.data.get(groupPosition).order.order_sn);
        view.tvDate.setText(Config.getDispatchOrderTime(mDetail.data.get(groupPosition).order.formated_add_time));
        view.orderPrice.setText("金额 : " + mDetail.data.get(groupPosition).order.formated_total_fee);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildView view2= null;
        ChildView1 view1 = null;
        int type = getChildType(groupPosition,childPosition);
        if(convertView == null){
            switch (type){
                case TYPE_NORMAL:
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.adapter_dispatch_detail_child_1,null);
                    view1 = new ChildView1();
                    view1.goodsName = (TextView)convertView.findViewById(R.id.goods_name);
                    view1.goodsCount = (TextView)convertView.findViewById(R.id.goods_count);
                    view1.goodsPrice = (TextView)convertView.findViewById(R.id.goods_price);
                    convertView.setTag(view1);
                    break;
                case TYPE_LAST:
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.adapter_dispatch_detail_child,null);
                    view2 = new ChildView();
                    view2.salaryView = (TextView)convertView.findViewById(R.id.salary_value);
                    view2.carryValue = (TextView)convertView.findViewById(R.id.carry_value);
                    view2.luntongbiValue = (TextView)convertView.findViewById(R.id.luntongbi_value);
                    view2.getatbleValue = (TextView)convertView.findViewById(R.id.getatable_value);
                    view2.primeValue = (TextView)convertView.findViewById(R.id.prime_value);
                    view2.practice = (TextView)convertView.findViewById(R.id.practice);
                    view2.orderTime = (TextView)convertView.findViewById(R.id.order_time);
                    convertView.setTag(view2);

                    break;
                default:
                    break;
            }
        }else{
            switch (type){
                case TYPE_NORMAL:
                    view1 = (ChildView1)convertView.getTag();
                    break;
                case TYPE_LAST:
                    view2 = (ChildView)convertView.getTag();
                    break;
            }
        }

        switch (type){
            case TYPE_NORMAL:
                view1.goodsName.setText(mDetail.data.get(groupPosition).
                        goods_list.get(childPosition).goods_name);

                view1.goodsCount.setText("数量 : " + mDetail.data.get(groupPosition).
                        goods_list.get(childPosition).goods_number);

                view1.goodsPrice.setText("金额 : "+mDetail.data.get(groupPosition)
                        .goods_list.get(childPosition).goods_price);

                break;

            case TYPE_LAST:

                view2.salaryView.setText("￥"+mDetail.data.get(groupPosition).order.order_amount);
                view2.carryValue.setText("￥"+mDetail.data.get(groupPosition).order.shipping_fee);

                view2.luntongbiValue.setText("-￥"+mDetail.data.get(groupPosition).order.integral_money);
                view2.getatbleValue.setText("￥"+mDetail.data.get(groupPosition).order.will_get_integral);

                view2.primeValue.setText("-￥"+ mDetail.data.get(groupPosition).order.discounts);

                view2.practice.setText("实付金额："+mDetail.data.get(groupPosition).order.formated_order_amount);

                view2.orderTime.setText("下单时间: " + mDetail.data.get(groupPosition).order.formated_add_time);
                break;
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class ParentView{
        TextView tvOrderId;
        TextView tvDate;
        TextView orderPrice;
    }

    class ChildView{
        MyListView mListView;
        TextView salaryView;

        TextView carryValue;
        TextView luntongbiValue;
        TextView getatbleValue;
        TextView primeValue;
        TextView practice;
        TextView orderTime;
    }

    class ChildView1{
        TextView goodsName;
        TextView goodsPrice;
        TextView goodsCount;
    }
}
