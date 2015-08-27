package com.louie.luntonghui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.louie.luntonghui.R;

/**
 * Created by Jack on 15/8/12.
 */
public class MultiSwipeRefreshLayout extends SwipeRefreshLayout {
    private Drawable mForegroundDrawable;
    private CanChildScrollUpCallback mCanChildScrollUpCallback;

    public MultiSwipeRefreshLayout(Context context) {
        this(context,null);
    }

    public MultiSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultiSwipeRefreshLayout);
        mForegroundDrawable = a.getDrawable(R.styleable.MultiSwipeRefreshLayout_foreground);
        if(mForegroundDrawable !=null){
            mForegroundDrawable.setCallback(this);
            setWillNotDraw(false);
        }
        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mForegroundDrawable !=null){
            mForegroundDrawable.setBounds(0,0,w,h);
        }
    }
    public void setCanChildScrollUpCallback(CanChildScrollUpCallback canChildScrollUpCallback){
        mCanChildScrollUpCallback = canChildScrollUpCallback;
    }
    public interface CanChildScrollUpCallback{
        boolean canSwipeRefreshChildScrollUp();
    }

    @Override
    public boolean canChildScrollUp() {
        if(mCanChildScrollUpCallback !=null){
            return mCanChildScrollUpCallback.canSwipeRefreshChildScrollUp();
        }
        return super.canChildScrollUp();
    }
}
