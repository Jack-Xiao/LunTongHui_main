package com.louie.luntonghui.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.louie.luntonghui.R;

/**
 * Created by Administrator on 2015/6/23.
 */
public class MyAdjustPriceView extends LinearLayout{
    private View mView;
    private Button btnMinus;
    private Button btnPlus;
    private EditText mContent;
    private int result = 1;
    private TextChangeListener listener;
    private Context mContext;

    public MyAdjustPriceView(Context context) {
        super(context);
    }

    public MyAdjustPriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mView = (LinearLayout)inflate(context, R.layout.view_adjust_price, null);
        addView(mView);
        btnMinus = (Button)mView.findViewById(R.id.minus);
        btnPlus = (Button)mView.findViewById(R.id.plus);
        mContent = (EditText)mView.findViewById(R.id.content);
        mContent.clearFocus();
        notifyPriceChanged();
        mContent.setText("1");

        btnMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    result = Integer.parseInt(mContent.getText().toString());
                } catch (Exception e) {
                    result = 1;
                }


                if (result > 1) {
                    result--;
                    notifyPriceChanged();
                } else {
                    return;
                }
            }
        });

        btnPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    result = Integer.parseInt(mContent.getText().toString());
                }catch (Exception e){
                    result = 1;
                }
                result ++;
                notifyPriceChanged();
            }
        });

        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mContent.getText().toString().equals("")) {
                    mContent.setText("1");
                }
                mContent.setSelection(mContent.getText().length());
                listener.change(mContent.getText().toString());
            }
        });

        mContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setView(R.layout.view_adjust_goods_number)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
        });
    }

    public interface TextChangeListener{
        public void change(String text);
    }



    public MyAdjustPriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public int getValue(){
        return result;
    }

    private void notifyPriceChanged(){
       mContent.setText(result +"");
    }

}
