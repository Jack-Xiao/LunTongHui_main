package com.louie.luntonghui.ui.mine;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.MineReceiverAddressAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.SaveAndModifyAddressEvent;
import com.louie.luntonghui.model.db.Address;
import com.louie.luntonghui.model.result.AddressList;
import com.louie.luntonghui.ui.SecondLevelBaseActivity;
import com.louie.luntonghui.ui.car.ProduceOrderActivity;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.view.RecyclerItemClickListener;
import com.louie.luntonghui.view.RecyclerViewLinearLayoutViewItemDecoration;
import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.louie.luntonghui.ui.register.RegisterLogin.USERUID;
import static com.louie.luntonghui.view.RecyclerViewLinearLayoutViewItemDecoration.HORIZONTAL_LIST;

/**
 * Created by Administrator on 2015/6/9.
 */
public class MineReceiverAddressActivity extends SecondLevelBaseActivity {
    private String uid;
    public static final String ADDRESSMESSAGE = "address_count";
    public static final int HASADDRESS = 1;
    public static final int NOADDRESS = 0;
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /*    @InjectView(R.id.empty_txt)
        TextView mEmptyText;*/
    private String mUrl;
    /*@InjectView(R.id.progress)
    ProgressBar progress;*/

    @InjectView(R.id.logo_anim)
    ImageView logoAnim;

    private MineReceiverAddressAdapter mAdapter;
    private List<Address> data;
    private long startTime;
    private long endTime;
    private boolean isAddressSelect = false;
    private AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        uid = DefaultShared.getString(USERUID, "0");
        App.getBusInstance().register(this);

        animationDrawable = (AnimationDrawable)logoAnim.getBackground();

        isAddressSelect = getIntent().getBooleanExtra(ProduceOrderActivity.ADDRESS_SELECT,isAddressSelect);

        mUrl = String.format(ConstantURL.ADDRESSLIST, uid);
        //test(url);

            /*executeRequest(new JsonObjectRequest(url,new JSONObject(), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonObject) {
                    Log.d("-","");
                }
            },errorListener()));
*/
        //executeOkHttp(url);

        //executeRequest(new GsonRequest(url, AddressList.class, getAddressList(), errorListener()));
        //executeRequest();


        data = new ArrayList<Address>();

        mAdapter = new MineReceiverAddressAdapter(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewLinearLayoutViewItemDecoration(this, HORIZONTAL_LIST));

        if(isAddressSelect){
            mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    Address address = mAdapter.getAddress(position);
                    Intent intent = new Intent();
                    intent.putExtra(PROVINCE_ID,address.province);
                    intent.putExtra(CITY_ID,address.city);
                    intent.putExtra(DISTRICT_ID,address.district);
                    intent.putExtra(DETAIL_ADDRESS,address.address);
                    intent.putExtra(PHONE_NUMBER,address.phone);
                    intent.putExtra(CONSIGNER,address.consignee);
                    intent.putExtra(ADDRESS_ID,address.addressId);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }));
        }

        reference();


    }
    public static final String ADDRESS_ID = "address_id";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String CONSIGNER = "consigner";
    public static final String PROVINCE_ID = "province_id";
    public static final String CITY_ID = "city_id";
    public static final String DISTRICT_ID = "district_id";
    public static final String DETAIL_ADDRESS = "detail_address";


    public Response.Listener<AddressList> getAddressList() {

        return new Response.Listener<AddressList>() {
            @Override
            public void onResponse(final AddressList addressList) {

                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<Address>>() {

                    @Override
                    protected void onPreExecute() {
                        if(animationDrawable!=null && !animationDrawable.isRunning()){
                            if(logoAnim !=null)logoAnim.setVisibility(View.VISIBLE);
                            animationDrawable.start();
                        }
                    }

                    @Override
                    protected List<Address> doInBackground(Object... params) {
                        startTime = System.currentTimeMillis();
                        List<Address> lists = new ArrayList<Address>();
                        new Delete()
                                .from(Address.class)
                                .where("uid=?", uid)
                                .execute();
                        if (addressList.listallcat != null && addressList.listallcat.size() > 0) {

                            ActiveAndroid.beginTransaction();
                            for (AddressList.ListallcatEntity entity : addressList.listallcat) {
                                Address address = new Address();
                                address.uid = uid;
                                address.addressId = entity.address_id;
                                address.consignee = entity.consignee;
                                address.phone = entity.mobile;
                                address.defaultSelect = entity.defaultX;
                                address.address = entity.address;
                                address.province = String.valueOf(entity.province);
                                address.city = String.valueOf(entity.city);
                                address.district = String.valueOf(entity.district);
                                address.save();
                                lists.add(address);
                            }
                            ActiveAndroid.setTransactionSuccessful();
                            ActiveAndroid.endTransaction();
                        }
                        endTime = System.currentTimeMillis();
                        Log.d("Time....", (endTime - startTime) + " ms");
                        return lists;
                    }

                    @Override
                    protected void onPostExecute(List<Address> lists) {
                        //super.onPostExecute(o);
                        //progress.setVisibility(View.GONE);
                        if(animationDrawable!=null && animationDrawable.isRunning()){
                            if(logoAnim!=null)logoAnim.setVisibility(View.GONE);
                            if(animationDrawable!=null)animationDrawable.stop();
                        }

                        mAdapter.setData(lists);
                        //mRecyclerView.not
                        //notifyDataSetChanged
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
    }


    @Override
    protected int setToolTitle() {
        return R.string.mine_receiver_address;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_receiver_address;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mine_receiver_address, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ids = item.getItemId();
        switch (ids) {
            case R.id.addittion_address:
                IntentUtil.startActivity(this, MineAdditionAddressActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onAddClick(View view) {
        IntentUtil.startActivity(this, MineAdditionAddressActivity.class);
    }

    @Subscribe
    public void addAndModifyAddress(SaveAndModifyAddressEvent event) {
        reference();
    }

    private void reference() {
        executeRequest(new GsonRequest(mUrl, AddressList.class, getAddressList(), errorListener()));
    }

    @Override
    protected void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
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
