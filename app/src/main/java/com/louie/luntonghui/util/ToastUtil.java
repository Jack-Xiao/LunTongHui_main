package com.louie.luntonghui.util;

import android.content.Context;
import android.widget.Toast;

import com.louie.luntonghui.App;

public class ToastUtil {

	
	public static void showLongToast(Context context , String msg){
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void showLongToast(Context context,int msg){
		Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(Context context,int msg){
		Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
	}
	
	public static void showShortToast(Context context , String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}