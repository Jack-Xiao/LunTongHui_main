package com.louie.luntonghui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.louie.luntonghui.R;

/**
 * Created by Administrator on 2015/6/23.
 */
public class MarketPriceView extends RelativeLayout {
    private RelativeLayout mView;
    private TextView mContent;

    public MarketPriceView(Context context) {
        super(context);
    }

    public MarketPriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = (RelativeLayout)inflate(context, R.layout.view_market_price,null);
        addView(mView);
        mContent = (TextView)mView.findViewById(R.id.content);
     }

    public MarketPriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setMarketPrice(String text){
        mContent.setText("￥"+text);
    }
}
