package com.louie.luntonghui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.louie.luntonghui.R;

/**
 * Created by Administrator on 2015/7/15.
 */
public class ActivityIcon extends LinearLayout {
    private LinearLayout mView;
    private ImageView mIcon;
    private TextView mText;

    public ActivityIcon(Context context, AttributeSet attrs) {
        super(context, attrs);


        initAttr(context,attrs);
    }

    private void initAttr(Context context, AttributeSet attr) {

        mView = (LinearLayout)inflate(context, R.layout.activity_area,null);
        addView(mView);

        mIcon = (ImageView)mView.findViewById(R.id.icon);
        mText = (TextView)mView.findViewById(R.id.text);

        TypedArray ta = context.obtainStyledAttributes(attr, R.styleable.activity_area);

        int icon = ta.getResourceId(R.styleable.activity_area_activity_icon,-1);
        String text = ta.getString(R.styleable.activity_area_activity_text);

        mIcon.setImageResource(icon);
        mText.setText(text);

        ta.recycle();
    }

/*
    public void setTextAndColor(String text, int color){
        mText.setText(text);
        mText.setTextColor(color);
    }

    public void setIcon(int resId){
        mIcon.setImageResource(resId);
    }*/
}
