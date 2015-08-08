package com.louie.luntonghui.receiver;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.WindowManager;

import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.Home.SecondKillActivity;

import java.util.List;

/**
 * Created by Jack on 15/7/29.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private Context mContext;
    public static final String ACTIVITY_NAME = "com.louie.luntonghui.ui.kill.SecondKillActivity";
    private MediaPlayer mMediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        String activityName = getCurrentActivityName(context);

        playSound();
        if(activityName != null && ACTIVITY_NAME.equals(activityName)){

            Log.d("activity name",getCurrentActivityName(context));
            return;
        }else{
            showBox(context);
        }
    }

    private String getCurrentActivityName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);


        // get the info from the currently running task
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return componentInfo.getClassName();
    }

    public void playSound() {
        try {
            mMediaPlayer = MediaPlayer.create(mContext, R.raw.wink);
            mMediaPlayer.stop();
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showBox(final Context context) {

        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        dialog.setTitle(R.string.begin_rush);
       // dialog.setIcon(R.drawable.logo_new);
        dialog.setMessage(R.string.can_rush_luntong_goods);

        dialog.setPositiveButton(R.string.go_rush_goods, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //点击后跳转到某个Activity
                Intent result = new Intent(context, SecondKillActivity.class);
                result.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(result);
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton(R.string.cancel_rush_goods, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog mDialog=dialog.create();

        mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//设定为系统级警告，关键
        mDialog.show();
    }
}
