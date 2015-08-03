package com.louie.luntonghui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.louie.luntonghui.R;

/**
 * Created by Louie on 2015/5/28.
 */
public class NavigationItem extends LinearLayout{
    private LinearLayout mView;
    private ImageView mIcon;
    private TextView mText;


    public NavigationItem(Context context) {
        this(context, null);
    }

    public NavigationItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = (LinearLayout)inflate(context, R.layout.navigation_item,null);
        addView(mView);
        mIcon = (ImageView)mView.findViewById(R.id.icon);
        mText = (TextView)mView.findViewById(R.id.text);
    }

    public void setTextAndColor(String text, int color){
        mText.setText(text);
        mText.setTextColor(color);
    }

    public void setIcon(int resId){
        mIcon.setImageResource(resId);
    }
    public ImageView getIcon(){
        return mIcon;
    }
}
