package com.louie.luntonghui.ui.mine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.LoginEvent;
import com.louie.luntonghui.event.SaveAndModifyAddressEvent;
import com.louie.luntonghui.model.db.Address;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.ui.SecondLevelBaseActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.EncoderURL;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.widget.OnWheelChangedListener;
import com.louie.luntonghui.view.widget.WheelView;
import com.louie.luntonghui.view.widget.adapters.ArrayWheelAdapter;
import com.squareup.otto.Produce;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.louie.luntonghui.adapter.MineReceiverAddressAdapter.ADDRESSKEY;

/**
 * Created by Administrator on 2015/6/9.
 */
public class MineAdditionAddressActivity extends SecondLevelBaseActivity implements OnWheelChangedListener {
    public static final String ADDRESSDEFAULTSELECT = "1";
    public static final int REQUESTREGION =1910;
    public static final int WHEELSHOWVIEW = 5;
    public static final String SETTLE_ACCOUNT = "settle_account";
    public static final int NOT_SETTLE_ACCOUNT = 0;
    public static final int HAS_SETTLE_ACCOUNT = 1;
    private int SETTLE_ACCOUNT_VALUE = 0;

    @InjectView(R.id.consignee_value)
    EditText consigneeValue;
    @InjectView(R.id.mobile_value)
    EditText mobileValue;
/*    @InjectView(R.id.city_place_value)
    Button cityPlaceValue;*/
    @InjectView(R.id.stree_value)
    EditText streeValue;
    @InjectView(R.id.defalut_select)
    RadioButton defalutSelect;
    @InjectView(R.id.addressa_save)
    Button addressaSave;
    @InjectView(R.id.content)
    ScrollView scrollView;



    //public Map<String,String> idNList;
    public Map<String,String> nameIdList;
    private String curProvinceName;
    private String curCityName;
    private String curDistrict;
    private boolean isModify = false;

    private String userId;
    private String strConsignee;
    private String strMobileValue;
    private String strPlace;
    private String strStreet;
    private int intSelect;
    private String addressId;
    private String provinceId;
    private String cityId;
    private String districtId;
    private String initDistrictName;
    private boolean isFirstClick =true;


    private Context mContext;
    private PopupWindow popupWindow;
    //private SelectRegionListener listener;
    //private View contentView;

    private Object [] mProvinceDatas;

    public Map<String,String> idNList;

    public Map<String,List<String>> proNameMap;
    public Map<String,List<String>> cityNameMap;
    //private static PopupSelectRegionUtil instance;

    WheelView mViewProvince;

    WheelView mViewCity;

    WheelView mViewDistrict;

    @InjectView(R.id.city_place_value)
    TextView placeValue;

    @InjectView(R.id.progres)
    ProgressBar progress;


    @Override
    protected int setToolTitle() {
        return R.string.addition_receiver_address;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_receiver_address_addition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        Address address = getIntent().getParcelableExtra(ADDRESSKEY);
        idNList =((App)getApplication()).idNList;
        nameIdList = ((App) getApplication()).nameidList;
        userId = DefaultShared.getString(RegisterLogin.USERUID,"0");
        placeValue.setOnClickListener(listener);
        Bundle bundle = getIntent().getExtras();

        if(bundle !=null){
            SETTLE_ACCOUNT_VALUE = bundle.getInt(SETTLE_ACCOUNT);
        }
        if (address != null) {
            isModify = true;
            mToolbar.setTitle(R.string.address_modify);
            consigneeValue.setText(address.consignee);
            mobileValue.setText(address.phone);

            curProvinceName = idNList.get(address.province);
            curCityName = idNList.get(address.city);
            curDistrict = idNList.get(address.district);
            initDistrictName = curDistrict;
            //cityPlaceValue.setText(curProvinceName + " " + curCityName + " " + curDistrict +" ");
            String newAddress = address.address;
           /* newAddress = newAddress.replace(curCityName,"");
            newAddress.indexOf(curProvinceName,0);*/
            newAddress = newAddress.replaceFirst(curProvinceName ,"");
            newAddress = newAddress.replaceFirst(curCityName ,"");
            newAddress = newAddress.replaceFirst(curDistrict ,"");
            addressId = address.addressId;

            streeValue.setText(newAddress);
            placeValue.setText(curProvinceName+curCityName + curDistrict);

            defalutSelect.setChecked(address.defaultSelect.equals(ADDRESSDEFAULTSELECT));
         }else{
            curProvinceName = DefaultShared.getString(App.PROVINCE,"广东").replace("市","").replace("省","");
            curCityName = DefaultShared.getString(App.CITY,"广州").replace("市","");
        }
       /* cityPlaceValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           *//* PopupSelectRegionUtil.getInstance(MineAdditionAddressActivity.this)
                    .ShowView1(MineAdditionAddressActivity.this,v);*//*
                Intent intent = new Intent(MineAdditionAddressActivity.this,MineSelectRegionActivity.class);
                startActivityForResult(intent,REQUESTREGION);
            }
        });*/
        //设为默认地址
        defalutSelect.setChecked(true);
        initWheelView();
    }

