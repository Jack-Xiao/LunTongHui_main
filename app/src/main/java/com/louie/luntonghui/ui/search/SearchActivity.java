package com.louie.luntonghui.ui.search;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.HistorySearchAdapter;
import com.louie.luntonghui.adapter.ThinkSearchAdapter1;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.fragment.GoodsDetailFragment;
import com.louie.luntonghui.model.db.HistorySearchTable;
import com.louie.luntonghui.model.db.HotSearchTable;
import com.louie.luntonghui.model.result.GoodsThinkSearchList;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.category.GoodsDetailActivity;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyListView;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

import static com.louie.luntonghui.ui.register.RegisterLogin.USER_DEFAULT;
import static com.louie.luntonghui.ui.register.RegisterLogin.USER_TYPE;

/**
 * Created by Administrator on 2015/7/13.
 */
public class SearchActivity extends BaseNormalActivity {

    @InjectView(R.id.icon)
    ImageView icon;
    @Optional
   /* @InjectView(R.id.navigation_search_content)
    EditText navigationSearchContent;*/

    @InjectView(R.id.hot_search_list_horizon)
    LinearLayout hotSearchListHorizon;
    @InjectView(R.id.scrollView_hot_search)
    HorizontalScrollView scrollViewHotSearch;
    @InjectView(R.id.hot_search_vertical)
    LinearLayout hotSearchVertical;
    @InjectView(R.id.history_search_listview)
    MyListView historySearchListview;
    @InjectView(R.id.history_search_content)
    LinearLayout historySearchContent;
    @InjectView(R.id.clear_history_search)
    Button clearHistorySearch;
    @InjectView(R.id.search_history_content)
    ScrollView searchHistoryContent;

    @InjectView(R.id.navigation_search1_content)
    AutoCompleteTextView navigationSearchContent;


/*    @InjectView(R.id.sales_volume)
    TextView salesVolume;
    @InjectView(R.id.price)
    TextView price;
    @InjectView(R.id.progress)
    ProgressBar progress;
    @InjectView(R.id.listview)
    ListView listview;*/
    /*@InjectView(R.id.search_result_content)
    LinearLayout searchResultContent;*/


    private List<HotSearchTable> mHotList;
    private Context mContext;
    private HistorySearchAdapter mSearchAdapter;
    private GoodsDetailFragment.category curState;
    private ThinkSearchAdapter1 mThinkSearchAdapter;
    public GoodsDetailFragment.SearchGoodsListener mListener;
    public static final String SEARCH_CONTENT = "search_content";
    public static final String SEARCH_DEFAULT_CONTENT = "";
    private String display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        mContext = this;

        navigationSearchContent.setThreshold(1);
        mThinkSearchAdapter = new ThinkSearchAdapter1(mContext, R.layout.goods_think_search_list_item);
        navigationSearchContent.setAdapter(mThinkSearchAdapter);


        initView();

        initSearch();

        historySearchListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchContent = mSearchAdapter.getItem(position).toString();
                searchGoods(searchContent);
            }
        });
    }

    private void initView() {
        display = DefaultShared.getString(App.PROVINCEID,App.DEFAULT_PROVINCEID);

        switch (display){
            case "6":
                display = "0";
                break;
            case "388":
                display = "1";
                break;
        }


        navigationSearchContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String searchContent = mThinkSearchAdapter.getItem(position).toString();
                HistorySearchTable search = new HistorySearchTable();
                search.searchName = navigationSearchContent.getText().toString();
                search.save();
                mSearchAdapter.insertData(navigationSearchContent.getText().toString());
                searchGoods(searchContent);
            }
        });

        navigationSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String curSearchContent = s.toString().trim();
                if (curSearchContent.length() == 0) return;
                String ctype = DefaultShared.getString(USER_TYPE, USER_DEFAULT);



                try {
                    curSearchContent = URLEncoder.encode(curSearchContent, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String url = String.format(ConstantURL.GOODS_THINK_LIST, curSearchContent, ctype,display,userId);
                RequestManager.addRequest(new GsonRequest(url, GoodsThinkSearchList.class,
                        thinkSearch(),
                        errorListener()), this);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public Response.Listener<GoodsThinkSearchList> thinkSearch() {
        return new Response.Listener<GoodsThinkSearchList>() {
            @Override
            public void onResponse(GoodsThinkSearchList goodsThinkSearchList) {
                List<String> list = new ArrayList<>();
                for (GoodsThinkSearchList.ListallcatEntity entity : goodsThinkSearchList.listallcat) {
                    list.add(entity.goods_name);

                    Log.d("goods_name", entity.goods_name );
                }
                List<String> abc = new ArrayList<>();

                list.addAll(abc);
                mThinkSearchAdapter.setData(list);
                /*navigationSearchContent.setAdapter(new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_dropdown_item_1line,list));*/
            }
        };
    }


    private void initSearch() {
        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<String>>() {
                                       @Override
                                       protected List<String> doInBackground(Object... params) {
                                           List<HistorySearchTable> list = new Select()
                                                   .from(HistorySearchTable.class)
                                                   .orderBy("id desc")
                                                   .execute();

                                           mHotList = new Select()
                                                   .from(HotSearchTable.class)
                                                   .execute();

                                           List<String> searchList = new ArrayList<String>();
                                           for (HistorySearchTable his : list) {
                                               if (!searchList.contains(his.searchName))
                                                   searchList.add(his.searchName);
                                           }
                                           return searchList;
                                       }

                                       @Override
                                       protected void onPostExecute(List<String> historyList) {
                                           LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                                                   LinearLayout.LayoutParams.WRAP_CONTENT, 60);
                                           btnParams.setMargins(5, 10, 5, 10);

                                           if (historyList.size() > 0) {
                                               hotSearchVertical.setVisibility(View.GONE);

                                               clearHistorySearch.setVisibility(View.VISIBLE);
                                               historySearchContent.setVisibility(View.VISIBLE);


                                              /* LinearLayout.LayoutParams verticalParam = new LinearLayout.LayoutParams(
                                                       LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
*/
/*                                               FrameLayout.LayoutParams param = new FrameLayout.LayoutParams
                                                       (LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                               hotSearchListHorizon.setLayoutParams(param);*/
                                               //hotSearchListHorizon.setLayoutParams(verticalParam);
                                               hotSearchListHorizon.setOrientation(LinearLayout.HORIZONTAL);
                                               for (int j = 0; j < mHotList.size(); j++) {
                                                   Button btn = new Button(mContext);
                                                   btn.setOnClickListener(clickListener);
                                                   btn.setTextSize(12);
                                                   btn.setMaxLines(1);
                                                   btn.setLayoutParams(btnParams);
                                                   btn.setText(mHotList.get(j).hotSearchChar.toString());
                                                   btn.setTextColor(getResources().getColor(R.color.useful_grey));
                                                   btn.setBackgroundResource(R.drawable.hot_search_btn);
                                                   hotSearchListHorizon.addView(btn);
                                               }

                                               mSearchAdapter = new HistorySearchAdapter(mContext, R.layout.search_list_item, historyList);
                                               historySearchListview.setAdapter(mSearchAdapter);
                                               mSearchAdapter.notifyDataSetChanged();

                                           } else {
                                               scrollViewHotSearch.setVisibility(View.GONE);
                                               mSearchAdapter = new HistorySearchAdapter(mContext, R.layout.search_list_item);
                                               historySearchListview.setAdapter(mSearchAdapter);
                                               clearHistorySearch.setVisibility(View.GONE);
                                               historySearchContent.setVisibility(View.GONE);
                                               //hotSearchList.addView();

                                               if (mHotList.size() > 4) {

                                                   LinearLayout.LayoutParams childLinearLayoutParam = new LinearLayout.LayoutParams(
                                                           LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                   childLinearLayoutParam.setMargins(0, 0, 0, 0);

                                                   LinearLayout.LayoutParams verticalParam = new LinearLayout.LayoutParams(
                                                           LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                                   hotSearchVertical.setLayoutParams(verticalParam);
                                                   hotSearchVertical.setOrientation(LinearLayout.VERTICAL);

                                                   LinearLayout tempLinearLayout1 = new LinearLayout(mContext);
                                                   tempLinearLayout1.setLayoutParams(childLinearLayoutParam);
                                                   tempLinearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                                                   hotSearchVertical.addView(tempLinearLayout1);

                                                   LinearLayout tempLinearLayout2 = new LinearLayout(mContext);
                                                   tempLinearLayout2.setLayoutParams(childLinearLayoutParam);
                                                   tempLinearLayout2.setOrientation(LinearLayout.HORIZONTAL);
                                                   hotSearchVertical.addView(tempLinearLayout2);
                                                   for (int i = 0; i < mHotList.size(); i++) {
                                                       if (i < 4) {
                                                           Button btn = new Button(mContext);
                                                           btn.setOnClickListener(clickListener);
                                                           btn.setLayoutParams(btnParams);
                                                           btn.setTextSize(12);
                                                           btn.setText(mHotList.get(i).hotSearchChar.toString());
                                                           btn.setTextColor(getResources().getColor(R.color.useful_grey));
                                                           btn.setBackgroundResource(R.drawable.hot_search_btn);
                                                           tempLinearLayout1.addView(btn);

                                                       } else if (i < 8) {
                                                           Button btn = new Button(mContext);
                                                           btn.setOnClickListener(clickListener);
                                                           btn.setTextSize(12);
                                                           btn.setLayoutParams(btnParams);
                                                           btn.setText(mHotList.get(i).hotSearchChar.toString());
                                                           btn.setTextColor(getResources().getColor(R.color.useful_grey));
                                                           btn.setBackgroundResource(R.drawable.hot_search_btn);
                                                           tempLinearLayout2.addView(btn);
                                                       } else {
                                                           //只取前八个热搜

                                                           break;

                                                       }
                                                   }
                                               } else {
                                                   LinearLayout.LayoutParams verticalParam = new LinearLayout.LayoutParams(
                                                           LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                                   hotSearchVertical.setLayoutParams(verticalParam);
                                                   hotSearchVertical.setOrientation(LinearLayout.HORIZONTAL);
                                                   for (int j = 0; j < mHotList.size(); j++) {
                                                       Button btn = new Button(mContext);
                                                       btn.setOnClickListener(clickListener);
                                                       btn.setTextSize(12);
                                                       btn.setLayoutParams(btnParams);
                                                       btn.setText(mHotList.get(j).hotSearchChar.toString());
                                                       btn.setTextColor(getResources().getColor(R.color.useful_grey));
                                                       btn.setBackgroundResource(R.drawable.hot_search_btn);
                                                       hotSearchVertical.addView(btn);
                                                   }
                                               }
                                           }
                                       }
                                   }
        );
    }

    @OnClick(R.id.search_btn)
    public void onHotSearch() {
        if (navigationSearchContent.getText().toString().equals("")) {
            ToastUtil.showShortToast(mContext, R.string.please_input_search_content);
            return;
        }

        //searchHistoryContent.setVisibility(View.GONE);
        //icon.setImageResource(R.drawable.actionbar_back);

        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object,  List<HistorySearchTable>>() {
            @Override
            protected  List<HistorySearchTable> doInBackground(Object... params) {
                List<HistorySearchTable> list = new Select()
                        .from(HistorySearchTable.class)
                        .execute();
                return list;
            }

            @Override
            protected void onPostExecute( List<HistorySearchTable> list) {
                String searchContent = navigationSearchContent.getText().toString().trim();

                if(!list.contains(searchContent)){
                    HistorySearchTable search = new HistorySearchTable();
                    search.searchName = searchContent;
                    search.save();
                    mSearchAdapter.insertData(searchContent);
                }

                clearHistorySearch.setVisibility(View.VISIBLE);
                historySearchContent.setVisibility(View.VISIBLE);
                searchGoods(searchContent);
            }
        });
    }

    private void searchGoods(String searchContent) {
        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_CONTENT,searchContent);
        IntentUtil.startActivity(SearchActivity.this, GoodsDetailActivity.class, bundle);
    }

    @OnClick(R.id.clear_history_search)
    public void OnClearSearch() {
        mSearchAdapter.clearData();
        clearHistorySearch.setVisibility(View.GONE);
        historySearchContent.setVisibility(View.GONE);
    }

    @OnClick(R.id.icon)
    public void OnBackClick() {

        finish();
    }

    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(v instanceof Button){
                String searchContent = ((Button) v).getText().toString();
                searchGoods(searchContent);
            }
        }
    };

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

/*    @Optional
    @OnItemClick(R.id.navigation_search1_content)
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        String curSearchContent = mSearchAdapter.getItem(position).toString();
        mListener.search(curSearchContent);
    }*/
}
