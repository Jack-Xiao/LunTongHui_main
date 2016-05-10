package com.louie.luntonghui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.MyFragmentPagerAdapter;
import com.louie.luntonghui.event.OrderConfirmEvent;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jack on 16/4/26.
 */
public class NewOrderFragment extends BaseFragment {

    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    //private OrderFragmentNewAdapter mPageAdapter;
    private MyFragmentPagerAdapter mPageAdapter;
    String[] orderStateList;
    private int ferenceState;
    private int initType = 0;
    public static final int NOTREFERENCE = 0;
    public static final int NEEDREFERECE = 1;
    public static final String ISREFERENCE = "is_reference";
    public static final String TYPE = "type";
    EachOrderFragment fragment1;
    EachOrderFragment fragment2;
    EachOrderFragment fragment3;
    EachOrderFragment fragment4;
    EachOrderFragment fragment5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getBusInstance().register(this);



        initPager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_order_new, container, false);
        ButterKnife.inject(this, contentView);
        parseArgument();

        setupViewPager();
        return contentView;
    }

    private void setupViewPager() {
        viewpager.setAdapter(mPageAdapter);
        tabs.setupWithViewPager(viewpager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        viewpager.setCurrentItem(initType);
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            initType = bundle.getInt(TYPE);
            ferenceState = bundle.getInt(ISREFERENCE, 0);
        }
    }

    @Subscribe
    public void onOrderConfirmSuccess(OrderConfirmEvent event) {
        fragment1.reloadData();
    }



    public void initPager(){
        orderStateList = getActivity().getResources().getStringArray(R.array.mine_order_state_list);
        fragment1 = EachOrderFragment.newFragment(EachOrderFragment.ORDERTYPE_WHOLE);
        fragment2 = EachOrderFragment.newFragment(EachOrderFragment.ORDERTYPE_WAIT);
        fragment3 = EachOrderFragment.newFragment(EachOrderFragment.ORDERTYPE_SONG);
        fragment4 = EachOrderFragment.newFragment(EachOrderFragment.ORDERTYPE_FINISH);
        fragment5 = EachOrderFragment.newFragment(EachOrderFragment.ORDERTYPE_CANCEL);

        Fragment[] fragments = new Fragment[]{fragment1, fragment2, fragment3, fragment4, fragment5};

        boolean [] fragmentsUpdateFlag =new boolean []{false, false, false, false,false};

        mPageAdapter = new MyFragmentPagerAdapter(getChildFragmentManager())
                .setFragment(fragments)
                .setFragmentUpdateFlag(fragmentsUpdateFlag)
                .setPageTitleList(orderStateList);
    }


}
