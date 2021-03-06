package com.louie.luntonghui.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.category.GoodsDetailBuyActivity;
import com.louie.luntonghui.view.grally_view.GalleryWidget.BasePagerAdapter;
import com.louie.luntonghui.view.grally_view.GalleryWidget.GalleryViewPager;
import com.louie.luntonghui.view.grally_view.GalleryWidget.UrlPagerAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Jack on 15/8/1.
 */
public class ImageActivity extends AppCompatActivity {
    String[] urls;
    @butterknife.InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @butterknife.InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @butterknife.InjectView(R.id.view_pager)
    GalleryViewPager viewPager;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.inject(this);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            urls = bundle.getStringArray(GoodsDetailBuyActivity.IMAGES);
            index = Integer.parseInt(bundle.getString(GoodsDetailBuyActivity.INDEX));
        }


        GalleryViewPager viewPager = (GalleryViewPager) findViewById(R.id.view_pager);
        List<String> items = new ArrayList<>();
        Collections.addAll(items, urls);
        UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(this, items);
        pagerAdapter.setOnItemChangeListener(new BasePagerAdapter.OnItemChangeListener() {
            @Override
            public void onItemChange(int currentPosition) {

            }
        });
        toolbarTitle.setText("");
        toolbarNavigation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager.setOffscreenPageLimit(urls.length);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(index);
        //GalleryViewPager page = (GalleryViewPager)findViewById(R.id.view_pager);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
