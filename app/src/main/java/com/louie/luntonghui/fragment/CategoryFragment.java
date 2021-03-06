package com.louie.luntonghui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.CategoryHomeAdapter;
import com.louie.luntonghui.adapter.GoodsDetailAdapter;
import com.louie.luntonghui.model.result.GoodsList;
import com.louie.luntonghui.ui.search.SearchActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;

import static com.louie.luntonghui.App.CITYID;
import static com.louie.luntonghui.App.DEFAULT_CITYID;
import static com.louie.luntonghui.ui.register.RegisterLogin.USER_DEFAULT;
import static com.louie.luntonghui.ui.register.RegisterLogin.USER_TYPE;
import static com.louie.luntonghui.ui.register.RegisterLogin.USER_WHOLESALER;

/**
 * Created by Louie on 2015/5/28.
 */
public class CategoryFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    @InjectView(R.id.icon)
    ImageView icon;
    @InjectView(R.id.navigation_search)
    TextView navigationSearch;
    @InjectView(R.id.goods_list)
    ListView goodsList;
    @InjectView(R.id.goods_detail)
    ListView goodsDetail;
    @InjectView(R.id.cancel)
    TextView cancel;

    @InjectView(R.id.logo_anim)
    ImageView logoAnim;

    @InjectView(R.id.list_whole)
    LinearLayout listWhole;

    @InjectView(R.id.logo_anim_whole)
    RelativeLayout logoWhole;

    private ArrayAdapter mListAdapter;

    private ListView mGoodList;
    private ListView mGoodDetail;
    private GoodsDetailAdapter mDetailAdapter;
    private List<String> categoryList;
    private List<GoodsList.Goods_listEntity> goodsLists;
    private Context mContext;
    public static final String SCROLL_LOCATION = "scroll_location";
    public static final String SCROLL_RPOS_LOCATION = "scroll_pros_location";
    private int INIT_SCROLL = 0;

    private GoodsList lists;
    public int INITCATEGORYITEM = 0;
    private CategoryHomeAdapter mHomeAdapter;
    private boolean fastQuery = false;
    private String queryContent;
    //private ProgressDialog progressDialog;
    private int notNeedUpdate = 0;
    public static final String PROVIUS_SELECTED_ITEM = "provious_selected_item";
    private int currentItem =0;
    private boolean firstRun = false;

    private AnimationDrawable animationDrawable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        //progressDialog = new ProgressDialog(mContext);
       /* mApi = createApi(ServiceManager.LunTongHuiApi.class);
        mApi.getGoodsList(new CategoryFragmentCallback(CategoryFragment.this));*/

        if (App.mGoods_list != null) {
            Log.d("...run at mGoods_list,", "-------------");


        } else {
            Log.d("...run at mGoods_lis1,", "-------------");
            /*String cityId = DefaultShared.getString(CITYID,DEFAULT_CITYID);
            String url = String.format(ConstantURL.GOODS_LIST, cityId);
            String tempArg ="";
            if(DefaultShared.getString(USER_TYPE,USER_DEFAULT ).equals(USER_WHOLESALER)) {
                tempArg="";
            }else{
                tempArg = "&is_show=01";
            }
            url = url + tempArg;
            RequestManager.addRequest(new GsonRequest(url, GoodsList.class,
                    getGoodsList(), errorListener()),this);*/
        }
    }

    public UpdateListener mUpdateListener;

    public interface UpdateListener {
        public void updateVersion();
    }

    private Response.Listener<GoodsList> getGoodsList() {
        return goodsList1 -> {

        };
    }


    protected Response.ErrorListener errorListener() {
        return error -> {
            //ToastUtil.showLongToast(getActivity(), error.getMessage());
            if(error !=null){
                if(getActivity() == null) return;
                ToastUtil.showShortToast(getActivity(),R.string.network_error);
                return;
            }
            ToastUtil.showLongToast(getActivity(), R.string.network_connect_fail);
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View contentView = inflater.inflate(R.layout.category_list, container, false);

        ButterKnife.inject(this, contentView);

        cancel.setText("");
        mGoodList = (ListView) contentView.findViewById(R.id.goods_list);
        mGoodDetail = (ListView) contentView.findViewById(R.id.goods_detail);

        mGoodList.setOnItemClickListener(this);
        mGoodDetail.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // scrollPos记录当前可见的List顶端的一行的位置
                    scrollPos = mGoodDetail.getFirstVisiblePosition();
                    if (mDetailAdapter != null) {
                        View v = mGoodDetail.getChildAt(0);
                        scrollTop = (v == null) ? 0 : v.getTop();
                        DefaultShared.putInt(SCROLL_LOCATION, scrollTop);
                        DefaultShared.putInt(SCROLL_RPOS_LOCATION, scrollPos);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        animationDrawable = (AnimationDrawable)logoAnim.getBackground();

        listWhole.setVisibility(View.GONE);
        if(animationDrawable!=null && !animationDrawable.isRunning()){
            logoWhole.setVisibility(View.VISIBLE);
            logoAnim.setVisibility(View.VISIBLE);
            animationDrawable.start();
        }
        parserArgument();

        return contentView;
    }

    private int scrollPos;
    private int scrollTop;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        firstRun = true;
        INITCATEGORYITEM = DefaultShared.getInt(Config.LAST_SELECT_CATEGORY_ITEM, currentItem);
        initValue();
    }

    private void parserArgument() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            queryContent = bundle.getString(Config.FASTQUERYARG);
            if(queryContent !=null){
                fastQuery = true;
            }
        }
    }

    private void initValue() {
        categoryList = new ArrayList<>();

        String cityId = DefaultShared.getString(CITYID, DEFAULT_CITYID);
        String userType = DefaultShared.getString(USER_TYPE, USER_DEFAULT);
        String version = Config.getCurrentVersion() + "";
        String url = String.format(ConstantURL.GOODS_LIST, cityId, userType, version,userId);

        String tempArg = "";
        if (DefaultShared.getString(USER_TYPE, USER_DEFAULT).equals(USER_WHOLESALER)) {
            tempArg = "";
        } else {
            tempArg = "&is_show=01";
        }

        url = url + tempArg;
        /*RequestManager.addRequest(new GsonRequest(url, GoodsList.class,
                getGoodsList(), errorListener()), this);*/
        String display = Config.getRenamePlace();
        AppObservable.bindFragment(this,mApi.getProductCategory(cityId,userType,version,userId,display))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryServer);
        return;
    }

    Observer<GoodsList> categoryServer = new Observer<GoodsList>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(final GoodsList goodsList) {
            TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<String>>() {
                @Override
                protected List<String> doInBackground(Object... params) {

                    List<String> categoryList = new ArrayList<>();
                    goodsLists = goodsList.goods_list;
                    if (goodsList.version_list.get(0).version != Config.NOT_NEED_UPDATE) {
                        mUpdateListener.updateVersion();
                    }
                    //App.mGoods_list = goodsLists;
                    for (int i = 0; i < goodsLists.size(); i++) {
                        categoryList.add(goodsList.goods_list.get(i).name);
                        if (fastQuery && queryContent.equals(goodsList.goods_list.get(i).id)) {
                            INITCATEGORYITEM = i;
                        }
                    }
                    return categoryList;
                }

                @Override
                protected void onPostExecute(List<String> categoryList) {
                    //super.onPostExecute(o);
                    if(animationDrawable!=null && animationDrawable.isRunning()){
                        if(logoAnim !=null)logoAnim.setVisibility(View.GONE);
                        if(logoWhole !=null)logoWhole.setVisibility(View.GONE);
                        if(animationDrawable!=null)animationDrawable.stop();
                    }

                    if(listWhole!=null)listWhole.setVisibility(View.VISIBLE);

                    try {
                        mHomeAdapter = new CategoryHomeAdapter(mContext, R.layout.simple_list_item, categoryList);
                        if(mGoodList !=null) mGoodList.setAdapter(mHomeAdapter);

                        if (INITCATEGORYITEM > categoryList.size()) INITCATEGORYITEM = 0;

                        onItemClick(null, null, INITCATEGORYITEM, 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<GoodsList.Goods_listEntity.Goods_list1Entity> goods_list1 = goodsLists.get(position).goods_list;
        currentItem = position;
        mGoodList.setSelection(position);
        mDetailAdapter = new GoodsDetailAdapter(getActivity(), goods_list1);

        mHomeAdapter.setBackground(position);
        mGoodDetail.setAdapter(mDetailAdapter);

        //for(int i = 0;i<goods_list1.size()
        /*List<Goods> goodes =
                new ArrayList<>();
        int size = 0;

        for(int i=0;i<goods_list1.size();i++){
            Goods goods = new Goods();
            goods.id = goods_list1.get(i).id;
            goods.img = goods_list1.get(i).img;
            goods.name = goods_list1.get(i).name;
            goods.url = goods_list1.get(i).url;
            goods.type = CategoryFragmentAdapter.CATEGORY_TITLE;
            for(int j = 0;j<goods_list1.get(i).goods_list.size();j++){

            }
        }*/

        /*for(int i =0;i<goods_list1.size();i++){
                goods_list1.get(i).goods_list.size();
        }*/

        //mFragmentAdapter.setData(goods_list1);

        if(firstRun){
            scrollTop = DefaultShared.getInt(SCROLL_LOCATION, INIT_SCROLL);
            scrollPos = DefaultShared.getInt(SCROLL_RPOS_LOCATION,INIT_SCROLL);
            mGoodDetail.setSelectionFromTop(scrollPos,scrollTop);
        }

        DefaultShared.putInt(Config.LAST_SELECT_CATEGORY_ITEM, currentItem);
        firstRun = false;
        mGoodList.smoothScrollToPositionFromTop(position, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.navigation_search)
    public void onSearch() {
        IntentUtil.startActivity(getActivity(), SearchActivity.class);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mUpdateListener = (UpdateListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SearchListener.");
        }
    }
}