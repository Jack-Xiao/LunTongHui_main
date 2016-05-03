package com.louie.luntonghui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.OrderFragmentNewAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jack on 16/4/26.
 */
public class NewOrderFragment extends BaseFragment {
   /* @InjectView(R.id.toolbar)
    Toolbar toolbar;*/

    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    private OrderFragmentNewAdapter mPageAdapter;
    String[] orderStateList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderStateList = getActivity().getResources().getStringArray(R.array.mine_order_state_list);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_order_new, container, false);

        ButterKnife.inject(this, contentView);

        setupViewPager();
        return contentView;
    }

    private void setupViewPager() {
        mPageAdapter = new OrderFragmentNewAdapter(getActivity().getSupportFragmentManager());

        mPageAdapter.addFragment(EachOrderFragment.newFragment(EachOrderFragment.ORDERTYPE_WHOLE),orderStateList[0]);
        mPageAdapter.addFragment(EachOrderFragment.newFragment(EachOrderFragment.ORDERTYPE_WAIT),orderStateList[1]);
        mPageAdapter.addFragment(EachOrderFragment.newFragment(EachOrderFragment.ORDERTYPE_SONG),orderStateList[2]);
        mPageAdapter.addFragment(EachOrderFragment.newFragment(EachOrderFragment.ORDERTYPE_FINISH),orderStateList[3]);
        mPageAdapter.addFragment(EachOrderFragment.newFragment(EachOrderFragment.ORDERTYPE_CANCEL),orderStateList[4]);

        viewpager.setAdapter(mPageAdapter);
        tabs.setupWithViewPager(viewpager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
