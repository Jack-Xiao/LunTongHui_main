package com.louie.luntonghui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.HistorySearchAdapter;
import com.louie.luntonghui.adapter.ThinkSearchAdapter1;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.HistorySearchTable;
import com.louie.luntonghui.model.db.HotSearchTable;
import com.louie.luntonghui.model.result.GoodsThinkSearchList;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyListView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import butterknife.Optional;

import static com.louie.luntonghui.ui.register.RegisterLogin.USER_DEFAULT;
import static com.louie.luntonghui.ui.register.RegisterLogin.USER_TYPE;

/**
 * Created by Administrator on 2015/7/13.
 */
public class GoodsDetailFragment extends BaseFragment {
    @InjectView(R.id.icon)
    ImageView icon;

    @InjectView(R.id.search_btn)
    TextView navigationSearchBtn;

/*  @InjectView(R.id.hot_search_list)
    LinearLayout hotSearchList;*/

    @InjectView(R.id.history_search_listview)
    MyListView historySearchListview;

    @InjectView(R.id.history_search_content)
    LinearLayout historySearchContent;

    @InjectView(R.id.clear_history_search)
    Button clearHistorySearch;

    @InjectView(R.id.search_history_content)
    ScrollView searchHistoryContent;

    @InjectView(R.id.colligate)
    TextView colligate;

    @InjectView(R.id.sales_volume)
    TextView salesVolume;

    @InjectView(R.id.price)
    TextView price;

    @InjectView(R.id.progress)
    ProgressBar progress;

    @InjectView(R.id.listview)
    ListView listview;
    /*@InjectView(R.id.search_result_content)
    LinearLayout searchResultContent;*/
    @InjectView(R.id.hot_search_vertical)
    LinearLayout hotSearchVertical;

    @InjectView(R.id.hot_search_list_horizon)
    LinearLayout hotSearchListHorizon;
    @InjectView(R.id.scrollView_hot_search)
    HorizontalScrollView scrollViewHotSearch;

    //@InjectView(R.id.navigation_search_content)
    //AutoCompleteTextView navigationSearch;

    private List<HotSearchTable> mHotList;
    private Context mContext;
    private HistorySearchAdapter mSearchAdapter;
    private category curState;
    private ThinkSearchAdapter1 mThinkSearchAdapter;
    public SearchGoodsListener mListener;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public enum category {
        category, search
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        String[] test = getResources().getStringArray(R.array.mine_list_b);

        //
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.activity_search, container,false);
        ButterKnife.inject(this, contentView);

        //navigationSearch = (AutoCompleteTextView) contentView.findViewById(R.id.navigation_search);

        mThinkSearchAdapter = new ThinkSearchAdapter1(mContext, R.layout.goods_think_search_list_item);
        //navigationSearch.setAdapter(mThinkSearchAdapter);

        initSearch();

        return contentView;

    }


    public Response.Listener<GoodsThinkSearchList> thinkSearch() {
        return new Response.Listener<GoodsThinkSearchList>() {
            @Override
            public void onResponse(GoodsThinkSearchList goodsThinkSearchList) {
                //navigationSearch.setAdapter(mThinkSearchAdapter);
                List<String> list = new ArrayList<>();
                for (GoodsThinkSearchList.ListallcatEntity entity : goodsThinkSearchList.listallcat) {
                    list.add(entity.goods_name);
                }
                Log.d("goods_name111", list.size() + "");

                mThinkSearchAdapter.setData(list);
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
                                                   btn.setTextSize(12);
                                                   btn.setMaxLines(1);
                                                   btn.setLayoutParams(btnParams);
                                                   btn.setText(mHotList.get(j).hotSearchChar.toString());
                                                   btn.setTextColor(getResources().getColor(R.color.useful_grey));
                                                   btn.setBackgroundResource(R.drawable.hot_search_btn);
                                                   hotSearchListHorizon.addView(btn);
                                               }

                                               /*historySearchListview.setAdapter(new ArrayAdapter(mContext,
                                                       R.layout.search_list_item, historyList));*/

                                               mSearchAdapter = new HistorySearchAdapter(getActivity(), R.layout.search_list_item, historyList);

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
                                                           btn.setLayoutParams(btnParams);
                                                           btn.setTextSize(12);
                                                           btn.setText(mHotList.get(i).hotSearchChar.toString());
                                                           btn.setTextColor(getResources().getColor(R.color.useful_grey));
                                                           btn.setBackgroundResource(R.drawable.hot_search_btn);
                                                           tempLinearLayout1.addView(btn);

                                                       } else if (i < 8) {
                                                           Button btn = new Button(mContext);
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
        /*if (navigationSearch.getText().toString().equals("")) {
            ToastUtil.showShortToast(mContext, R.string.please_input_search_content);
            return;
        }

        //searchHistoryContent.setVisibility(View.GONE);
        icon.setImageResource(R.drawable.actionbar_back);

        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, String>() {
            @Override
            protected String doInBackground(Object... params) {
                HistorySearchTable search = new HistorySearchTable();
                search.searchName = navigationSearch.getText().toString();
                search.save();
                return navigationSearch.getText().toString();
            }

            @Override
            protected void onPostExecute(String serachContent) {
                clearHistorySearch.setVisibility(View.VISIBLE);
                historySearchContent.setVisibility(View.VISIBLE);

                mSearchAdapter.insertData(serachContent);
                mListener.search(serachContent);
            }
        });*/
    }

    @Optional
    @OnTextChanged(R.id.navigation_search_content)
    public void afterTextChanged(SpannableStringBuilder text) {
        String curSearchContent = text.toString().trim();
        if (curSearchContent.length() == 0) return;
        String ctype = DefaultShared.getString(USER_TYPE, USER_DEFAULT);

        try {
            curSearchContent = URLEncoder.encode(curSearchContent, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(ConstantURL.GOODS_THINK_LIST, curSearchContent, ctype);
        RequestManager.addRequest(new GsonRequest(url, GoodsThinkSearchList.class,
                        thinkSearch(),
                        errorListener()), this);
    }

    public interface SearchGoodsListener {
        public void search(String goodsName);

        public void goBackHome();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SearchGoodsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @OnClick(R.id.clear_history_search)
    public void OnClearSearch() {
        mSearchAdapter.clearData();
    }

    @OnClick(R.id.icon)
    public void OnBackClick() {
        mListener.goBackHome();
    }

    @Optional
    @OnItemClick(R.id.navigation_search_content)
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        String curSearchContent = mSearchAdapter.getItem(position).toString();
        mListener.search(curSearchContent);
    }
}
