package com.louie.luntonghui.view.zoom;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.louie.luntonghui.R;


/**
 * Created by Jack on 15/10/13.
 */
public class PullToZoomScrollViewEx extends PullToZoomBase<ScrollView> {
    private static final String TAG = PullToZoomScrollViewEx.class.getSimpleName();
    private boolean isCustomHeaderHeight = false;
    private FrameLayout mHeaderContainer;
    private LinearLayout mRootContainer;
    private View mContentView;
    private int mHeaderHeight;
    private int mHeaderWidth;
    private ScalingRunnable mScalingRunnable;
    private RelativeLayout mSearchContent;
    private LinearLayout mSearchView;
    private int mSearchContentWidth;
    private int mSearchContentHeight;
    private RelativeLayout mToolbarView;
    private int finalColor = 10747904;
    private int initColor = 65536;
    private RelativeLayout mTopSearchView;
    private int mSearchViewWidth;
    private int mSearchViewHeight;

    private static final Interpolator sInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float paramAnonymousFloat) {
            float f = paramAnonymousFloat  - 1.0F;
            return 1.0F + f * (f * (f * (f * f))) ;
        }
    };

    public PullToZoomScrollViewEx(Context context){
        this(context, null);
    }

    public PullToZoomScrollViewEx(Context context, AttributeSet attrs){
        super(context, attrs);
        mScalingRunnable = new ScalingRunnable();
        ((InternalScrollView)mRootView).setOnScrollViewChangedListener(new OnScrollViewChangedListener() {
            @Override
            public void onInternalScrollChanged(int left, int top, int oldLeft, int oldTop) {
                if (isPullToZoomEnabled() && isParallax()) {
                    Log.d(TAG, "onScrollChanged --> getScrollY() = " + mRootView.getScrollY());

                    float f = mHeaderHeight - mHeaderContainer.getBottom() + mRootView.getScrollY();


                    //mHeaderContainer.getH
                    Log.d(TAG, "onScrollChanged --> f = " + f);

                    Log.d(TAG, "onScroll - B:" + mHeaderContainer.getBottom());
                    Log.d(TAG, "onScroll - C:" + mHeaderHeight);
                    Log.d(TAG, "onScroll - H:" + mHeaderContainer.getHeight());

                    float c = mHeaderHeight - mRootView.getScrollY();

                    ViewGroup.LayoutParams localLayoutParams = mHeaderContainer.getLayoutParams();

                    int curSourceWidth = (int) (mSearchContentWidth * 0.28);

                    int half = curSourceWidth / 2;
                    if (c <= mSearchContentHeight && c > 0) {
                        Log.d(TAG, "onScroll - VVVV-:" + c);
                        //int temp = (int) ((curSourceWidth / 2) * ((112 - c) / 112));
                        //mSearchContent.setPadding(temp, 0, temp, 0);

                    } else if (c > mSearchContentHeight && c < (mSearchContentHeight *2 + 10)) {
                        int temp = (int) ((curSourceWidth / 2) * ((mSearchContentHeight *2 - c) / mSearchContentHeight));
                        mSearchView.setPadding(temp, 0, temp, 0);
                        int colorValue = initColor;
                        if(temp > 0 && temp < half/10){
                            colorValue = initColor;
                        }else if(temp <= half/9 && temp > half/10){
                            colorValue = getResources().getColor(R.color.toolbar_1);
                        }else if(temp <= half/8 && temp > half/9){
                            colorValue = getResources().getColor(R.color.toolbar_2);
                        }else if(temp <= half/7 && temp > half/8){
                            colorValue = getResources().getColor(R.color.toolbar_3);
                        }else if(temp <= half/6 && temp > half/7){
                            colorValue = getResources().getColor(R.color.toolbar_4);
                        }else if(temp <= half/5 && temp > half/6){
                            colorValue = getResources().getColor(R.color.toolbar_5);
                        }else if(temp <= half/4 && temp > half/5){
                            colorValue = getResources().getColor(R.color.toolbar_6);
                        }else if(temp <= half/3 && temp > half/4){
                            colorValue = getResources().getColor(R.color.toolbar_7);
                        }else if(temp <= half/2 && temp > half/3){
                            colorValue = getResources().getColor(R.color.toolbar_10);
                        }else if(temp <=half && temp >half/2){
                            colorValue = getResources().getColor(R.color.toolbar_10);
                        }
                        mTopSearchView.setBackgroundColor(colorValue);

                        //int tempColorValue =(int) ((finalColor - initColor) / ((mSearchContentHeight *2 - c)));
                        //mTopSearchView.setBackgroundColor(tempColorValue);

                    } else {
                       // mSearchContent.setPadding(0, 0, 0, 0);
                        //mSearchView.setPadding(0,0,0,0);
                        //mTopSearchView.setBackgroundColor(initColor);
                    }

                    if ((f > 0.0F) && (f < mHeaderHeight)) {
                        int i = (int) (0.65D * f);
                    } else if (mHeaderContainer.getScrollY() != 0) {
                        mHeaderContainer.scrollTo(0, 0);
                    }
                    //mToolbarView.setLayoutParams(localLayoutParams);
                    //mHeaderContainer.setLayoutParams(localLayoutParams);


                    if(mRootView.getScrollY() <= (mHeaderHeight - mSearchContentHeight * 2)){
                        Log.d(TAG, "onScroll - VY1:" + mRootView.getScrollY());
                        Log.d(TAG, "onScroll - TH1:" + mToolbarView.getHeight() + "");
                        ViewGroup.LayoutParams fParams = mToolbarView.getLayoutParams();
                        int height = (int) Math.abs(c);
                        fParams.height = height;
                        mToolbarView.setLayoutParams(fParams);

                    }else{
                        Log.d(TAG, "onScroll - VY2:" + mRootView.getScrollY());
                        Log.d(TAG, "onScroll - TH2:" + mToolbarView.getHeight() + "");
                        //Log.d(TAG, "onScroll - VH:" + );

                        ViewGroup.LayoutParams fParams = mToolbarView.getLayoutParams();
                        int height = mToolbarView.getHeight() - mRootView.getScrollY();
                        fParams.height = height;
                        mToolbarView.setLayoutParams(fParams);
                    }
                }
            }
        });
    }

    @Override
    protected void pullHeaderToZoom(int newScrollValue) {
        Log.d(TAG, "pullHeaderToZoom --> newScrollValue = " + newScrollValue);
        Log.d(TAG, "pullHeaderToZoom --> mHeaderHeight = " + mHeaderHeight);
        if (mScalingRunnable != null && !mScalingRunnable.isFinished()) {
            mScalingRunnable.abortAnimation();
        }

        ViewGroup.LayoutParams localLayoutParams = mHeaderContainer.getLayoutParams();
        localLayoutParams.height = Math.abs(newScrollValue) + mHeaderHeight;
        mHeaderContainer.setLayoutParams(localLayoutParams);

        /*ViewGroup.LayoutParams toolbarParams = mHeaderContainer.getLayoutParams();
        toolbarParams.height = Math.abs(newScrollValue) + mHeaderHeight;
        mToolbarView.setLayoutParams(localLayoutParams);*/

        if (isCustomHeaderHeight) {
            ViewGroup.LayoutParams zoomLayoutParams = mZoomView.getLayoutParams();
            zoomLayoutParams.height = Math.abs(newScrollValue) + mHeaderHeight;
            mZoomView.setLayoutParams(zoomLayoutParams);
            mToolbarView.setLayoutParams(zoomLayoutParams);
        }
    }


    @Override
    public void setHeaderView(View headerView) {
        if (headerView != null) {
            mHeaderView = headerView;
            updateHeaderView();
        }
    }

    @Override
    public void setZoomView(View zoomView) {
        if (zoomView != null) {
            mZoomView = zoomView;
            updateHeaderView();
        }
    }

    private void updateHeaderView() {
        if (mHeaderContainer != null) {
            mHeaderContainer.removeAllViews();

            if (mZoomView != null) {
                mHeaderContainer.addView(mZoomView);
            }

            if (mHeaderView != null) {
                mHeaderContainer.addView(mHeaderView);
            }
        }
    }

    public void setScrollContentView(View contentView) {
        if (contentView != null) {
            if (mContentView != null) {
                mRootContainer.removeView(mContentView);
            }
            mContentView = contentView;
            mRootContainer.addView(mContentView);
        }
    }

    @Override
    protected ScrollView createRootView(Context context, AttributeSet attrs) {
        ScrollView scrollView = new InternalScrollView(context, attrs);
        scrollView.setId(R.id.scrollview);
        return scrollView;
    }

    @Override
    protected void smoothScrollToTop() {
        Log.d(TAG, "smoothScrollToTop --> ");
        mScalingRunnable.startAnimation(200L);
    }

    @Override
    protected boolean isReadyForPullStart() {
        return mRootView.getScrollY() == 0;
    }

    @Override
    public void handleStyledAttributes(TypedArray a) {
        mRootContainer = new LinearLayout(getContext());
        mRootContainer.setOrientation(LinearLayout.VERTICAL);
        mHeaderContainer = new FrameLayout(getContext());

        if (mZoomView != null) {
            mHeaderContainer.addView(mZoomView);
        }

        if (mHeaderView != null) {
            mHeaderContainer.addView(mHeaderView);
        }

        int contentViewResId = a.getResourceId(R.styleable.PullToZoomView_contentView, 0);

        if (contentViewResId > 0) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
            mContentView = mLayoutInflater.inflate(contentViewResId, null, false);
        }

        mRootContainer.addView(mHeaderContainer);
        if (mContentView != null) {
            mRootContainer.addView(mContentView);
        }

        mRootContainer.setClipChildren(false);
        mHeaderContainer.setClipChildren(false);

        mRootView.addView(mRootContainer);
    }
    /**
     * 设置HeaderView高度
     *
     * @param width
     * @param height
     */
    public void setHeaderViewSize(int width, int height) {
        if (mHeaderContainer != null) {
            Object localObject = mHeaderContainer.getLayoutParams();
            if (localObject == null) {
                localObject = new ViewGroup.LayoutParams(width, height);
            }
            ((ViewGroup.LayoutParams) localObject).width = width;
            ((ViewGroup.LayoutParams) localObject).height = height;
            mHeaderContainer.setLayoutParams((ViewGroup.LayoutParams) localObject);
            mHeaderHeight = height;
            mHeaderWidth = width;
            isCustomHeaderHeight = true;
        }
    }

    /**
     * 设置HeaderView LayoutParams
     *
     * @param layoutParams LayoutParams
     */
    public void setHeaderLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (mHeaderContainer != null) {
            mHeaderContainer.setLayoutParams(layoutParams);
            mHeaderHeight = layoutParams.height;
            mHeaderWidth = layoutParams.width;
            isCustomHeaderHeight = true;
        }
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2,
                            int paramInt3, int paramInt4) {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        Log.d(TAG, "onLayout --> ");
        if (mHeaderHeight == 0 && mZoomView != null) {
            mHeaderHeight = mHeaderContainer.getHeight();
            mHeaderWidth = mHeaderContainer.getWidth();
        }
        if(mSearchContentWidth == 0 && mZoomView !=null) {
            mSearchContentWidth = mSearchContent.getWidth();
            mSearchContentHeight = mSearchContent.getHeight();
        }
        Log.d(TAG, "onLayout --> width:" + mSearchContentWidth);
        Log.d(TAG, "onLayout --> height:" + mSearchContentHeight);

    }

    public void setToolbarView(RelativeLayout toolbarView) {
        mToolbarView = toolbarView;
        mSearchContent =(RelativeLayout) mToolbarView.findViewById(R.id.search_content);
        mSearchView = (LinearLayout)mToolbarView.findViewById(R.id.search_view);
        mTopSearchView = (RelativeLayout)mToolbarView.findViewById(R.id.top_search_view);


       /* ViewGroup.LayoutParams zoomLayoutParams;
        zoomLayoutParams = mZoomView.getLayoutParams();
        //zoomLayoutParams.height = ((int) (f2 * mHeaderHeight));
        mZoomView.setLayoutParams(zoomLayoutParams);
        mToolbarView.setLayoutParams(zoomLayoutParams);
        mTopSearchView.setBackgroundColor(initColor);*/



    }

    class ScalingRunnable implements Runnable {
        protected long mDuration;
        protected boolean mIsFinished = true;
        protected float mScale;
        protected long mStartTime;

        ScalingRunnable() {
        }

        public void abortAnimation() {
            mIsFinished = true;
        }

        public boolean isFinished() {
            return mIsFinished;
        }

        public void run() {
            if (mZoomView != null) {
                float f2;
                ViewGroup.LayoutParams localLayoutParams;
                if ((!mIsFinished) && (mScale > 1.0D)) {
                    float f1 = ((float) SystemClock.currentThreadTimeMillis() - (float) mStartTime) / (float) mDuration;
                    f2 = mScale - (mScale - 1.0F) * PullToZoomScrollViewEx.sInterpolator.getInterpolation(f1);
                    localLayoutParams = mHeaderContainer.getLayoutParams();
                    Log.d(TAG, "ScalingRunnable --> f2 = " + f2);
                    if (f2 > 1.0F) {
                        localLayoutParams.height = ((int) (f2 * mHeaderHeight));
                        mHeaderContainer.setLayoutParams(localLayoutParams);
                        if (isCustomHeaderHeight) {
                            ViewGroup.LayoutParams zoomLayoutParams;
                            zoomLayoutParams = mZoomView.getLayoutParams();
                            zoomLayoutParams.height = ((int) (f2 * mHeaderHeight));
                            mZoomView.setLayoutParams(zoomLayoutParams);
                            mToolbarView.setLayoutParams(zoomLayoutParams);
                            mTopSearchView.setBackgroundColor(initColor);
                        }
                        post(this);
                        return;
                    }
                    mIsFinished = true;
                }
            }
        }

        public void startAnimation(long paramLong) {
            if (mZoomView != null) {
                mStartTime = SystemClock.currentThreadTimeMillis();
                mDuration = paramLong;
                mScale = ((float) (mHeaderContainer.getBottom()) / mHeaderHeight);
                mIsFinished = false;
                post(this);
            }
        }
    }

    protected class InternalScrollView extends ScrollView {
        private OnScrollViewChangedListener onScrollViewChangedListener;

        public InternalScrollView(Context context) {
            this(context, null);
        }

        public InternalScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void setOnScrollViewChangedListener(OnScrollViewChangedListener onScrollViewChangedListener) {
            this.onScrollViewChangedListener = onScrollViewChangedListener;
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);
            if (onScrollViewChangedListener != null) {
                onScrollViewChangedListener.onInternalScrollChanged(l, t, oldl, oldt);
            }
        }
    }

    protected interface OnScrollViewChangedListener {
        public void onInternalScrollChanged(int left, int top, int oldLeft, int oldTop);
    }

}
