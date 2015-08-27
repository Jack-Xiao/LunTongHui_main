package com.louie.luntonghui.view;

import android.content.Context;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.louie.luntonghui.App;
import com.louie.luntonghui.R;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Administrator on 2015/7/16.
 */
public class MyAlertDialogUtil {

    public AlertDialogListener listener;
    private static Context mContext;

    private static MyAlertDialogUtil mInstance;
    private String message;
    private String positiveContent;
    private String negativeContent;
    private boolean canceledOnTouchOutside = true;

    public interface AlertDialogListener{
        public void confirmUpdate();
    }

    public static MyAlertDialogUtil getInstance(){
        if(mInstance == null){
            mInstance = new MyAlertDialogUtil();
        }
        return mInstance;
    }

    public MyAlertDialogUtil setMessage(String message){
        this.message = message;
        return mInstance;
    }

    public MyAlertDialogUtil setMessage(int message){
        this.message = App.getContext().getResources().getString(message);
        return mInstance;
    }

    public MyAlertDialogUtil setPositiveContent(String positiveContent){
        this.positiveContent = positiveContent;
        return mInstance;
    }

    public MyAlertDialogUtil setNegativeContent(String negativeContent){
        this.negativeContent = negativeContent;
        return mInstance;
    }


    public MyAlertDialogUtil setPositiveContent(int positiveContent){
        this.positiveContent = App.getContext().getResources().getString(positiveContent);
        return mInstance;
    }

    public MyAlertDialogUtil setNegativeContent(int negativeContent){
        this.negativeContent = App.getContext().getResources().getString(negativeContent);;
        return mInstance;
    }



    public MyAlertDialogUtil setCanceledOnTouchOutside(boolean outsideCancel){
        this.canceledOnTouchOutside = outsideCancel;
        return mInstance;
    }


    public void show(Context context, final AlertDialogListener listener){
        this.mContext = context;
        this.listener = listener;
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_my_alertdialog, null);
        TextView tvMessage = (TextView)contentView.findViewById(R.id.message);
        Button positive = (Button)contentView.findViewById(R.id.positive);
        Button negative= (Button)contentView.findViewById(R.id.negative);

        //tvMessage.setText(message);
        tvMessage.setText(Html.fromHtml(message));
        tvMessage.setMovementMethod(new ScrollingMovementMethod());

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
                listener.confirmUpdate();

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