    public void onSelectRegionClick(View view){
        Activity mActivity = MineAdditionAddressActivity.this;
        Intent intent = new Intent(mActivity,MineSelectRegionActivity.class);
        startActivityForResult(intent, REQUESTREGION);
    }

    private void initWheelView() {
        mContext = this;
        proNameMap = ((App)getApplication()).proNameMap;
        cityNameMap = ((App)getApplication()).cityNameMap;

        mProvinceDatas = proNameMap.keySet().toArray();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUESTREGION){

            String province = data.getStringExtra("province");
            String city = data.getStringExtra("city");
            String district = data.getStringExtra("district");
        }
    }


    @OnClick(R.id.addressa_save)
    public void onSaveAddressClick(View view) {
        strConsignee = EncoderURL.encode(consigneeValue.getText().toString().trim());
        if(String.valueOf(strConsignee).equals("")){
            ToastUtil.showShortToast(mContext,R.string.consign_person_not_empty);
            return ;
        }

        strMobileValue =EncoderURL.encode(mobileValue.getText().toString().trim());
        if(String.valueOf(strMobileValue).equals("")){
            ToastUtil.showShortToast(mContext,R.string.mobile_not_empty);
            return;
        }

        //strPlace = cityPlaceValue.getText().toString().replace(" ", "");
        strPlace = EncoderURL.encode(streeValue.getText().toString().replace(" ", ""));
        intSelect = defalutSelect.isChecked() ? 1 : 0 ;

        int size=new Select()
                .from(Address.class)
                .execute().size();
        if(size == 0 && intSelect==0){
            ToastUtil.showShortToast(mContext,R.string.select_default_address);
            return;
        }


        provinceId = nameIdList.get(curProvinceName);
        cityId = nameIdList.get(curCityName);
        districtId =nameIdList.get(curDistrict);
        String url;
        if(isModify){
            url = String.format(ConstantURL.MODIFYADDRESS,userId,addressId,
                            strPlace,strConsignee,strMobileValue,
                            provinceId,cityId,districtId,intSelect);
        }else{
            url = String.format(ConstantURL.ADDADDRESS,userId,
                            strPlace,strConsignee,strMobileValue,
                            provinceId,cityId,districtId,intSelect);
        }
        executeRequest(new GsonRequest(url, Result.class, addOrModifyAddress(), errorListener()));
    }


    private Response.Listener<Result> addOrModifyAddress() {
        return new Response.Listener<Result>() {

            @Override
            public void onResponse(Result result) {
                if(result.rsgcode.equals(SUCCESSCODE)){
                    TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {

                        @Override
                        protected void onPreExecute() {
                            progress.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                        }

                        @Override
                        protected Object doInBackground(Object... params) {
                            /*if(isModify){
                                new Update(Address.class)
                                        .set("consignee = ?," +
                                                "mobile = ?," +
                                                "default_select = ?," +
                                                "address = ?," +
                                                "province = ?," +
                                                "city = ?," +
                                                "district = ?",
                                                     strConsignee,
                                                    strMobileValue,
                                                    defalutSelect.isChecked() ? "1" : "0",
                                                    strPlace,
                                                    provinceId,
                                                    cityId,
                                                    districtId)
                                        .where("address_id = ? and uid = ?", addressId, userId)
                                        .execute();

                            }else{
                                Address address = new Address();
                                address.uid = userId;
                                address.address = strPlace;
                                address.consignee = strConsignee;
                                address.phone = strMobileValue;
                                address.province = provinceId;
                                address.city = cityId;
                                address.district = districtId;
                                address.defaultSelect = defalutSelect.isChecked() ? "1" : "0";
                                address.save();
                            }*/
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            progress.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    });
                }else{
                    ToastUtil.showLongToast(MineAdditionAddressActivity.this, result.rsgmsg);
                }
                App.getBusInstance().post(saveAndModifyAddressEvent());
                MineAdditionAddressActivity.this.finish();
            }
        };
    }

    @Produce
    public SaveAndModifyAddressEvent saveAndModifyAddressEvent(){
        return new SaveAndModifyAddressEvent();
    }



    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            List cityList = cityNameMap.get(curCityName);
            if(cityList !=null){
                curDistrict = cityList.get(newValue).toString();
            }else{
                curDistrict = "";
            }

        }
    }

 /*   *
     * 根据当前的省，更新市WheelView的信息*/

    private void updateCities() {
        try{

            int pCurrent = mViewProvince.getCurrentItem();
            curProvinceName = mProvinceDatas[pCurrent].toString();

            mViewCity.setViewAdapter(new ArrayWheelAdapter<Object>(mContext, proNameMap.get(curProvinceName).toArray()));
            mViewCity.setCurrentItem(0);
            updateAreas();
        }catch (Exception e){
            Log.d("11111", e.getMessage());
        }
    }

