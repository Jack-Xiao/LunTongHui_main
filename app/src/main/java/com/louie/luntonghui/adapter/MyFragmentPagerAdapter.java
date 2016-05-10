package com.louie.luntonghui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jack on 16/5/9.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    FragmentManager fm;
    private Fragment[] fragments;
    boolean[] fragmentsUpdateFlag;// = { false, false, false, false,false };
    String[] orderStateList;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }
    public MyFragmentPagerAdapter setFragment(Fragment [] fragments){
        this.fragments = fragments;
        return this;
    }

    public MyFragmentPagerAdapter setFragmentUpdateFlag(boolean[] fragmentsUpdateFlag){
        this.fragmentsUpdateFlag = fragmentsUpdateFlag;
        return this;
    }

    public MyFragmentPagerAdapter setPageTitleList(String[] titleList){
        this.orderStateList = titleList;
        return this;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment= fragments[position % fragments.length];
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
    /*@Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container,position);
        String fragmentTag = fragment.getTag();

        if(fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]){
            //如果这个fragment需要更新
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            fragment = fragments[position % fragments.length];
            //添加新fragment时必须用前面获得的tag， 这点很重要
            ft.add(container.getId(),fragment,fragmentTag);
            ft.attach(fragment);
            ft.commitAllowingStateLoss();
            //复位更新标志
            fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
        }
        return fragment;
    }*/

    @Override
    public CharSequence getPageTitle(int position) {
        if(orderStateList !=null){
            return orderStateList[position];
        }
        return super.getPageTitle(position);
    }
}
