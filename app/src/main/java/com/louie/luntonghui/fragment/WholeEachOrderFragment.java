package com.louie.luntonghui.fragment;

import android.os.Bundle;

import com.louie.luntonghui.App;
import com.louie.luntonghui.event.OrderConfirmEvent;
import com.squareup.otto.Subscribe;

/**
 * Created by Jack on 16/5/10.
 */
public class WholeEachOrderFragment extends EachOrderFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getBusInstance().register(this);
    }

    @Override
    public void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onOrderConfirmSuccess(OrderConfirmEvent event) {
        super.reloadData();
    }

    public void reload(){
        super.reloadData();
    }
}
