package com.louie.luntonghui.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bumptech.glide.Glide;
import com.louie.luntonghui.R;

/**
 * Created by Jack on 15/10/13.
 */
public class NetworkImageHolderViewProductDesc implements CBPageAdapter.Holder<String>{
    private ImageView imageView;
    private Context mContext;
    private int position;

    public OnSelectItemListener mListener;
    private Activity mActivity;

    public NetworkImageHolderViewProductDesc(Activity activity, OnSelectItemListener listener){
        this.mListener = listener;
        this.mActivity = activity;
    }

    public interface  OnSelectItemListener{
        public void selectItem(int index);
    }

    @Override
    public View createView(Context context) {
        mContext = context;
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        View view  = LayoutInflater.from(context).inflate(R.layout.view_glide,null);
        imageView  = (ImageView) view.findViewById(R.id.image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }

    @Override
    public void UpdateUI(Context context, final int position, String data) {

        Glide.with(context).load(data)
                .placeholder(R.drawable.default_image_in_no_source)
                .error(R.drawable.default_image_in_no_source)
                .into(imageView);

        imageView.setTag(R.string.position, position);
        imageView.setOnClickListener(onClickListener);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag(R.string.position).toString());
            mListener.selectItem(position);
        }
    };
}
