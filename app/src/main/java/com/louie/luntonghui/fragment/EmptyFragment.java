package com.louie.luntonghui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.louie.luntonghui.R;

/**
 * Created by Administrator on 2015/6/5.
 */
public class EmptyFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_empty, null);
        return contentView;
    }
}
