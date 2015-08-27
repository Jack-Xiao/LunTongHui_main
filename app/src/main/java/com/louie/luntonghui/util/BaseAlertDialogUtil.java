package com.louie.luntonghui.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.louie.luntonghui.App;
import com.louie.luntonghui.R;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Administrator on 2015/7/20.
 */
public class BaseAlertDialogUtil {
    public BaseAlertDialogListener listener;
    private static Context mContext;

    private static BaseAlertDialogUtil mInstance;
    private String message;
    private String positiveContent;
    private String negativeContent;
    private boolean canceledOnTouchOutside = true;

    public interface BaseAlertDialogListener{
        public void confirm();
    }

    public static BaseAlertDialogUtil getInstance(){
        if(mInstance == null){
            mInstance = new BaseAlertDialogUtil();
        }
        return mInstance;
    }

    public BaseAlertDialogUtil setMessage(String message){
        this.message = message;
        return mInstance;
    }

    public BaseAlertDialogUtil setMessage(int message){
        this.message = App.getContext().getResources().getString(message);
        return mInstance;
    }

    public BaseAlertDialogUtil setPositiveContent(String positiveContent){
        this.positiveContent = positiveContent;
        return mInstance;
    }

    public BaseAlertDialogUtil setNegativeContent(String negativeContent){
        this.negativeContent = negativeContent;
        return mInstance;
    }


    public BaseAlertDialogUtil setPositiveContent(int positiveContent){
        this.positiveContent = App.getContext().getResources().getString(positiveContent);
        return mInstance;
    }

    public BaseAlertDialogUtil setNegativeContent(int negativeContent){
        this.negativeContent = App.getContext().getResources().getString(negativeContent);;
        return mInstance;
    }



    public BaseAlertDialogUtil setCanceledOnTouchOutside(boolean outsideCancel){
        this.canceledOnTouchOutside = outsideCancel;
        return mInstance;
    }


    public void show(Context context, final BaseAlertDialogListener listener){
        this.mContext = context;
        this.listener = listener;
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_base_alertdialog, null);
        TextView tvMessage = (TextView)contentView.findViewById(R.id.message);
        Button positive = (Button)contentView.findViewById(R.id.positive);
        Button negative= (Button)contentView.findViewById(R.id.negative);

        tvMessage.setText(message);
        positive.setText(positiveContent);
        negative.setText(negativeContent);

        final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
        mMaterialDialog.setView(contentView)
                .setCanceledOnTouchOutside(canceledOnTouchOutside);

        mMaterialDialog.show();

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
                listener.confirm();
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
