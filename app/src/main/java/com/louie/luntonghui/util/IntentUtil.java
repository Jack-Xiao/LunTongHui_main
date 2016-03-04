package com.louie.luntonghui.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.louie.luntonghui.R;

import org.apache.http.message.BasicNameValuePair;

/**
  * @author xuxd
 *
 */
public class IntentUtil {


	/**
 	 * @param activity
	 * @param cls
	 */
	public static void startActivity(Activity activity,Class<?> cls){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.activity_push_left_in, R.anim.activity_push_left_out);
	}
	/*public static void startActivity(Context activity, Class<?> cls){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.activity_push_left_in, R.anim.activity_push_left_out);
	}*/

	/**
 	 * @param activity
	 * @param cls
	 */
	public static void startActivityFromMain(Activity activity,Class<?> cls){
		Intent intent=new Intent();
		intent.setClass(activity, cls);
		activity.startActivity(intent);
	}
	/**
 	 * @param activity
	 * @param cls
	 * @param
	 */
	public static void startActivity(Activity activity,Class<?> cls,Bundle bundle){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.activity_push_left_in, R.anim.activity_push_left_out);
	}

	public static void startActivityFromMain(Activity activity,Class<?> cls,Bundle bundle){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.activity_push_left_in, R.anim.activity_push_left_out);
	}
	public static void startActivityFromMain1(Activity activity,Class<?> cls,Bundle bundle){
		Intent intent=new Intent();
		intent.setClass(activity, cls);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		//activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	/**
 	 * @param activity
	 * @param cls
	 * @param params
	 */
	public static void startService(Activity activity,Class<?> cls,BasicNameValuePair...params){
		Intent intent=new Intent();
		intent.setClass(activity, cls);
		for(int i=0;i<params.length;i++){
			intent.putExtra(params[i].getName(), params[i].getValue());
		}
		activity.startService(intent);
	}

	public static void startActivityToMainActivity(Activity activity,Class<?> clazz){
		Intent intent = new Intent();
		intent.setClass(activity,clazz);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		activity.startActivity(intent);
	}

	public static void startActivityToMainActivity(Activity activity,Class<?> clazz,Bundle bundle){
		Intent intent = new Intent();
		intent.setClass(activity,clazz);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtras(bundle);
		activity.startActivity(intent);
	}
	public static void startActivityWiehAlpha(Activity activity,Class<?> clazz){
		Intent intent=new Intent();
		intent.setClass(activity, clazz);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_alpha_out);
	}

	public static void startActivityWiehAlpha(Activity activity,Class<?> clazz,Bundle bundle){
		Intent intent=new Intent();
		intent.setClass(activity,clazz);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_alpha_out);
	}
}
