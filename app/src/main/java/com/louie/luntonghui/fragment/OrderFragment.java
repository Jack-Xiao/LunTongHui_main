package com.louie.luntonghui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.OrderAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.CategoryGoAroundEvent;
import com.louie.luntonghui.model.db.Order;
import com.louie.luntonghui.model.result.OrderList;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.view.RecyclerViewLinearLayoutViewItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

import static com.louie.luntonghui.view.RecyclerViewLinearLayoutViewItemDecoration.HORIZONTAL_LIST;

/**
 * Created by Administrator on 2015/7/17.
 */
public class OrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.whole)
    TextView whole;
    @InjectView(R.id.wait_confirm)
    TextView waitConfirm;
    @InjectView(R.id.wait_goods)
    TextView waitGoods;
    @InjectView(R.id.wait_play)
    TextView waitPlay;
    @InjectView(R.id.has_cancel)
    TextView hasCancel;

    @InjectView(R.id.empty_view)
    LinearLayout emptyView;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.look_over)
    Button lookOver;
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    private List<TextView> tvList;
    private Context mContext;
    private OrderAdapter mAdapter;
    private int initType = 0;
    public static final int NOTREFERENCE = 0;
    public static final int NEEDREFERECE = 1;
    public static final String ISREFERENCE = "is_reference";

    public static final String TYPE = "type";
    private int ferenceState;
    private ComeBackListener mBackListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getBusInstance().register(this);
        tvList = new ArrayList<>();

//        test(); //测试友盟
    }

    private void test() {
        int a = 1/0;
    }

    public interface ComeBackListener {
        public void back();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mBackListener = (ComeBackListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SearchListener.");
        }
    }

    @OnClick(R.id.toolbar_navigation)
    public void onBack() {
        mBackListener.back();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.inject(this, contentView);
        mContext = getActivity();
        mAdapter = new OrderAdapter(getActivity());
        toolbarTitle.setText(R.string.mine_order);
        parseArgument();

        tvList.add(whole);
        tvList.add(waitConfirm);
        tvList.add(waitGoods);
        tvList.add(waitPlay);
        tvList.add(hasCancel);

        swipeContainer.setOnRefreshListener(this);

        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new RecyclerViewLinearLayoutViewItemDecoration(getActivity(), HORIZONTAL_LIST));

         return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (NOTREFERENCE == ferenceState) {
            OnClickOrderNavigation(initType);
        } else {
            onRefresh();
        }
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            initType = bundle.getInt(TYPE);
            ferenceState = bundle.getInt(ISREFERENCE, 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.whole)
    public void OnWholeClick() {
        OnClickOrderNavigation(0);
    }

    @OnClick(R.id.wait_confirm)
    public void onWaitConfirm() {
        OnClickOrderNavigation(1);
    }

    @OnClick(R.id.wait_goods)
    public void onWaitGoods() {
        OnClickOrderNavigation(2);
    }

    @OnClick(R.id.wait_play)
    public void onWaitPlay() {
        OnClickOrderNavigation(3);
    }

    @OnClick(R.id.has_cancel)
    public void onHasCancel() {
        OnClickOrderNavigation(4);
    }

    @Optional
    @OnClick(R.id.look_over)
    public void onLookOverClick() {
        App.getBusInstance().post(new CategoryGoAroundEvent());
    }

    @Override
    public void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }

    public void OnClickOrderNavigation(final int chooseIndex) {
        for (int i = 0; i < tvList.size(); i++) {
            if(isAdded() && getActivity()!=null) {
                if (chooseIndex == i) {
                    tvList.get(i).setTextColor(getResources().getColor(R.color.order_font_choose));
                } else {
                    tvList.get(i).setTextColor(getResources().getColor(R.color.order_font_normal));
                }
            }
        }

        String type = "0";
        switch (chooseIndex) {
            case 1:
                type = "1";
                break;
            case 2:
                type = "3";
                break;
            case 3:
                type = "8";
                break;
            case 4:
                type = "9";
                break;
        }

        final String finalType = type;

        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<Order>>() {
            @Override
            protected void onPreExecute() {
                if (emptyView != null) emptyView.setVisibility(View.GONE);
                if (recyclerView != null) recyclerView.setVisibility(View.GONE);
            }

            @Override
            protected List<Order> doInBackground(Object... params) {
                List<Order> list;
                if (chooseIndex == 0) {
                    list = new Select()
                            .from(Order.class)
                            .execute();
                } else {
                    list = new Select()
                            .from(Order.class)
                            .where("type = ?", finalType)
                            .execute();
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Order> orders) {
                if (orders.size() == 0) {
                    if (emptyView != null) emptyView.setVisibility(View.VISIBLE);
                    if (recyclerView != null) recyclerView.setVisibility(View.GONE);
                } else {
                    if (recyclerView != null) recyclerView.setVisibility(View.VISIBLE);
                    //mAdapter.setData(orders);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        swipeContainer.setRefreshing(true);
        initOrderList();
    }

    private void initOrderList() {
        if (userId.equals(RegisterLogin.DEFAULT_USER_ID)) return;
        String url = String.format(ConstantURL.GET_WHOLE_ORDER, userId);
        RequestManager.addRequest(new GsonRequest(url, OrderList.class, getWholeOrderList(), errorListener()), this);
    }

    private Response.Listener<OrderList> getWholeOrderList() {
        return new Response.Listener<OrderList>() {
            @Override
            public void onResponse(final OrderList orderList) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<Order>>() {
                    @Override
                    protected List<Order> doInBackground(Object... params) {
                        List<Order> data = new ArrayList<Order>();
                            new Delete()
                                    .from(Order.class)
                                    .execute();

                            for (int i = 0; i < orderList.mysalelist.size(); i++) {
                                Order order = new Order();
                                order.allowToModify = orderList.mysalelist.get(i).allow_to_modify;
                                order.type = orderList.mysalelist.get(i).handler;
                                order.money = orderList.mysalelist.get(i).money;
                                order.payName = orderList.mysalelist.get(i).pay_name;
                                order.orderId = orderList.mysalelist.get(i).order_id;
                                order.orderSn = orderList.mysalelist.get(i).order_sn;
                                order.orderAmount = orderList.mysalelist.get(i).order_amount;
                                order.addTime = orderList.mysalelist.get(i).add_time;
                                order.save();
                                data.add(order);
                            }

                        return data;
                    }

                    @Override
                    protected void onPostExecute(List<Order> orders) {
                        if (swipeContainer != null) swipeContainer.setRefreshing(false);
                        OnClickOrderNavigation(initType);
                    }
                });
            }
        };
    }
}
