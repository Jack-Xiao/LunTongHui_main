package com.louie.luntonghui.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.MineAttentionAdapter;
import com.louie.luntonghui.model.db.AttentionGoods;
import com.louie.luntonghui.model.result.MineAttentionResult;
import com.louie.luntonghui.ui.SwipeRefreshBaseActivity;
import com.louie.luntonghui.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by Jack on 15/8/11.
 */
public class MineAttentionActivity extends SwipeRefreshBaseActivity {
    private RecyclerView mRecyclerView;
    private MineAttentionAdapter mAdapter;
    private TextView emptyAtt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpRecyclerView();
        // mApi.getMineAttentionGoodsList(userId,userType);

        /*getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("我的收藏");*/

        emptyAtt = (TextView)findViewById(R.id.empty_attention);
        loadData();
    }

    @Override
    protected int toolbarTitle() {
        return R.string.activity_area_mine_attention;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_mine_attention;
    }

    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new MineAttentionAdapter(this);

        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        /*mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                AttentionGoods goods = mAdapter.getData().get(position);
                String goodsId = goods.goodsId;
                Bundle bundle = new Bundle();
                bundle.putString(GoodsDetailActivity.GOODSDETAILID, goodsId);
                IntentUtil.startActivity(MineAttentionActivity.this, GoodsDetailBuyActivity.class, bundle);
            }
        }));*/

        loadData();
    }

    void loadData() {
        setRefreshing(true);
        AppObservable.bindActivity(this, mApi.getMineAttentionGoodsList(userId, userType))
                .map(new Func1<MineAttentionResult, List<AttentionGoods>>() {
                         @Override
                         public List<AttentionGoods> call(MineAttentionResult list) {
                             List<AttentionGoods> data = new ArrayList<>();
                             new Delete().
                                     from(AttentionGoods.class)
                                     .execute();
                             try {
                                 for (int i = 0; i < list.listallcat.size(); i++) {
                                     MineAttentionResult.ListallcatEntity entity = list.listallcat.get(i);
                                     AttentionGoods goods1 = new AttentionGoods();
                                     goods1.goodsId = entity.goods_id;
                                     goods1.goodsName = entity.goods_name;
                                     goods1.goodsImg = entity.goods_img;
                                     goods1.goodsSN = entity.goods_sn;
                                     goods1.goodsNumber = entity.goods_number;
                                     goods1.marketPrice = entity.market_price;
                                     goods1.shopPrice = entity.shop_price;
                                     goods1.gysMoney = entity.gys_money;
                                     goods1.promotePrice = entity.promote_price;
                                     goods1.goodsBrief = entity.goods_brief;
                                     goods1.goodsDesc = entity.goods_desc;
                                     goods1.sortOrder = entity.sort_order;
                                     goods1.isBest = entity.is_best;
                                     goods1.isNew = entity.is_new;
                                     goods1.isHot = entity.is_hot;
                                     goods1.display = entity.display;
                                     goods1.giveIntegral = entity.give_integral;
                                     goods1.integral = entity.integral;
                                     goods1.isPromote = entity.is_promote;
                                     goods1.discounta = entity.discounta;
                                     goods1.discount = entity.discount;
                                     goods1.discountTime = entity.discount_time;
                                     goods1.discountName = entity.discount_name;
                                     goods1.guige = entity.guige;
                                     goods1.unit = entity.danwei;
                                     goods1.recId = entity.rec_id;
                                     goods1.discount = entity.discount;
                                     goods1.discountType = entity.discount_type;
                                     goods1.save();
                                     goods1.inventory = entity.inventory;
                                     data.add(goods1);
                                 }
                             } finally {
                             }
                             return data;
                         }
                     })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Observer<List<AttentionGoods>> observer = new Observer<List<AttentionGoods>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            setRefreshing(false);
            ToastUtil.showShortToast(mContext, R.string.network_connect_fail);
        }

        @Override
        public void onNext(List<AttentionGoods> attentionGoodses) {
            setRefreshing(false);
            if(attentionGoodses.size() == 0)emptyAtt.setVisibility(View.VISIBLE);
            mAdapter.setData(attentionGoodses);
        }
    };
}
