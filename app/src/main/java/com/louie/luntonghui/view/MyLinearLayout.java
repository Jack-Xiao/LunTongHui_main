package com.louie.luntonghui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Jack on 15/8/14.
 */
public class MyLinearLayout extends LinearLayout {
    private Context mContext;

    GestureDetector mGestureDector;

    public MyLinearLayout(Context context) {
        //super(context);
        this(context,null);

    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        //super(context, attrs);
        this(context,attrs,0);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("LinearLayout", "init..");

        this.mContext = context;
        mGestureDector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        Log.d("LinearLayout", "Intercept..");

        return true;

       /* if( mGestureDector.onTouchEvent(e)){
            return true;
        }*/
        //return false;
        //return true;
        //return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean callOnClick() {
        return super.callOnClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("LinearLayout", "onTouchEvent..");

        return super.onTouchEvent(event);
    }
}
