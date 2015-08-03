package com.louie.luntonghui.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.louie.luntonghui.App;
import com.louie.luntonghui.ui.register.RegisterStep1Activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/6/18.
 */
public class Config {

    public static final boolean DEBUG = true;

    public static final String RESPONSE_CACHE = "okHttp_response";

    public static final int RESPONSE_CACHE_SIZE = 10 * 1024 * 1024; // 10MB

    public static final int HTTP_CONNECT_TIMEOUT = 10 * 1000;
    public static final int HTTP_READ_TIMEOUT = 10 * 1000;

    public static final int CONNECT_SUCCESS = 200;

    public static final String CITY_ID = "city_id";
    public static final int DEFAULT_CITY_ID = 76;

    public static final String DAILY_SING_IN = "daily_sing_in";
    public static final int HAS_SING_IN = 1;
    public static final int NOT_SING_IN = 0;

    public static final String REQUEST_SUCCESSCODE = "0";

    public static final String LAST_SING_IN_TIME = "last_sing_in_time";

    public static final int SEND_SIGN_IN = 0;
    public static final int SEND_QUERY_INTEGRAL = 1;

    public static final String FASTQUERYARG = "fast_query_arg";
    public static final long CLEAR_SIGN_IN = -1;

    public static final int NEED_UPDATE = 1;
    public static final int NOT_NEED_UPDATE = 0;

    public static final int BADGEVIEW_SIZE = 10;

    public static final String DEFAULT_SECOND_KILL = "00000000";
    public static final String SECOND_KILL = "second_kill";

    public static final long HOUR = 3600;
    public static final long MINUTE = 60;

    public static final long HOUR_MILLIS = 3600 * 1000;
    public static final long MINUTE_MILLIS = 60 * 1000;
    public static final long SECOND_MILLIS = 1000;

    public static final String SECONDVKILLGOODS = "1";

    // -1
    public static final String RUSH_GOODS_NOT_BEGIN = "0";
    public static final String RUSH_GOODS_BEGINNING = "1";
    public static final String RUSH_GOODS_FINISH = "2";

    public static final String CAN_SET_ALARM_CLOCK = "3";
    public static final String CAN_NOT_SET_ALARM_CLOCK = "4";

    public static final String LAST_SELECT_CATEGORY_ITEM ="last_select_category_item";
    public static final int INIT_LAST_SELECT_CATEGORY_ITEM = 0;
    //rememeber this format " xx:xx:xx"
    public static final String TODAY_RUSH_GOODS_TIME = " 10:00:00";
    public static final String normalFormatter = "yyyy-MM-dd HH:mm:ss";
    public static final String onlyDateFormatter = "yyyy-MM-dd";


    public static int getCurrentVersion() {
        int curVersionNumber = 0;

        try {
            PackageManager pm = App.getContext().getPackageManager();
            PackageInfo info = pm.getPackageInfo(App.getContext().getPackageName(), 0);
            curVersionNumber = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            curVersionNumber = 0;
        }
        return curVersionNumber;
    }

    public static String getCurrentName() {
        String curVersionName;
        try {
            PackageManager pm = App.getContext().getPackageManager();
            PackageInfo info = pm.getPackageInfo(App.getContext().getPackageName(), 0);
            curVersionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            curVersionName = "0.0";
        }
        return curVersionName;
    }

    public static boolean isSignIn() {
        long lastSingInTime=0;
        long adjustMillisecond=0;
        try {
            lastSingInTime = DefaultShared.getLong(Config.LAST_SING_IN_TIME, -1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());
            String str = format.format(curDate);
            String tomorrowZeroTime = str + " 00:00:00";
            SimpleDateFormat detailFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = null;

            date = detailFormat.parse(tomorrowZeroTime);

            adjustMillisecond = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (lastSingInTime > adjustMillisecond);
    }
    public static boolean needClearRegisterTime() {
        long lastSingInTime=0;
        long adjustMillisecond=0;
        try {
            lastSingInTime = DefaultShared.getLong(RegisterStep1Activity.REGISTER_TIME, -1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());
            String str = format.format(curDate);
            String tomorrowZeroTime = str + " 00:00:00";
            SimpleDateFormat detailFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = null;

            date = detailFormat.parse(tomorrowZeroTime);

            adjustMillisecond = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (adjustMillisecond >lastSingInTime);
    }


    //秒杀结束时间
    public static final long getTodayEndSecondKillTime(){
        long adjustMillisecond = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = format.format(curDate);
        String todayEndTime = str + TODAY_RUSH_GOODS_TIME;
        SimpleDateFormat detailFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
           date= detailFormat.parse(todayEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adjustMillisecond = date.getTime() + HOUR_MILLIS;
        return adjustMillisecond;
    }

    //秒杀开始时间
    public static long getTodayBeginSecondKillTime() {
        long adjustMillisecond = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = format.format(curDate);
        String todayBeginTime = str + TODAY_RUSH_GOODS_TIME;
        SimpleDateFormat detailFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date= detailFormat.parse(todayBeginTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        adjustMillisecond = date.getTime() ;
        return adjustMillisecond;
    }

    public static final long getTodayAlarmClockTime(){
        long adjustMillisecond = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = format.format(curDate);
        String todayBeginTime = str + TODAY_RUSH_GOODS_TIME;
        SimpleDateFormat detailFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date= detailFormat.parse(todayBeginTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        adjustMillisecond = date.getTime() - HOUR_MILLIS;
        return adjustMillisecond;
    }

    public static final long getNeedReceiverTime(){
        long currentMill = getTodayBeginSecondKillTime();
        long result = currentMill - System.currentTimeMillis() - MINUTE_MILLIS;
        return result;
    }

    public static final long getTimeMill(String time){
        SimpleDateFormat format = new SimpleDateFormat(normalFormatter);
        Date date = null;
        try{
            date = format.parse(time);
        }catch (ParseException e){
            date = new Date(System.currentTimeMillis());
        }
        return date.getTime();
    }

    public static String getRushTimeValue(String startTime) {
        SimpleDateFormat format = new SimpleDateFormat(normalFormatter);
        String value = "00:00";
        try {
            Date date = format.parse(startTime);

            SimpleDateFormat useFormat = new SimpleDateFormat("MM-dd日 HH:mm");
            value = useFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  value;
    }
    public static String getCurrentWeekModay(){
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        SimpleDateFormat format = new SimpleDateFormat(onlyDateFormatter);
        String result = format.format(cal.getTime()) + " 00:00:00";
        return result;
    }
}
