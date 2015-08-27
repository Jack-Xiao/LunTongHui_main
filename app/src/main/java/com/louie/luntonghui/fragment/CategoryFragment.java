package com.louie.luntonghui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.CategoryHomeAdapter;
import com.louie.luntonghui.adapter.GoodsDetailAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.result.GoodsList;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.rest.ServiceManager;
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
    private ServiceManager.LunTongHuiApi mApi;
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
    private ProgressDialog progressDialog;
    private int notNeedUpdate = 0;
    public static final String PROVIUS_SELECTED_ITEM = "provious_selected_item";
    private int currentItem =0;
    private boolean firstRun = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        progressDialog = new ProgressDialog(mContext);
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
        return new Response.Listener<GoodsList>() {
            @Override
            public void onResponse(final GoodsList goodsList) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<String>>() {
                    @Override
                    protected void onPreExecute() {
                        progressDialog.show();
                    }

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
                            if (fastQuery && queryContent.equals(goodsList.goods_list.get(i).name)) {
                                INITCATEGORYITEM = i;
                            }
                        }
                        return categoryList;
                    }

                    @Override
                    protected void onPostExecute(List<String> categoryList) {
                        //super.onPostExecute(o);
                        progressDialog.dismiss();
                        try {
                            mHomeAdapter = new CategoryHomeAdapter(mContext, R.layout.simple_list_item, categoryList);
                            mGoodList.setAdapter(mHomeAdapter);

                            Log.d("initcategory_ item", INITCATEGORYITEM + "");
                            if (INITCATEGORYITEM > categoryList.size()) INITCATEGORYITEM = 0;

                            onItemClick(null, null, INITCATEGORYITEM, 0);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        };
    }


    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //ToastUtil.showLongToast(getActivity(), error.getMessage());
                if(error !=null){
                    if(getActivity() == null) return;
                    ToastUtil.showShortToast(getActivity(),R.string.network_error);
                    return;
                }
                ToastUtil.showLongToast(getActivity(), R.string.network_connect_fail);
            }
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
                    if (mDetailAdapter!= null) {
                        View v = mGoodDetail.getChildAt(0);
                        scrollTop=( v ==null)?0:v.getTop();
                        DefaultShared.putInt(SCROLL_LOCATION, scrollTop);
                        DefaultShared.putInt(SCROLL_RPOS_LOCATION,scrollPos);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
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
        }else{

            Log.d("initcategory_ item" ,INITCATEGORYITEM +"");
        }
    }

    private void initValue() {
        categoryList = new ArrayList<>();
        //goodsLists = App.mGoods_list;

        String cityId = DefaultShared.getString(CITYID, DEFAULT_CITYID);
        String userType = DefaultShared.getString(USER_TYPE, USER_DEFAULT);
        String version = Config.getCurrentVersion() + "";
        String url = String.format(ConstantURL.GOODS_LIST, cityId, userType, version);

        String tempArg = "";
        if (DefaultShared.getString(USER_TYPE, USER_DEFAULT).equals(USER_WHOLESALER)) {
            tempArg = "";
        } else {
            tempArg = "&is_show=01";
        }
        url = url + tempArg;
        RequestManager.addRequest(new GsonRequest(url, GoodsList.class,
                getGoodsList(), errorListener()), this);
        return;
        //}

       /* for (int i = 0; i < goodsLists.size(); i++) {
            categoryList.add(goodsLists.get(i).name);
            if(fastQuery && queryContent.equals(goodsLists.get(i).name)){
                INITCATEGORYITEM = i;
            }
        }*/



       /* mListAdapter = new ArrayAdapter(getActivity(),R.layout.simple_list_item,categoryList);
        mGoodList.setAdapter(mListAdapter);*/

/*
        mHomeAdapter = new CategoryHomeAdapter(mContext, R.layout.simple_list_item, categoryList);
        mGoodList.setAdapter(mHomeAdapter);

        onItemClick(null, null, INITCATEGORYITEM, 0);*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<GoodsList.Goods_listEntity.Goods_list1Entity> goods_list1 = goodsLists.get(position).goods_list;
        currentItem = position;
        mGoodList.setSelection(position);
        mDetailAdapter = new GoodsDetailAdapter(getActivity(), goods_list1);
        mHomeAdapter.setBackground(position);
        mGoodDetail.setAdapter(mDetailAdapter);
        if(firstRun){
            scrollTop = DefaultShared.getInt(SCROLL_LOCATION, INIT_SCROLL);
            scrollPos = DefaultShared.getInt(SCROLL_RPOS_LOCATION,INIT_SCROLL);
            mGoodDetail.setSelectionFromTop(scrollPos,scrollTop);
        }

        DefaultShared.putInt(Config.LAST_SELECT_CATEGORY_ITEM, currentItem);
        firstRun = false;
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