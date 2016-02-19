package com.louie.luntonghui.view;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Jack on 16/1/9.
 */
public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //Apply options to the builder here.
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //DecodeFormat.PREFER_RGB_565
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        //register ModelLoader here.
    }
}
