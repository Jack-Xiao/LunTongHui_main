package com.louie.luntonghui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.GoodsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/18.
 */
public class GoodsDetailEachAdapter extends RecyclerView.Adapter<GoodsDetailEachAdapter.ViewHolder>{
    private Context mContext;
    private LayoutInflater inflater;
     private List<GoodsList.Goods_listEntity.Goods_list1Entity.Goods_list2Entity> data
            = new ArrayList<>();

    public GoodsDetailEachAdapter(Context context,List<GoodsList.Goods_listEntity
            .Goods_list1Entity.Goods_list2Entity> list) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.data.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = inflater.inflate(R.layout.fragment_goods_detail_each, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.imageView = data.d
        try{
            holder.textView.setText(data.get(position).name);
            final ImageView imageView = holder.imageView;

            /*holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png")
                            //.placeholder(R.drawable.user_placeholder) 错误或空白占位
                            .centerCrop()
                            .into(imageView);
                }
            });*/

        }catch (Exception e){
            System.out.print(""+ e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        String url;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.goods_detail_item_each_image);
            textView = (TextView) itemView.findViewById(R.id.goods_detail_item_each_text);
        }
    }
}



