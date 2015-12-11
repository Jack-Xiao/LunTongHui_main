package com.louie.luntonghui.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.louie.luntonghui.R;
import com.louie.luntonghui.fragment.HomeFragment;
import com.louie.luntonghui.ui.web.AdvertisementWebActivity;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by Jack on 15/10/13.
 */
public class NetworkImageHolderView implements CBPageAdapter.Holder<String>{
    private ImageView imageView;
    private Context mContext;
    private int position;

    public OnSelectItemListener mListener;
    private Activity mActivity;

    public NetworkImageHolderView(Activity activity,OnSelectItemListener listener){
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
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }
    private OnClickListener onClickListener =new  OnClickListener(){

        @Override
        public void onClick(View v) {
            int position =(Integer) v.getTag();
            String url = HomeFragment.webUrl.get(position);
            if(TextUtils.isEmpty(url)){
                ToastUtil.showShortToast(mContext, "功能正在赶制中...");
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString(AdvertisementWebActivity.URL, url);
            IntentUtil.startActivity(mActivity, AdvertisementWebActivity.class, bundle);
        }
    };


    @Override
    public void UpdateUI(Context context, final int position, String data) {
        //imageView.setImageResource(R.drawable.ic_default_adimage);
       //ImageLoader.getInstance().displayImage(data, imageView);
        Picasso.with(mContext).load(data)
                .placeholder(R.drawable.default_image_in_no_source)
                .error(R.drawable.default_image_in_no_source)
                .into(imageView);

        //mListener.selectItem(position);
        //imageView.setTag(position);

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = HomeFragment.webUrl.get(position);
                if(TextUtils.isEmpty(url)){
                    ToastUtil.showShortToast(mContext, "功能正在赶制中...");
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString(AdvertisementWebActivity.URL, url);
                IntentUtil.startActivity(mActivity, AdvertisementWebActivity.class, bundle);
            }
        });
    }
}