/*    *
     * 根据当前的市，更新区WheelView的信息*/

    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();

        curCityName = proNameMap.get(curProvinceName).get(pCurrent);

        List<String> list = cityNameMap.get(curCityName);
        Object[] arrays ;
        if(list == null){
            arrays = new Object[]{};
            mViewDistrict.setViewAdapter(new ArrayWheelAdapter<Object>(this, arrays));
            curDistrict = "";
        }else{
            arrays  = cityNameMap.get(curCityName).toArray();
            mViewDistrict.setViewAdapter(new ArrayWheelAdapter<Object>(this, arrays));
            mViewDistrict.setCurrentItem(0);
            curDistrict = cityNameMap.get(curCityName).get(0);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        App.getBusInstance().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.getBusInstance().register(this);
    }

    private OnClickListener listener = new OnClickListener() {
            long startTime;
            long endTime;
            public View view;
            private boolean isFirst = true;

            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                view =  LayoutInflater.from(MineAdditionAddressActivity.this).inflate(R.layout.area_select, null);
                mViewProvince = (WheelView) view.findViewById(R.id.id_province);
                mViewCity = (WheelView) view.findViewById(R.id.id_city);
                mViewDistrict = (WheelView) view.findViewById(R.id.id_district);

                 initSelect();
                isFirst = false;
                AlertDialog.Builder builder1= new android.app.AlertDialog.Builder(MineAdditionAddressActivity.this)
                        .setTitle("区域选择")
                        .setView(view)
                        .setCancelable(true)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                placeValue.setText(curProvinceName + curCityName + curDistrict);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder1.show();
                endTime = System.currentTimeMillis();
                Log.d("Time....2", (endTime - startTime) + "ms");
        }
    };

    void initSelect(){
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<Object>(MineAdditionAddressActivity.this,
                mProvinceDatas));


        // 设置可见条目数量
        mViewProvince.setVisibleItems(WHEELSHOWVIEW);
        mViewCity.setVisibleItems(WHEELSHOWVIEW);
        mViewDistrict.setVisibleItems(WHEELSHOWVIEW);

        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);


        initRegion();
       /* updateCities();
        updateAreas();*/
    }

    private void initRegion() {
        for(int i = 0 ; i <mProvinceDatas.length ; i ++){
            if(curProvinceName.equals(mProvinceDatas[i].toString())){
                mViewProvince.setCurrentItem(i);
                break;
            }
        }

        mViewCity.setViewAdapter(new ArrayWheelAdapter<Object>(mContext, proNameMap.get(curProvinceName).toArray()));
        Object [] mCityData = proNameMap.get(curProvinceName).toArray();
        for(int j =0;j<mCityData.length ; j++){
            if(curCityName.equals(mCityData[j].toString())){
                mViewCity.setCurrentItem(j);
                break;
            }
        }


        if(isFirstClick) curDistrict = initDistrictName;
        if(isModify){
            if(curDistrict != ""){
                List<String> list = cityNameMap.get(curCityName);
                Object[] arrays ;
                if(list == null){
                    arrays = new Object[]{};
                    mViewDistrict.setViewAdapter(new ArrayWheelAdapter<Object>(this, arrays));
                    curDistrict = "";
                }else{
                    arrays  = cityNameMap.get(curCityName).toArray();
                    mViewDistrict.setViewAdapter(new ArrayWheelAdapter<Object>(this, arrays));
                    for(int z =0; z<arrays.length;z++){
                        if(curDistrict.equals(arrays[z].toString())){
                            mViewDistrict.setCurrentItem(z);
                            break;
                        }
                    }
                }
            }

        }else{
            List<String> list = cityNameMap.get(curCityName);
            Object[] arrays ;
            if(list == null){
                arrays = new Object[]{};
                mViewDistrict.setViewAdapter(new ArrayWheelAdapter<Object>(this, arrays));
                curDistrict = "";
            }else{
                arrays  = cityNameMap.get(curCityName).toArray();
                mViewDistrict.setViewAdapter(new ArrayWheelAdapter<Object>(this, arrays));
                mViewDistrict.setCurrentItem(0);
                curDistrict = cityNameMap.get(curCityName).get(0);
            }
        }
    }
}
