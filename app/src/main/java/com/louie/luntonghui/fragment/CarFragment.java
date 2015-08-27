package com.louie.luntonghui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.VolleyError;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.CarFragmentAdapter;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.CategoryGoAroundEvent;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.AddressList;
import com.louie.luntonghui.model.result.CarList;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.car.ProduceOrderActivity;
import com.louie.luntonghui.ui.mine.MineAdditionAddressActivity;
import com.louie.luntonghui.ui.mine.MineAttentionActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.ItemDivider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observer;

import static com.louie.luntonghui.ui.register.RegisterLogin.USERUID;

/**
 * Created by Louie on 2015/5/28.
 */
public class CarFragment extends BaseFragment implements CarFragmentAdapter.ReferenceList {
    public static final String BROADCASE_REFEREN_CAR_LIST = "broadcase_reference_car_list";
    @Optional
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Optional
    @InjectView(R.id.goods_total)
    TextView goodsTotoal;

    TextView mPlaying;

    //ProgressBar progress;
    @Optional
    @InjectView(R.id.checked)
    CheckBox checked;

    @Optional
    @InjectView(R.id.goods_list_name)
    TextView goodsListName;

    @Optional
    @InjectView(R.id.settle)
    RelativeLayout settle;

    @Optional
    @InjectView(R.id.go_around)
    Button goAround;

    @Optional
    @InjectView(R.id.mine_attention)
    Button mineAttention;

    @Optional
    @InjectView(R.id.playing)
    TextView playing;

    @Optional
    @InjectView(R.id.buy_again)
    TextView buyAgain;

    @Optional
    @InjectView(R.id.progress)
    ProgressBar progress;

    @Optional
    @InjectView(R.id.img_empty_car)
    ImageView imgEmptyCar;

    @Optional
    @InjectView(R.id.tv_empty_Car)
    TextView tvEmptyCar;

    private CarFragmentAdapter mAdapter;
    private Context mContext;
    String userId;
    private boolean saveCheckBox = false;
    private Map<String, String> checkStateList;
    private boolean isFirstRun = true;
    public static final String INITCHECKED = "1";
    private List<String> goodsIdList;
    private View emptyView;
    private ProgressDialog mProgressDialog;
    private boolean isInsertSuccess;

