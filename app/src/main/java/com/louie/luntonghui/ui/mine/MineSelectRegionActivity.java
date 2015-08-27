package com.louie.luntonghui.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.SecondLevelBaseActivity;
import com.louie.luntonghui.view.widget.OnWheelChangedListener;
import com.louie.luntonghui.view.widget.WheelView;
import com.louie.luntonghui.view.widget.adapters.ArrayWheelAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/6/15.
 */
public class MineSelectRegionActivity extends SecondLevelBaseActivity implements OnWheelChangedListener{
    public static final int RESULTADDRESS = 12;
    private Context mContext;
    private PopupWindow popupWindow;
    //private SelectRegionListener listener;
    //private View contentView;

    private String mCurrentProvinceName;
    private String mCurrentDistrictName;
    private String mCurrentCityName;

    private Object [] mProvinceDatas;

    public Map<String,String> idNList;

    public Map<String,List<String>> proNameMap;
    public Map<String,List<String>> cityNameMap;


    @InjectView(R.id.id_province)
    WheelView mViewProvince;

    @InjectView(R.id.id_city)
    WheelView mViewCity;

    @InjectView(R.id.id_district)
    WheelView mViewDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        initWheelView();
    }

    private void initWheelView() {
        mContext = this;
        proNameMap = ((App)getApplication()).proNameMap;
        cityNameMap = ((App)getApplication()).cityNameMap;

        mProvinceDatas = proNameMap.keySet().toArray();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<Object>(MineSelectRegionActivity.this,
                proNameMap.keySet().toArray()));


        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);

        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);

        updateCities();
        updateAreas();

    }

    @Override
    protected int setToolTitle() {
        return R.string.select_region;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_select_region;
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {

            mCurrentDistrictName = cityNameMap.get(mCurrentCityName).get(newValue);
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        try{

            int pCurrent = mViewProvince.getCurrentItem();
            mCurrentProvinceName = mProvinceDatas[pCurrent].toString();

            mViewCity.setViewAdapter(new ArrayWheelAdapter<Object>(mContext, proNameMap.get(mCurrentProvinceName).toArray()));
            mViewCity.setCurrentItem(0);
            updateAreas();
        }catch (Exception e){
            Log.d("11111", e.getMessage());
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();

        mCurrentCityName = proNameMap.get(mCurrentProvinceName).get(pCurrent);

        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<Object>(mContext,cityNameMap.get(mCurrentCityName).toArray()));
        mViewDistrict.setCurrentItem(0);
    }

    public void onSaveClick(View view){
        Intent intent = new Intent();
        intent.putExtra("province",mViewProvince.toString());
        intent.putExtra("city",mViewCity.toString());
        intent.putExtra("district",mViewDistrict.toString());
        setResult(RESULTADDRESS, intent);
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
}
