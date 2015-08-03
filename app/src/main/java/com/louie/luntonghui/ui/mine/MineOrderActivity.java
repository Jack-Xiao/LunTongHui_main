package com.louie.luntonghui.ui.mine;

import android.os.Bundle;

import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.SecondLevelBaseActivity;

/**
 * Created by Administrator on 2015/6/9.
 */
public class MineOrderActivity extends SecondLevelBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int setToolTitle() {
        return R.string.mine_order;
    }

    @Override
    protected int getContentView() {
        return 0;
    }
}