    public OnReferenCartListener mCartListener;
    private String dbOperFial ="";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setHasOptionsMenu(true);
        mContext = getActivity();
        mProgressDialog = new ProgressDialog(mContext);
        //getActivity().getActionBar().setTitle(R.string.shopping_car);
        userId = DefaultShared.getString(USERUID, RegisterLogin.DEFAULT_USER_ID);
        App.getBusInstance().register(this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shopping_car, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.clear)
                        .setMessage(R.string.clear_shopping_car_message)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!userId.equals(RegisterLogin.DEFAULT_USER_ID)) {
                                    clearCar();
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(true)
                        .create()
                        .show();

        }
        return true;
    }

    Observer<Result> observer = new Observer<Result>() {

        @Override
        public void onCompleted() {
            Log.d("observer", "completed..");
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(mContext, e.getMessage());
        }

        @Override
        public void onNext(Result result) {
            Log.d("observer", "onNext..");
            if (result.rsgcode.equals(ConstantURL.SUCCESSCODE)) {
                mAdapter.clearData();
                mAdapter.notifyDataSetChanged();
            } else {
                ToastUtil.showShortToast(mContext, result.rsgmsg);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View contentView = inflater.inflate(R.layout.fragment_car_list, container, false);

        userId = DefaultShared.getString(USERUID, RegisterLogin.DEFAULT_USER_ID);

        if (userId.equals(RegisterLogin.DEFAULT_USER_ID)) {
//            ToastUtil.showShortToast(mContext, R.string.login_remind);

            contentView = inflater.inflate(R.layout.fragment_car_empty, container, false);
            ButterKnife.inject(this, contentView);
            return contentView;
        }

        ButterKnife.inject(this, contentView);
        emptyView = contentView.findViewById(R.id.empty_view);

        mPlaying = (TextView) contentView.findViewById(R.id.playing);
        progress = (ProgressBar) contentView.findViewById(R.id.progress);
        //createApi(ServiceManager.LunTongHuiApi.class).getCarList(uid, new CarListCallback(this));

        mAdapter = new CarFragmentAdapter(mContext, this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.addItemDecoration(new RecyclerViewLinearLayoutViewItemDecoration(mContext, HORIZONTAL_LIST));
        mRecyclerView.addItemDecoration(new ItemDivider(getActivity(), R.drawable.recyclerview_break_line));

        mRecyclerView.setAdapter(mAdapter);

        checked.setChecked(true);
        checked.setOnCheckedChangeListener(new CheckChaneListener());
        mPlaying.setOnClickListener(playClickListener);

        //reference();
       /* CarList carList = mApi.getCarList(userId);
        if(carList.goods_list.size() == 0){
            contentView = inflater.inflate(R.layout.fragment_car_empty,container,false);
            LinearLayout layout = (LinearLayout) contentView.findViewById(R.id.login_info);
            layout.setVisibility(View.GONE);
            return contentView;
        }else{
            fillCarList(carList);
        }*/

        //mApi.getCarList(userId, new CarListCallback(this));

        progress.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);


        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getCarList();
    }

    public void getCarList(){
        String url = String.format(ConstantURL.GET_CAR_LIST, userId);
        RequestManager.addRequest(new GsonRequest(url, CarList.class, getCarListListener(), errorListener()), this);
    }

    public com.android.volley.Response.Listener<CarList> getCarListListener() {
        return new com.android.volley.Response.Listener<CarList>() {
            @Override
            public void onResponse(final CarList carList) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<ShoppingCar>>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        if(progress !=null) progress.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected List<ShoppingCar> doInBackground(Object... params) {
                        goodsIdList = new ArrayList<>();
                        new Delete()
                                .from(ShoppingCar.class)
                                .execute();
                        List<ShoppingCar> data = new ArrayList<ShoppingCar>();
                        if(carList !=null && carList.goods_list !=null) {
                            try {
                                ActiveAndroid.beginTransaction();
                                for (int i = 0; i < carList.goods_list.size(); i++) {
                                    ShoppingCar car = new ShoppingCar();
                                    car.carId = carList.goods_list.get(i).rec_id;
                                    car.isChecked = INITCHECKED;
                                    car.goodsShopPrice = carList.goods_list.get(i).goods_price;
                                    car.goodsImage = carList.goods_list.get(i).goods_img;
                                    car.goodsNumber = carList.goods_list.get(i).goods_number;
                                    car.goodsId = carList.goods_list.get(i).goods_id;
                                    car.goodsName = carList.goods_list.get(i).goods_name;
                                    car.guige = carList.goods_list.get(i).guige;
                                    car.unit = carList.goods_list.get(i).danwei;
                                    car.rId = carList.goods_list.get(i).rid;
                                    car.discountType = carList.goods_list.get(i).discount_type;
                                    car.discount = carList.goods_list.get(i).discount;
                                    car.save();
                                    isInsertSuccess = true;
                                    data.add(car);
                                }
                                ActiveAndroid.setTransactionSuccessful();
                            }catch(Exception e){
                                dbOperFial = e.getStackTrace().toString();
                            }finally {
                                ActiveAndroid.endTransaction();
                            }
                        }
                        return data;
                    }

                    @Override
                    protected void onPostExecute(List<ShoppingCar> list) {
                        if (progress != null) progress.setVisibility(View.GONE);
                        postValue(list);

                        int total=0;
                        for(int i =0;i<list.size();i++){
                            if(!list.get(i).rId.equals("0")) continue;
                            total += Integer.parseInt(list.get(i).goodsNumber);
                        }

                        if(mCartListener!=null) mCartListener.referenceCart(total);

                        if(mPlaying!=null)mPlaying.setText("去结算(" + carList.total.real_goods_count + ")");
                        if(goodsTotoal!=null)goodsTotoal.setText("￥" + carList.total.goods_price);
                        if(list.size() !=0)mAdapter.setData(list);
                    }
                });
            }
        };
    }

    private void postValue(List<ShoppingCar> list) {
        if (list.size() == 0) {
            if(goodsListName !=null)goodsListName.setVisibility(View.GONE);
            if(emptyView !=null)emptyView.setVisibility(View.VISIBLE);
            if(settle!=null)settle.setVisibility(View.GONE);
            if(mRecyclerView!=null)mRecyclerView.setVisibility(View.GONE);
        } else {
            if(goodsListName!=null)goodsListName.setVisibility(View.VISIBLE);
            if(emptyView!=null)emptyView.setVisibility(View.GONE);
            if(settle!=null)settle.setVisibility(View.VISIBLE);
            if(mRecyclerView!=null)mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void fillCarList(final CarList carList) {

        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<ShoppingCar>>() {
            @Override
            protected void onPreExecute() {
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            protected List<ShoppingCar> doInBackground(Object... params) {
                goodsIdList = new ArrayList<>();
                /*Log.d("goodsIdList,", goodsIdList.size() + "  ...1");
                List<ShoppingCar> carLists= new Select()
                        .from(ShoppingCar.class)
                        .execute();
                for(int i =0;i<carLists.size();i++){
                    goodsIdList.add(carLists.get(i).goodsId);
                }*/
                //Log.d("goodsIdList,", goodsIdList.size() + "   ...2");
                new Delete()
                        .from(ShoppingCar.class)
                        .execute();

                List<ShoppingCar> data = new ArrayList<ShoppingCar>();
                try {

                    ActiveAndroid.beginTransaction();

                    for (int i = 0; i < carList.goods_list.size(); i++) {
                            ShoppingCar car = new ShoppingCar();
                            car.carId = carList.goods_list.get(i).rec_id;
                            car.isChecked = INITCHECKED;
                            car.goodsShopPrice = carList.goods_list.get(i).goods_price;
                            car.goodsImage = carList.goods_list.get(i).goods_img;
                            car.goodsNumber = carList.goods_list.get(i).goods_number;
                            car.goodsId = carList.goods_list.get(i).goods_id;
                            car.goodsName = carList.goods_list.get(i).goods_name;
                            car.guige = carList.goods_list.get(i).guige;
                            car.unit = carList.goods_list.get(i).danwei;
                            car.save();
                            data.add(car);
                    }
                    ActiveAndroid.setTransactionSuccessful();
                }finally {
                    ActiveAndroid.endTransaction();
                }
                return data;
            }

            @Override
            protected void onPostExecute(List<ShoppingCar> list) {
                if (progress != null) progress.setVisibility(View.GONE);
                if (list.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    settle.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    settle.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mPlaying.setText("去结算(" + carList.total.real_goods_count + ")");
                    goodsTotoal.setText("￥" + carList.total.goods_price);
                }
                mAdapter.setData(list);
            }
        });
    }


    class CheckChaneListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //mAdapter.setChecked(isChecked);
            if (isChecked) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected void onPreExecute() {
                        progress.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected Object doInBackground(Object... params) {
                        List<ShoppingCar> carList = new Select()
                                .from(ShoppingCar.class)
                                .where("isChecked=?", "0")
                                .execute();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                    }
                });
            } else {

                clearCar();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }


    @Override
    public void reference(int result) {
        if (result == 0) {
            reference();
        } else {
            reference();
        }
    }

    private double totalPrice;
    private BigDecimal bg;
    private int totalCount;

    public void reference() {
        getCarList();
        //final double total;
        //executeRequest(new GsonRequest());

        /*TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<ShoppingCar>>() {
            @Override
            protected List<ShoppingCar> doInBackground(Object... params) {
                double tempTotal = 0.00;
                List<ShoppingCar> list = new Select()
                        .from(ShoppingCar.class)
                        .execute();
                double curValue;
                int tempCount = 0;
                for (int i = 0; i < list.size(); i++) {
                    curValue = Integer.parseInt(list.get(i).goodsNumber) * Double.parseDouble(list.get(i).goodsShopPrice);
                    tempTotal += curValue;
                    tempCount += Integer.parseInt(list.get(i).goodsNumber);
                }
                totalPrice = tempTotal;
                totalCount = tempCount;
                return list;
            }

            @Override
            protected void onPreExecute() {
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(List<ShoppingCar> list) {
                if(progress !=null)progress.setVisibility(View.GONE);
                if(mPlaying !=null)mPlaying.setText("去结算(" + totalCount + ")");
                if(mCartListener !=null)mCartListener.referenceCart(totalCount);
                //bg = new BigDecimal(totalPrice);
                //bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
                if(goodsTotoal !=null)goodsTotoal.setText("￥" + new DecimalFormat("0.00").format(totalPrice));
                mAdapter.setData(list);
            }
        });*/
    }

    public void clearCar() {
        String url = String.format(ConstantURL.GOODS_SHOPPING_CAR_EMPTY, userId);
        //ProgressUtil.show(mContext);
        mProgressDialog.show();
        RequestManager.addRequest(new GsonRequest(url, Result.class, emptyCarListener(), errorListener()), this);
    }

    private com.android.volley.Response.Listener<Result> emptyCarListener() {
        return new com.android.volley.Response.Listener<Result>() {
            @Override
            public void onResponse(Result result) {
                if (result.rsgcode.equals(ConstantURL.SUCCESSCODE)) {
                    new Delete()
                            .from(ShoppingCar.class)
                            .execute();
                    mProgressDialog.dismiss();
                    reference();
                } else {
                    ToastUtil.showShortToast(mContext, result.rsgmsg);
                }
            }
        };
    }

    protected com.android.volley.Response.ErrorListener errorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.showLongToast(mContext, error.getMessage());
                /*Log.d("error  ....", error.getMessage());*/
            }
        };
    }

    private View.OnClickListener playClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mProgressDialog.show();
            String url = String.format(ConstantURL.ADDRESSLIST, userId);
            executeRequest(new GsonRequest(url, AddressList.class, getAddressList(), errorListener()));

            /*TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Integer>() {
                @Override
                protected Integer doInBackground(Object... params) {
                    List<Address> list = new Select()
                            .from(Address.class)
                            .execute();
                    return list.size();
                }

                @Override
                protected void onPostExecute(Integer integer) {
                    if (integer == 0) {
                        IntentUtil.startActivity(getActivity(), MineReceiverAddressActivity.class);
                        ToastUtil.showShortToast(mContext,R.string.please_create_address_at_first);
                    } else {
                        IntentUtil.startActivity(getActivity(), ProduceOrderActivity.class);
                    }
                }
            });*/
        }
    };

    public com.android.volley.Response.Listener<AddressList> getAddressList() {

        return new com.android.volley.Response.Listener<AddressList>() {
            @Override
            public void onResponse(final AddressList addressList) {
                mProgressDialog.dismiss();
                if (addressList.listallcat.size() == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(MineAdditionAddressActivity.SETTLE_ACCOUNT,
                            MineAdditionAddressActivity.HAS_SETTLE_ACCOUNT);
                    IntentUtil.startActivity(getActivity(), MineAdditionAddressActivity.class);
                } else {
                    IntentUtil.startActivity(getActivity(), ProduceOrderActivity.class);
                }
            }
        };
    }

    @Optional
    @OnClick(R.id.go_around)
    public void onClickGoAround() {
        App.getBusInstance().post(new CategoryGoAroundEvent());
    }

    @Optional
    @OnClick(R.id.mine_attention)
    public void OnClickMineAttention() {
        IntentUtil.startActivityWiehAlpha(getActivity(), MineAttentionActivity.class);
    }

    @Override
    public void onDetach() {
        RequestManager.cancelAll(this);
        super.onDetach();
    }

    @Optional
    @OnClick(R.id.car_login)
    public void onLogin() {
        IntentUtil.startActivityFromMain(getActivity(), RegisterLogin.class);
    }

    @Optional
    @OnClick(R.id.buy_again)
    public void buyAgain(){
        App.getBusInstance().post(new CategoryGoAroundEvent());
    }

    public interface OnReferenCartListener{
        public void referenceCart(int count);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCartListener = (OnReferenCartListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SearchListener.");
        }
    }
}