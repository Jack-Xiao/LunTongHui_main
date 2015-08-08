package com.louie.luntonghui.ui.mine.MineService;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.MineServicePeopleAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.MineServicePeopleListTable;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.MineServicePeopleListResult;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.view.MyListView;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 15/7/30.
 */
public class MineCustomerPeopleListActivity extends BaseNormalActivity {

    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;

    @InjectView(R.id.list_view)
    MyListView listView;
    @InjectView(R.id.curent_week_peoples)
    TextView curentWeekPeoples;
    @InjectView(R.id.total_peoples)
    TextView totalPeoples;
    public String currentWeekTime;
    @InjectView(R.id.customer_name)
    TextView customerName;
    @InjectView(R.id.customer_value)
    TextView customerValue;
    private SimpleDateFormat peopleListFormat;
    private SimpleDateFormat normalDateFormat;
    private MineServicePeopleAdapter mAdapter;
    private Context mContext;
    private boolean isMyWork = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_customer_people_list);
        ButterKnife.inject(this);
        mContext = this;
        toolbarTitle.setText(R.string.mine_customer_order_list);
        currentWeekTime = Config.getCurrentWeekModay();
        //peopleListFormat = new SimpleDateFormat("yy/MM/dd");
        //normalDateFormat = new SimpleDateFormat(Config.normalFormatter);
        mAdapter = new MineServicePeopleAdapter(mContext);
        listView.setAdapter(mAdapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null &&
                bundle.getString(User.IS_EMPLOYEE).equals(User.ISEMPLOYEE)){
                isMyWork = true;
        }

        queryCustomerPeopleList();

    }

    @OnClick(R.id.total_peoples)
    public void onClickTotalPeoples() {
        totalPeoples.setTextColor(getResources().getColor(R.color.order_font_choose));
        curentWeekPeoples.setTextColor(getResources().getColor(R.color.order_font_normal));
        customerName.setText(R.string.total_people_count);

        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<MineServicePeopleListTable>>() {
            @Override
            protected List<MineServicePeopleListTable> doInBackground(Object... params) {
                List<MineServicePeopleListTable> list = new Select()
                        .from(MineServicePeopleListTable.class)
                        .orderBy("register_time desc")
                        .execute();
                return list;
            }

            @Override
            protected void onPostExecute(List<MineServicePeopleListTable> list) {

                /*for(int i =0;i<list.size();i++){
                    try {
                        Date date = normalDateFormat.parse(list.get(i).registerTime);
                        String time = peopleListFormat.format(date);
                        list.get(i).registerTime = time;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }*/
                mAdapter.setData(list);
                customerValue.setText(list.size() + "");
            }
        });
    }

    @OnClick(R.id.curent_week_peoples)
    public void onClickCurrentWeekPeoples() {
        curentWeekPeoples.setTextColor(getResources().getColor(R.color.order_font_choose));
        totalPeoples.setTextColor(getResources().getColor(R.color.order_font_normal));
        customerName.setText(R.string.current_week_people_count);

        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<MineServicePeopleListTable>>() {
            @Override
            protected List<MineServicePeopleListTable> doInBackground(Object... params) {
                List<MineServicePeopleListTable> list = new Select()
                        .from(MineServicePeopleListTable.class)
                        //.where("datetime(register_time) > datetime(?)", currentWeekTime)/**/
                        .where("register_time >= ?" ,currentWeekTime)
                        .orderBy("register_time desc")
                        .execute();

                return list;
            }

            @Override
            protected void onPostExecute(List<MineServicePeopleListTable> list) {

                /*for (int i = 0; i < list.size(); i++) {
                    try {
                        Date date = normalDateFormat.parse(list.get(i).registerTime);
                        String time = peopleListFormat.format(date);
                        list.get(i).registerTime = time;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }*/
                mAdapter.setData(list);
                customerValue.setText(list.size() + "");
            }
        });
    }

    private void queryCustomerPeopleList() {
        String url;
        if(isMyWork){
            url = String.format(ConstantURL.GETUSER, userId);
        }else{
            url = String.format(ConstantURL.MINE_SERVICE_PEOPLE_LIST, userId);
        }

        executeRequest(new GsonRequest(url, MineServicePeopleListResult.class,
                peopleListRequest(), errorListener()));

    }

    private Response.Listener<MineServicePeopleListResult> peopleListRequest() {
        new Delete()
                .from(MineServicePeopleListTable.class)
                .execute();

        return new Response.Listener<MineServicePeopleListResult>() {
            @Override
            public void onResponse(final MineServicePeopleListResult mineServicePeopleListResult) {
                for (int i = 0; i < mineServicePeopleListResult.listallcat.size(); i++) {
                    MineServicePeopleListTable people = new MineServicePeopleListTable();
                    people.userId = mineServicePeopleListResult.listallcat.get(i).user_id;
                    people.userName = mineServicePeopleListResult.listallcat.get(i).user_name;
                    people.mobilePhone = mineServicePeopleListResult.listallcat.get(i).mobile_phone;
                    //Config.normalFormatter
                    try {
                        //Date date = normalDateFormat.parse(mineServicePeopleListResult.listallcat.get(i).reg_time);
                        people.registerTime = mineServicePeopleListResult.listallcat.get(i).reg_time;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //people.registerTime = mineServicePeopleListResult.listallcat.get(i).reg_time;
                    people.remark = mineServicePeopleListResult.listallcat.get(i).remark;

                    people.save();
                }
                onClickCurrentWeekPeoples();
            }
        };
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
}
