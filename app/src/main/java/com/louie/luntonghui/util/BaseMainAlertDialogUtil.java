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
public class BaseMainAlertDialogUtil {
    public BaseMainAlertDialogListener listener;
    private static Context mContext;

    private static BaseMainAlertDialogUtil mInstance;
    private String message;
    private String positiveContent;
    private String negativeContent;
    private boolean canceledOnTouchOutside = true;

    public interface BaseMainAlertDialogListener{
        public void submitConfirm1();
    }

    public static BaseMainAlertDialogUtil getInstance(){
        if(mInstance == null){
            mInstance = new BaseMainAlertDialogUtil();
        }
        return mInstance;
    }

    public BaseMainAlertDialogUtil setMessage(String message){
        this.message = message;
        return mInstance;
    }

    public BaseMainAlertDialogUtil setMessage(int message){
        this.message = App.getContext().getResources().getString(message);
        return mInstance;
    }

    public BaseMainAlertDialogUtil setPositiveContent(String positiveContent){
        this.positiveContent = positiveContent;
        return mInstance;
    }

    public BaseMainAlertDialogUtil setNegativeContent(String negativeContent){
        this.negativeContent = negativeContent;
        return mInstance;
    }


    public BaseMainAlertDialogUtil setPositiveContent(int positiveContent){
        this.positiveContent = App.getContext().getResources().getString(positiveContent);
        return mInstance;
    }

    public BaseMainAlertDialogUtil setNegativeContent(int negativeContent){
        this.negativeContent = App.getContext().getResources().getString(negativeContent);;
        return mInstance;
    }



    public BaseMainAlertDialogUtil setCanceledOnTouchOutside(boolean outsideCancel){
        this.canceledOnTouchOutside = outsideCancel;
        return mInstance;
    }


    public void show(Context context, final BaseMainAlertDialogListener listener){
        this.mContext = context;
        this.listener = listener;
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_base_main_alertdialog, null);
        TextView tvMessage = (TextView)contentView.findViewById(R.id.message);
        Button positive = (Button)contentView.findViewById(R.id.positive);
        Button negative= (Button)contentView.findViewById(R.id.negative);

        //tvMessage.setText(message);
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
                listener.submitConfirm1();
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
