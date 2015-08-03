package com.louie.luntonghui.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.ui.mine.MineReceiverAddressActivity;
import com.louie.luntonghui.view.widget.OnWheelChangedListener;
import com.louie.luntonghui.view.widget.WheelView;
import com.louie.luntonghui.view.widget.adapters.ArrayWheelAdapter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.InjectView;

/**
 * Created by Administrator on 2015/6/12.
 */
public class PopupSelectRegionUtil implements OnWheelChangedListener {
     WheelView mViewProvince;
     WheelView mViewCity;
     WheelView mViewDistrict;

    private Context mContext;
    private PopupWindow popupWindow;
    private SelectRegionListener listener;
    private View contentView;

    private String mCurrentProvinceName;
    private String mCurrentDistrictName;
    private String mCurrentCityName;

    private Object [] mProvinceDatas;

    public Map<String,String> idNList;

    public Map<String,List<String>> proNameMap;
    public Map<String,List<String>> cityNameMap;
    private static PopupSelectRegionUtil instance;





    public PopupSelectRegionUtil(Activity context){
        this.mContext = context;
        proNameMap = ((App)context.getApplication()).proNameMap;
        cityNameMap = ((App)context.getApplication()).cityNameMap;

        mProvinceDatas = proNameMap.keySet().toArray();

        contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_region_select, null);

        mViewProvince =(WheelView)contentView.findViewById(R.id.id_province);
        mViewCity = (WheelView)contentView.findViewById(R.id.id_city);
        mViewDistrict = (WheelView)contentView.findViewById(R.id.id_district);


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

    }
    public synchronized static PopupSelectRegionUtil getInstance(Activity activity){
        if(instance == null){
            instance = new PopupSelectRegionUtil(activity);
        }
        return instance;
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


    public interface SelectRegionListener {
        public void onCallback(String province, String city, String district);
    }

    public void showView(View v) {
        updateCities();
        updateAreas();

        popupWindow = new PopupWindow(contentView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);

        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                listener.onCallback(mCurrentProvinceName, mCurrentCityName, mCurrentDistrictName);

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        //popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.white));
        popupWindow.showAsDropDown(v,xPos,0);
        popupWindow.showAtLocation(v, Gravity.LEFT, 0, -90);
        //popupWindow.set

    }

    public void ShowView1(Activity activity,View view){
        View v1 = LayoutInflater.from(activity).inflate(R.layout.popup_region_select, null);

        /*popupWindow = new PopupWindow(v1,
                200, RelativeLayout.LayoutParams.WRAP_CONTENT, true);*/

        popupWindow = new PopupWindow(contentView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);

        int[] location = new int[2];
        view.getLocationOnScreen(location);


        popupWindow.showAtLocation(view, Gravity.RIGHT|Gravity.TOP, 0 , location[1] + view.getHeight());
                /*   popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);

        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                listener.onCallback(mCurrentProvinceName, mCurrentCityName, mCurrentDistrictName);

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        //popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.white));
        popupWindow.showAsDropDown(view,xPos,0);
        popupWindow.showAtLocation(view, Gravity.LEFT, 0, -90);*/

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
            Log.d("11111",e.getMessage());
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
}
