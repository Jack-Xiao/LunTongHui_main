package com.louie.luntonghui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.ReturnProductDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 16/5/3.
 */
public class CancelProductAdapter extends BaseAdapter {
    private List<ReturnProductDetail.DataEntity.GoodsListEntity> mList;
    private Context mContext;
    private LayoutInflater inflater;

    public CancelProductAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<ReturnProductDetail.DataEntity.GoodsListEntity> list){
        if(list == null) return;
        if(mList == null) mList = new ArrayList<>();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ReturnProductDetail.DataEntity.GoodsListEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
             holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_cancel_goods,parent,false);
            holder.ivGoodsImg =(ImageView)convertView.findViewById(R.id.goods_img);
            holder.tvGoodsName = (TextView) convertView.findViewById(R.id.goods_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.goods_price);
            holder.tvHasBuy = (TextView)convertView.findViewById(R.id.has_buy_goods);
            holder.tvReturnCount = (TextView)convertView.findViewById(R.id.return_goods_count);
            holder.tvReturnReason = (TextView)convertView.findViewById(R.id.tv_return_reason);

            convertView.setTag(holder);
        }else{
            holder  =(ViewHolder)convertView.getTag();
        }

        Glide.with(mContext)
                .load(getItem(position).goods_thumb)
                .asBitmap()
                .into(holder.ivGoodsImg);

        holder.tvGoodsName.setText(getItem(position).goods_name);
        holder.tvPrice.setText(getItem(position).goods_price);
        holder.tvHasBuy.setText("已购买："+ getItem(position).goods_number);
        holder.tvReturnCount.setText("退货数量：" + getItem(position).return_number);
        holder.tvReturnReason.setText("退货原因：" + getItem(position).return_reason);

        return convertView;
    }

    class ViewHolder{
        ImageView ivGoodsImg;
        TextView tvGoodsName;
        TextView tvPrice;
        TextView tvHasBuy;

        TextView tvReturnCount;
        TextView tvReturnReason;
    }

}
