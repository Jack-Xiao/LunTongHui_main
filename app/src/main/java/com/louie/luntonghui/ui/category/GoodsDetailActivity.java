package com.louie.luntonghui.ui.category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.GoodsDetailListAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.ShowCarListEvent;
import com.louie.luntonghui.model.db.Goods;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.CurrentBrandGoodsList;
import com.louie.luntonghui.model.result.CurrentBrandGoodsList1;
import com.louie.luntonghui.rest.ServiceManager;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.ui.mine.MineService.MineCustomerOrderListActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.ui.search.SearchActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.view.BadgeView;
import com.louie.luntonghui.view.MyPullUpListView;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.louie.luntonghui.ui.register.RegisterLogin.USER_DEFAULT;
import static com.louie.luntonghui.ui.register.RegisterLogin.USER_TYPE;
import static com.louie.luntonghui.ui.register.RegisterLogin.USER_WHOLESALER;


/**
 * Created by Administrator on 2015/6/19.
 */
public class GoodsDetailActivity extends BaseNormalActivity implements
        GoodsDetailListAdapter.ReferenceBadgeListener,AbsListView.OnScrollListener {
    public static final String GOODSDETAILURL = "url";
    public static final String GOODSDETAILID = "goods_id";
    public static final String SALES = "sales";
    public static final String COLLIIGATE = "colligate";
    public static final String PRICE = "price";
    public static final String NEW_GOODS = "new_goods";
    public static final int IS_NEW_GOODS = 1;
    public static final int NOT_NEW_GOODS = 0;
    public static final int INIT_PAGE = 1;
    public static final int MAX_PAGE_SIZE = Integer.MAX_VALUE;
    public String url;
    public String id;
    @InjectView(R.id.listview)
    MyPullUpListView listView;

    @InjectView(R.id.progress)
    ProgressBar progress;
    @InjectView(R.id.colligate)
    TextView colligate;
    @InjectView(R.id.sales_volume)
    TextView salesVolume;
    @InjectView(R.id.price)
    TextView price;
    @InjectView(R.id.navigation_search)
    TextView navigationSearch;
    /*    @InjectView(R.id.navigation_search_edit)
        EditText navigationSearchEdit;*/
    @InjectView(R.id.icon)
    ImageView icon;

    @InjectView(R.id.main_fab)
    //com.melnykov.fab.FloatingActionButton mainFab;
            com.shamanland.fab.FloatingActionButton mainFab;

    private RecyclerView mRecyclerView;
    private ServiceManager.LunTongHuiApi api;
    private GoodsDetailListAdapter mAdapter;
    private List<Goods> data;
    private String intro = COLLIIGATE; //排序  销量/综合/价格  sales:销量  colligate：综合, price:价格

    private String sorting = "2"; // 默认是降序  1:升序 2:降序
    private String show; // B/C端   01是B, 02是C, 不传是B
    private String display; //1辽宁，0广东

    private String regionArg = "city";
    private String BCClient = "show";

    public static final String INTRO = "intro";
    public static final String SORTING = "sorting";

    private boolean isSales = false;
    private boolean isColligate = false;
    private boolean isPrice = false;
    Drawable sorting_up, sorting_down;
    private String goodsParentId;
    public List<String> carIdList;
    private boolean isSearch = false;
    private String searchContent;
    public String userId;
    private boolean isNewGods = false;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_list);
        ButterKnife.inject(this);

        mProgressDialog = new ProgressDialog(mContext);
        icon.setImageResource(R.drawable.actionbar_back);
        Bundle bundle = getIntent().getExtras();
        userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        if (bundle != null && !bundle.getString(SearchActivity.SEARCH_CONTENT,
                SearchActivity.SEARCH_DEFAULT_CONTENT).equals(SearchActivity.SEARCH_DEFAULT_CONTENT)) {
            searchContent = bundle.getString(SearchActivity.SEARCH_CONTENT,
                    SearchActivity.SEARCH_DEFAULT_CONTENT);
            isSearch = true;
        }

        if (bundle != null && bundle.getInt(NEW_GOODS) == IS_NEW_GOODS) {

            isNewGods = true;
        }

        sorting_up = getResources().getDrawable(R.drawable.category_product_up);
        sorting_down = getResources().getDrawable(R.drawable.category_product_down);
        sorting_up.setBounds(0, 0, sorting_up.getMinimumWidth(), sorting_up.getMinimumHeight());
        sorting_down.setBounds(0, 0, sorting_down.getMinimumWidth(), sorting_down.getMinimumHeight());

        carIdList = ((App) getApplication()).goodsInCaridsList;
        show = DefaultShared.getString(RegisterLogin.USER_TYPE, USER_DEFAULT);
        show = "";

        if (DefaultShared.getString(USER_TYPE, USER_DEFAULT).equals(USER_WHOLESALER)) {
            show = "01";
        } else {
            show = "02";
        }

        data = new ArrayList<>();
        //listView.addFooterView(footerView);
        mAdapter = new GoodsDetailListAdapter(GoodsDetailActivity.this);

        listView.setAdapter(mAdapter);


        mProgressDialog.show();
        //listView.setOnTouchListener(new ShowHideOnScroll(mainFab));
        initBadgeView();

        initSorting();

        mProgressDialog.hide();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goods goods = mAdapter.getData().get(position);
                String goodsId = goods.goodsId;
                Bundle bundle = new Bundle();
                bundle.putString(GOODSDETAILID, goodsId);
                IntentUtil.startActivity(GoodsDetailActivity.this, GoodsDetailBuyActivity.class, bundle);
            }
        });
    }

    private BadgeView mBadgeView;

    private void initBadgeView() {
        mBadgeView = new BadgeView(mContext, mainFab);
        mBadgeView.setTextSize(Config.BADGEVIEW_SIZE_BIG);
    }

    private void initSorting() {
        display = Config.getRenamePlace();

        String cType = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT);

        if (isNewGods) {
            //String cityId = DefaultShared.getString(App.CITYID, App.DEFAULT_CITYID);
            String newGoodsUrl = String.format(ConstantURL.NEWGOODS, display, cType, INIT_PAGE, MAX_PAGE_SIZE, userId);

            url = newGoodsUrl;

            listView.setMyPullUpListViewCallBack(new MyPullUpListView.MyPullUpListViewCallBack() {
                @Override
                public void scrollBottomState() {
                    listView.hideFooterView(false);
                }
            });

        } else if (!isSearch) {
            display = DefaultShared.getString(App.CITYID, App.DEFAULT_CITYID);

            String cityId = DefaultShared.getString(App.CITYID, App.DEFAULT_CITYID);

            goodsParentId = getIntent().getExtras().getString(GOODSDETAILID, "-1");
            url = getIntent().getExtras().getString(GOODSDETAILURL).toString();
            url = url + "&" + ConstantURL.MODE + "&" + regionArg + "=" + display;
            url = url + "&ctype=" + cType;
            url = url + "&user_id=" + userId;
            //  + "&" + BCClient + "=" + show;

            //id = getIntent().getBundleExtra(GOODSDETAILID);

            String initUrl = url + "&" + INTRO + "=" + intro +
                    "&" + SORTING + "=" + sorting;

            //listView.setOnScrollListener(new MyScrollListener());
            listView.setMyPullUpListViewCallBack(new MyPullUpListView.MyPullUpListViewCallBack() {
                @Override
                public void scrollBottomState() {
                    loadData();
                }
            });
        } else {

            //navigationSearch.setVisibility(View.GONE);
            try {
                searchContent = URLEncoder.encode(searchContent, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String searchUrl = String.format(ConstantURL.GOODS_SEARCH_LIST, userId, searchContent,
                    cType, display);
            url = searchUrl;

            listView.setMyPullUpListViewCallBack(new MyPullUpListView.MyPullUpListViewCallBack() {
                @Override
                public void scrollBottomState() {
                    listView.hideFooterView(false);
                }
            });
            //executeRequest(new GsonRequest());
        }

        //为商品分类重写 分类
        if(isNewGods || isSearch) {
            onColligateset();
        }else{
            currentPage = 0;
            loadData();
        }
    }

    private int totalCount;
    private int currentPage;

    /*class MyScrollListener implements AbsListView.OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                int last = view.getLastVisiblePosition();

                if ( last == totalCount -1 ) {
                    ToastUtil.showShortToast(mContext, R.string.loading_the_end);
                    return;
                }

                listView.removeFooterView(footerView);

                if (mAdapter != null) {
                    int adapterCnt =listView.getCount() - 1;

                    if (adapterCnt == last) {
                        //if(last>= adapterCnt && footView.getVisibility() == View.VISIBLE){
                        listView.addFooterView(footerView);

                        loadData();
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }*/

    private void loadData() {
        progress.setVisibility(View.VISIBLE);
        currentPage +=1;
        String curUrl = url + "&" + "page=" + currentPage + "&" + "page_size="
                                + MineCustomerOrderListActivity.loadingDataCount;
        executeRequest(new GsonRequest(curUrl, CurrentBrandGoodsList1.class, getCurrentBrand(), errorListener()));

    }

    private Response.Listener<CurrentBrandGoodsList1> getCurrentBrand() {
        return new Response.Listener<CurrentBrandGoodsList1>() {
            @Override
            public void onResponse(final CurrentBrandGoodsList1 response) {
                totalCount = Integer.parseInt(response.total_count);
                TaskUtils.executeAsyncTask(
                        new AsyncTask<Object, Object, List<Goods>>() {
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                            }

                            @Override
                            protected void onPostExecute(List<Goods> goodses) {
                                super.onPostExecute(goodses);
                                progress.setVisibility(View.GONE);
                                mAdapter.addData(goodses);
                                listView.hideFooterView(false);
                            }

                            @Override
                            protected List<Goods> doInBackground(Object... params) {
                                if (response.listallcat.size() == 0)
                                    return null;

                                data.clear();

                                List<CurrentBrandGoodsList.ListallcatEntity> list = new ArrayList<CurrentBrandGoodsList.ListallcatEntity>();
                                List<String> curGoodsId = new ArrayList<String>();
                                List<Goods> lists = new Select()
                                        .from(Goods.class)
                                        .where("goods_parent_id=?", goodsParentId)
                                        .execute();

                                for (int i = 0; i < lists.size(); i++) {
                                    curGoodsId.add(lists.get(i).goodsId);
                                }

                                List<ShoppingCar> lists1 = new ArrayList<>();
                                lists1 = new Select()
                                        .from(ShoppingCar.class)
                                        .execute();
                                List<String> goodsIds = new ArrayList<String>();
                                for (int i = 0; i < lists1.size(); i++) {
                                    goodsIds.add(lists1.get(i).goodsId);
                                }

                                if (lists.size() > 0) {
                                    try {
                                        for (int i = 0; i < response.listallcat.size(); i++) {
                                            CurrentBrandGoodsList1.ListallcatEntity entity = response.listallcat.get(i);
                                            Goods goods1 = new Goods();
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
                                            goods1.goodsParentId = goodsParentId;
                                            goods1.guige = entity.guige;
                                            goods1.unit = entity.danwei;
                                            goods1.discountType = entity.discount_type;
                                            goods1.inventory = entity.inventory;

                                            data.add(goods1);
                                            if (!curGoodsId.contains(entity.goods_id)) {
                                                Goods goods = new Goods();
                                                goods.goodsId = entity.goods_id;
                                                goods.goodsName = entity.goods_name;
                                                goods.goodsImg = entity.goods_img;
                                                goods.goodsSN = entity.goods_sn;
                                                goods.goodsNumber = entity.goods_number;
                                                goods.marketPrice = entity.market_price;
                                                goods.shopPrice = entity.shop_price;
                                                goods.gysMoney = entity.gys_money;
                                                goods.promotePrice = entity.promote_price;
                                                goods.goodsBrief = entity.goods_brief;
                                                goods.goodsDesc = entity.goods_desc;
                                                goods.sortOrder = entity.sort_order;
                                                goods.isBest = entity.is_best;
                                                goods.isNew = entity.is_new;
                                                goods.isHot = entity.is_hot;
                                                goods.display = entity.display;
                                                goods.giveIntegral = entity.give_integral;
                                                goods.integral = entity.integral;
                                                goods.isPromote = entity.is_promote;
                                                goods.discounta = entity.discounta;
                                                goods.discount = entity.discount;
                                                goods.discountTime = entity.discount_time;
                                                goods.discountName = entity.discount_name;
                                                goods.goodsParentId = goodsParentId;
                                                goods.guige = entity.guige;
                                                goods.unit = entity.danwei;
                                                goods.discountType = entity.discount_type;
                                                goods.inventory = entity.inventory;
                                                goods.save();
                                            }
                                        }
                                    } finally {
                                        //ActiveAndroid.setTransactionSuccessful();
                                        //ActiveAndroid.endTransaction();
                                    }

                                } else {

                                    for (int i = 0; i < response.listallcat.size(); i++) {
                                        CurrentBrandGoodsList1.ListallcatEntity entity = response.listallcat.get(i);
                                        Goods goods = new Goods();
                                        goods.goodsId = entity.goods_id;
                                        goods.goodsName = entity.goods_name;
                                        goods.goodsImg = entity.goods_img;
                                        goods.goodsSN = entity.goods_sn;
                                        goods.goodsNumber = entity.goods_number;
                                        goods.marketPrice = entity.market_price;
                                        goods.shopPrice = entity.shop_price;
                                        goods.gysMoney = entity.gys_money;
                                        goods.promotePrice = entity.promote_price;
                                        goods.goodsBrief = entity.goods_brief;
                                        goods.goodsDesc = entity.goods_desc;
                                        goods.sortOrder = entity.sort_order;
                                        goods.isBest = entity.is_best;
                                        goods.isNew = entity.is_new;
                                        goods.isHot = entity.is_hot;
                                        goods.display = entity.display;
                                        goods.giveIntegral = entity.give_integral;
                                        goods.integral = entity.integral;
                                        goods.isPromote = entity.is_promote;
                                        goods.discounta = entity.discounta;
                                        goods.discount = entity.discount;
                                        goods.discountTime = entity.discount_time;
                                        goods.discountName = entity.discount_name;
                                        goods.goodsParentId = goodsParentId;
                                        goods.isChecked = Goods.GOODS_IS_NOT_BUY;
                                        goods.guige = entity.guige;
                                        goods.unit = entity.danwei;
                                        goods.discountType = entity.discount_type;
                                        goods.inventory = entity.inventory;
                                        goods.save();
                                        data.add(goods);
                                    }
                                }
                                for (int i = 0; i < data.size(); i++) {
                                    if (goodsIds.contains(data.get(i).goodsId)) {
                                        data.get(i).isChecked = Goods.GOODS_IS_BUY;
                                    } else {
                                        data.get(i).isChecked = Goods.GOODS_IS_NOT_BUY;
                                    }
                                }
                                return data;
                            }
                        }
                );
            }
        };
    }


    private void executeUrl(String url) {
        executeRequest(new GsonRequest(url, CurrentBrandGoodsList.class, getGoodsList(), errorListener()));
    }

    private Response.Listener<CurrentBrandGoodsList> getGoodsList() {
        return new Response.Listener<CurrentBrandGoodsList>() {
            @Override
            public void onResponse(final CurrentBrandGoodsList currentBrandGoodsList) {
                TaskUtils.executeAsyncTask(
                        new AsyncTask<Object, Object, List<Goods>>() {
                                               @Override
                                               protected void onPreExecute() {
                                                   progress.setVisibility(View.VISIBLE);
                                               }

                                               @Override
                                               protected void onPostExecute(List<Goods> goodses) {
                                                   progress.setVisibility(View.GONE);
                                                   if (goodses == null) return;
                                                   mAdapter.setData(goodses);
                                                   referenceBadge();
                                               }

                                               @Override
                                               protected List<Goods> doInBackground(Object... params) {

                                                   if (currentBrandGoodsList.listallcat.size() == 0)
                                                       return null;

                                                   List<Goods> data = new ArrayList<Goods>();

                                                   if (isSearch || isNewGods) {

                                                       List<ShoppingCar> lists1 = new ArrayList<>();
                                                       lists1 = new Select()
                                                               .from(ShoppingCar.class)
                                                               .execute();
                                                       List<String> goodsIds = new ArrayList<String>();
                                                       for (int i = 0; i < lists1.size(); i++) {
                                                           goodsIds.add(lists1.get(i).goodsId);
                                                       }

                                                       for (int i = 0; i < currentBrandGoodsList.listallcat.size(); i++) {
                                                           CurrentBrandGoodsList.ListallcatEntity entity = currentBrandGoodsList.listallcat.get(i);
                                                           Goods goods1 = new Goods();
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
                                                           goods1.goodsParentId = goodsParentId;
                                                           goods1.guige = entity.guige;
                                                           goods1.unit = entity.danwei;
                                                           goods1.discountType = entity.discount_type;
                                                           goods1.inventory = entity.inventory;
                                                           //goods1.save();
                                                           data.add(goods1);
                                                       }
                                                       for (int i = 0; i < data.size(); i++) {
                                                           if (goodsIds.contains(data.get(i).goodsId)) {
                                                               data.get(i).isChecked = Goods.GOODS_IS_BUY;
                                                           } else {
                                                               data.get(i).isChecked = Goods.GOODS_IS_NOT_BUY;
                                                           }
                                                       }
                                                       return data;

                                                   } else {
                                                       List<CurrentBrandGoodsList.ListallcatEntity> list = new ArrayList<CurrentBrandGoodsList.ListallcatEntity>();
                                                       List<String> curGoodsId = new ArrayList<String>();
                                                       List<Goods> lists = new Select()
                                                               .from(Goods.class)
                                                               .where("goods_parent_id=?", goodsParentId)
                                                               .execute();
                                                       Log.d("TAG ...list.size", lists.size() + "");
                                                       for (int i = 0; i < lists.size(); i++) {
                                                           curGoodsId.add(lists.get(i).goodsId);
                                                       }


                                                       List<ShoppingCar> lists1 = new ArrayList<>();
                                                       lists1 = new Select()
                                                               .from(ShoppingCar.class)
                                                               .execute();
                                                       List<String> goodsIds = new ArrayList<String>();
                                                       for (int i = 0; i < lists1.size(); i++) {
                                                           goodsIds.add(lists1.get(i).goodsId);
                                                       }

                                                       if (lists.size() > 0) {
                                                           try {
                                                               for (int i = 0; i < currentBrandGoodsList.listallcat.size(); i++) {
                                                                   CurrentBrandGoodsList.ListallcatEntity entity = currentBrandGoodsList.listallcat.get(i);
                                                                   Goods goods1 = new Goods();
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
                                                                   goods1.goodsParentId = goodsParentId;
                                                                   goods1.guige = entity.guige;
                                                                   goods1.unit = entity.danwei;
                                                                   goods1.discountType = entity.discount_type;
                                                                   goods1.inventory = entity.inventory;

                                                                   //goods1.save();
                                                                   data.add(goods1);

                                                                   if (!curGoodsId.contains(entity.goods_id)) {
                                                                       Goods goods = new Goods();
                                                                       goods.goodsId = entity.goods_id;
                                                                       goods.goodsName = entity.goods_name;
                                                                       goods.goodsImg = entity.goods_img;
                                                                       goods.goodsSN = entity.goods_sn;
                                                                       goods.goodsNumber = entity.goods_number;
                                                                       goods.marketPrice = entity.market_price;
                                                                       goods.shopPrice = entity.shop_price;
                                                                       goods.gysMoney = entity.gys_money;
                                                                       goods.promotePrice = entity.promote_price;
                                                                       goods.goodsBrief = entity.goods_brief;
                                                                       goods.goodsDesc = entity.goods_desc;
                                                                       goods.sortOrder = entity.sort_order;
                                                                       goods.isBest = entity.is_best;
                                                                       goods.isNew = entity.is_new;
                                                                       goods.isHot = entity.is_hot;
                                                                       goods.display = entity.display;
                                                                       goods.giveIntegral = entity.give_integral;
                                                                       goods.integral = entity.integral;
                                                                       goods.isPromote = entity.is_promote;
                                                                       goods.discounta = entity.discounta;
                                                                       goods.discount = entity.discount;
                                                                       goods.discountTime = entity.discount_time;
                                                                       goods.discountName = entity.discount_name;
                                                                       goods.goodsParentId = goodsParentId;
                                                                       goods.guige = entity.guige;
                                                                       goods.unit = entity.danwei;
                                                                       goods.discountType = entity.discount_type;
                                                                       goods1.inventory = entity.inventory;
                                                                       goods.save();
                                                                   }
                                                               }
                                                           } finally {
                                                               //ActiveAndroid.setTransactionSuccessful();
                                                               //ActiveAndroid.endTransaction();
                                                           }

                                                       } else {

                                                           for (int i = 0; i < currentBrandGoodsList.listallcat.size(); i++) {
                                                               CurrentBrandGoodsList.ListallcatEntity entity = currentBrandGoodsList.listallcat.get(i);
                                                               Goods goods = new Goods();
                                                               goods.goodsId = entity.goods_id;
                                                               goods.goodsName = entity.goods_name;
                                                               goods.goodsImg = entity.goods_img;
                                                               goods.goodsSN = entity.goods_sn;
                                                               goods.goodsNumber = entity.goods_number;
                                                               goods.marketPrice = entity.market_price;
                                                               goods.shopPrice = entity.shop_price;
                                                               goods.gysMoney = entity.gys_money;
                                                               goods.promotePrice = entity.promote_price;
                                                               goods.goodsBrief = entity.goods_brief;
                                                               goods.goodsDesc = entity.goods_desc;
                                                               goods.sortOrder = entity.sort_order;
                                                               goods.isBest = entity.is_best;
                                                               goods.isNew = entity.is_new;
                                                               goods.isHot = entity.is_hot;
                                                               goods.display = entity.display;
                                                               goods.giveIntegral = entity.give_integral;
                                                               goods.integral = entity.integral;
                                                               goods.isPromote = entity.is_promote;
                                                               goods.discounta = entity.discounta;
                                                               goods.discount = entity.discount;
                                                               goods.discountTime = entity.discount_time;
                                                               goods.discountName = entity.discount_name;
                                                               goods.goodsParentId = goodsParentId;
                                                               goods.isChecked = Goods.GOODS_IS_NOT_BUY;
                                                               goods.guige = entity.guige;
                                                               goods.unit = entity.danwei;
                                                               goods.discountType = entity.discount_type;
                                                               goods.inventory = entity.inventory;
                                                               goods.save();
                                                               data.add(goods);
                                                           }
                                                       }
                                                       for (int i = 0; i < data.size(); i++) {
                                                           if (goodsIds.contains(data.get(i).goodsId)) {
                                                               data.get(i).isChecked = Goods.GOODS_IS_BUY;
                                                           } else {
                                                               data.get(i).isChecked = Goods.GOODS_IS_NOT_BUY;
                                                           }
                                                       }
                                                       return data;
                                                   }
                                               }
                                           }
                );
            }
        };
    }


    @OnClick(R.id.sales_volume)
    public void onSalesVolume() {
        isSales = !isSales;
        intro = SALES;
        sorting = getSorting(isSales);
        String curUrl = url + "&" + INTRO + "=" + intro +
                "&" + SORTING + "=" + sorting;
        executeUrl(curUrl);
    }


    @OnClick(R.id.price)
    public void onPrice() {
        intro = PRICE;
        isPrice = !isPrice;
        sorting = getSorting(isPrice);
        String curUrl = url + "&" + INTRO + "=" + intro +
                "&" + SORTING + "=" + sorting;
        executeUrl(curUrl);
    }

    @OnClick(R.id.colligate)
    public void onColligateset() {
        intro = COLLIIGATE;
        isColligate = !isColligate;
        sorting = getSorting(isColligate);
        String curUrl = url + "&" + INTRO + "=" + intro +
                "&" + SORTING + "=" + sorting;
        executeUrl(curUrl);
    }

    private String getSorting(boolean isSelect) {
        switch (intro) {
            case COLLIIGATE:
                if (isSelect) {
                    colligate.setCompoundDrawables(null, null, sorting_up, null);
                } else {
                    colligate.setCompoundDrawables(null, null, sorting_down, null);
                }
                price.setCompoundDrawables(null, null, null, null);
                salesVolume.setCompoundDrawables(null, null, null, null);
                break;
            case PRICE:
                if (isSelect) {
                    price.setCompoundDrawables(null, null, sorting_up, null);
                } else {
                    price.setCompoundDrawables(null, null, sorting_down, null);
                }
                colligate.setCompoundDrawables(null, null, null, null);
                salesVolume.setCompoundDrawables(null, null, null, null);
                break;
            case SALES:
                if (isSelect) {
                    salesVolume.setCompoundDrawables(null, null, sorting_up, null);
                } else {
                    salesVolume.setCompoundDrawables(null, null, sorting_down, null);
                }
                colligate.setCompoundDrawables(null, null, null, null);
                price.setCompoundDrawables(null, null, null, null);
                break;
        }

        if (isSelect) {
            return "1";
        } else {
            return "2";
        }
    }

    @OnClick(R.id.navigation_search)
    public void searchGoods() {
        IntentUtil.startActivity(GoodsDetailActivity.this, SearchActivity.class);
    }

  /*  @OnClick(R.id.search)
    public void search() {
        String cType = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT);
        searchContent = navigationSearchEdit.getText().toString();
        try {
            searchContent = URLEncoder.encode(searchContent, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String searchUrl = String.format(ConstantURL.GOODS_SEARCH_LIST, userId, searchContent,
                cType);
        url = searchUrl;
        onColligateset();
    }*/

    @OnClick(R.id.icon)
    public void OnBack() {
        finish();
    }

    @Override
    protected void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }

    @OnClick(R.id.navigation_search)
    public void navigationSearch() {
        Intent intent = new Intent(GoodsDetailActivity.this, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.car)
    public void checkCar() {
        App.getBusInstance().post(new ShowCarListEvent());
        IntentUtil.startActivity(GoodsDetailActivity.this, MainActivity.class);
        finish();
    }

    @OnClick(R.id.main_fab)
    public void onOnClickCart() {
        App.getBusInstance().post(new ShowCarListEvent());
        IntentUtil.startActivity(GoodsDetailActivity.this, MainActivity.class);
        finish();
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

    @Override
    public void referenceBadge() {
        int total = 0;
        List<ShoppingCar> list = new Select()
                .from(ShoppingCar.class)
                .execute();

        for (int i = 0; i < list.size(); i++) {
            total += Integer.parseInt(list.get(i).goodsNumber);
        }

        if (total == 0) {
            mBadgeView.hide();
        } else {
            if (total > 99) {
                mBadgeView.setText("99+");
            } else {
                mBadgeView.setText(total + "");
            }
            mBadgeView.show();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
