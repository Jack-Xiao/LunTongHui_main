package com.louie.luntonghui.util;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.louie.luntonghui.fragment.CarFragment;
import com.louie.luntonghui.fragment.CategoryFragment;
import com.louie.luntonghui.fragment.GoodsDetailFragment;
import com.louie.luntonghui.fragment.HomeFragment;
import com.louie.luntonghui.fragment.MineFragment1;
import com.louie.luntonghui.fragment.NewOrderFragment;

/**
 * Created by Administrator on 2015/6/5.
 */
public class FragmentFactory {
    private Activity activity;
    private static Fragment orderFragment = new NewOrderFragment();

    public static Fragment getNavigationFragment(int index){
        Fragment fragment;
        switch (index){
            case 0:
                fragment = new HomeFragment();
            break;
            case 1:
                fragment = new CategoryFragment();
                break;
            case 2:
                fragment = new MineFragment1();
                break;
            case 3:
                fragment = new CarFragment();
                break;
            case 4:
                //fragment = new OrderFragment();
                fragment = new NewOrderFragment();
                break;
            case 5:
                fragment = new GoodsDetailFragment();
            default:
                fragment = new HomeFragment();
                break;
        }
        return fragment;
    }
}
