package com.louie.luntonghui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.view.widget.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 16/1/16.
 */
public class GuideActivity extends AppCompatActivity{
    ViewPager viewPager;
    com.louie.luntonghui.view.widget.CircleIndicator indicator;
    private List<View> viewList;
    private int currentItem;
    private GestureDetector gestureDetector;
    private int flaggingWidth;// 互动翻页所需滚动的长度是当前屏幕宽度的1/3
    private CircleIndicator circleIndicator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initData();
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);

        circleIndicator = (CircleIndicator)findViewById(R.id.guide_indicator);
        circleIndicator.setViewPager(viewPager);

    }

    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {

            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "title";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));

            return viewList.get(position);
        }

    };

    private void initData() {
        viewList = new ArrayList<>();
        View view1 = getLayoutInflater().inflate(R.layout.view_guide_page1,null);
        View view2 = getLayoutInflater().inflate(R.layout.view_guide_page2,null);
        View view3 = getLayoutInflater().inflate(R.layout.view_guide_page3,null);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        Button start = (Button)view3.findViewById(R.id.start);
        start.setOnClickListener(btnStart);
    }

    private View.OnClickListener btnStart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GoToMainActivity();
        }
    };

    private void GoToMainActivity() {
            String userId = DefaultShared.getString(RegisterLogin.USERUID, App.DEFAULT_USER_ID);
            DefaultShared.putInt(Config.RUN_COUNT ,1);
            if(userId.equals(App.DEFAULT_USER_ID)){
                IntentUtil.startActivityToMainActivity(GuideActivity.this, RegisterLogin.class);
            }else{
                IntentUtil.startActivityToMainActivity(GuideActivity.this, MainActivity.class);
            }
        finish();
    }
}