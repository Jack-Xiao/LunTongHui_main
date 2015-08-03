package com.louie.luntonghui.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.view.widget.WheelView;
import com.louie.luntonghui.view.widget.adapters.ArrayWheelAdapter;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Jack on 15/7/28.
 */
public class MyAlarmClockDialogUtil  {
    public AlarmClockListener listener;
    public static final int NONE_ALARM_CLOCK = 0;
    public static final int ONE_ALARM_CLOCK = 1;
    public static final int REPEAT_ALARM_CLOCK = 2;
    private Context mContext;
    private static String[] hours;
    private static String[] minutes;
    private  WheelView wheelViewHour;
    private WheelView wheelViewMinute;

    public static final String SET_ALARM_CLOCK_TYPE = "set_alarm_clock_type";
    public static final String SET_ALARM_CLOCK_HOUR = "set_alarm_clock_hour";
    public static final String SET_ALARM_CLOCK_MINUTE = "set_alarm_clock_minute";

    public static final String SET_HOUR_VALUE = "set_hour_value";
    public static final String SET_MINUTE_VALUE = "set_minute_value";

    public static final String DEFAULT_HOUR_VALUE ="09";
    public static final String DEFAULT_MINUTE_VALUE ="30";

    RadioButton rbNone;
    RadioButton rbOne;
    RadioButton rbRepeat;
    private int clockType ;

    public interface  AlarmClockListener{
        public void confirmAlarmColock(int type,String time);
    }

    private static MyAlarmClockDialogUtil mInstance;

    public static MyAlarmClockDialogUtil  getInstance(){
        if(mInstance == null){
            mInstance = new MyAlarmClockDialogUtil();
        }

        hours = new String[]{
            "01","02","03","04","05","06","07","08","09","10",
            "11","12","13","14","15","16","17","18","19","20",
            "21","22","23","00"

        };
        minutes = new String[]{
                "01","02","03","04","05","06","07","08","09","10",
                "11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30",
                "31","32","33","34","35","36","37","38","39","40",
                "41","42","43","44","45","46","47","48","49","50",
                "51","52","53","54","55","56","57","58","59","00",
        };

        return mInstance;
    }

    public void show(Context context, final AlarmClockListener listener){
        this.mContext = context;
        this.listener = listener;
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_set_alarm_clock_alertdialog, null);
        TextView tvMessage = (TextView)contentView.findViewById(R.id.message);
        Button positive = (Button)contentView.findViewById(R.id.positive);
        Button negative= (Button)contentView.findViewById(R.id.negative);
        wheelViewHour = (WheelView)contentView.findViewById(R.id.wv_hour);
        wheelViewMinute = (WheelView)contentView.findViewById(R.id.wv_minue);

        tvMessage.setText(R.string.set_alarm_clock_type);

        rbNone = (RadioButton)contentView.findViewById(R.id.set_alarm_none);
        rbOne = (RadioButton) contentView.findViewById(R.id.set_alarm_one);
        rbRepeat = (RadioButton)contentView.findViewById(R.id.set_alarm_repeat);

        clockType = DefaultShared.getInt(SET_ALARM_CLOCK_TYPE, NONE_ALARM_CLOCK);
        wheelViewHour.setViewAdapter(new ArrayWheelAdapter<>(mContext,hours));
        wheelViewMinute.setViewAdapter(new ArrayWheelAdapter<>(mContext,minutes));

        if(clockType != NONE_ALARM_CLOCK){
            String hourValue = DefaultShared.getString(SET_HOUR_VALUE,DEFAULT_HOUR_VALUE);
            String minuteValue = DefaultShared.getString(SET_MINUTE_VALUE,DEFAULT_MINUTE_VALUE);

            if(clockType == ONE_ALARM_CLOCK){
                rbOne.setChecked(true);
            }else if(clockType == REPEAT_ALARM_CLOCK){
                rbRepeat.setChecked(true);
            }
            for(int i =0;i<hours.length;i++){
                if(hourValue.equals(hours[i])){
                    wheelViewHour.setCurrentItem(i);
                    break;
                }
            }
            for(int j =0;j<minutes.length;j++){
                if(minuteValue.equals(minutes[j])){
                    wheelViewMinute.setCurrentItem(j);
                    break;
                }
            }
        }

        final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
        mMaterialDialog.setView(contentView)
                .setCanceledOnTouchOutside(true);

        mMaterialDialog.show();

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = NONE_ALARM_CLOCK;

                if(rbNone.isChecked()){
                    type = NONE_ALARM_CLOCK;
                }else if(rbOne.isChecked()){
                    type = ONE_ALARM_CLOCK;
                }else if(rbRepeat.isChecked()){
                    type = REPEAT_ALARM_CLOCK;
                }


                DefaultShared.putInt(SET_ALARM_CLOCK_TYPE, type);
                String hourTime = hours[wheelViewHour.getCurrentItem()];
                String minuteTime = minutes[wheelViewMinute.getCurrentItem()];

                //DefaultShared.putString(SET_HOUR_VALUE, );
                DefaultShared.putString(SET_MINUTE_VALUE,minutes[wheelViewMinute.getCurrentItem()]);
                String time = hours[wheelViewHour.getCurrentItem()] + ":" + minutes[wheelViewMinute.getCurrentItem()];
                listener.confirmAlarmColock(type,time);

            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
    }

}
