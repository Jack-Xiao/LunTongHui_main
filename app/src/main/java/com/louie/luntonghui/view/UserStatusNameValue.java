package com.louie.luntonghui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.louie.luntonghui.R;

/**
 * Created by Administrator on 2015/6/4.
 */
public class UserStatusNameValue extends LinearLayout {
    private TextView mName;
    private TextView mValue;

    public UserStatusNameValue(Context context){
        this(context, null);
    }

    public UserStatusNameValue(Context context, AttributeSet attrs) {
        super(context, attrs);
        View contentView = inflate(context, R.layout.user_status_name_value,null);
        addView(contentView);
        mName = (TextView)contentView.findViewById(R.id.status_name);
        mValue = (TextView)contentView.findViewById(R.id.status_value);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.UserStatusNameValue);

        for(int i =0;i<typedArray.getIndexCount(); i++){
            switch (typedArray.getIndex(i)){
                case R.styleable.UserStatusNameValue_name_color:
                    mName.setTextColor(typedArray.getColor(i,R.color.gray));
                    break;
                case R.styleable.UserStatusNameValue_value_color:
                    mValue.setTextColor(typedArray.getColor(i,R.color.gray));
                    break;
                case R.styleable.UserStatusNameValue_name_text:
                    mName.setText(typedArray.getString(i));
                    break;
                case R.styleable.UserStatusNameValue_value_text:
                    mValue.setText(typedArray.getString(i));
            }
        }
        typedArray.recycle(); //必须语句
    }

    public void setValue(String text){
        mValue.setText(text);
    }
}
