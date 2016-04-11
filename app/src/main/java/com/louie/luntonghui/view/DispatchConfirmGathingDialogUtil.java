package com.louie.luntonghui.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.util.ToastUtil;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Administrator on 2015/7/16.
 */
public class DispatchConfirmGathingDialogUtil {

    public AlertDialogListener listener;
    private static Context mContext;

    private static DispatchConfirmGathingDialogUtil mInstance;
    private String consignne;
    private String applyMoney;
    private String negativeContent;
    private boolean canceledOnTouchOutside = true;
    private String orderId;
    private String orderSn;

    public interface AlertDialogListener{
        public void confirmGathering(String explain,String applyMoney,String orderId,String rType);
    }

    public static DispatchConfirmGathingDialogUtil getInstance(){
        if(mInstance == null){
            mInstance = new DispatchConfirmGathingDialogUtil();
        }
        return mInstance;
    }

    public DispatchConfirmGathingDialogUtil setOrderId(String orderId){
        this.orderId = orderId;
        return mInstance;
    }

    public DispatchConfirmGathingDialogUtil setConsignne(String message){
        this.consignne =  message;
        return mInstance;
    }

    public DispatchConfirmGathingDialogUtil setApplyMoney(String money){
        this.applyMoney = money;
        return mInstance;
    }

    public DispatchConfirmGathingDialogUtil setOrderSn(String orderSn){
        this.orderSn = orderSn;
        return mInstance;
    }

    public void show(Context context, final AlertDialogListener listener){
        this.mContext = context;
        this.listener = listener;

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_dialog_confirm_gathering, null);
        TextView tvOrder = (TextView)contentView.findViewById(R.id.order);
        TextView tvConsignee = (TextView)contentView.findViewById(R.id.consignee);
        TextView tvApplyMoney = (TextView)contentView.findViewById(R.id.apply_money);
        final TextView tvNeedMoney = (TextView)contentView.findViewById(R.id.need_money);
        final EditText etConsigneeExplain = (EditText)contentView.findViewById(R.id.consignee_explain);
        final RadioButton rbCash = (RadioButton)contentView.findViewById(R.id.cash_pay);
        final RadioButton rbCard = (RadioButton)contentView.findViewById(R.id.card_pay);


        Button btnCancel = (Button)contentView.findViewById(R.id.cancel);
        Button btnConfirm = (Button)contentView.findViewById(R.id.confirm);


        tvOrder.setText("出仓单号: " + orderSn);
        tvConsignee.setText("收货人: " + consignne);
        tvApplyMoney.setText("应收金额: " + applyMoney);
        tvNeedMoney.setHint(applyMoney);

        final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
        mMaterialDialog.setView(contentView)
                .setCanceledOnTouchOutside(canceledOnTouchOutside);

        mMaterialDialog.show();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(tvNeedMoney.getText().toString())){
                    ToastUtil.showShortToast(mContext,"请填写实收金额");
                    return;
                }
                String explainResult ="";
                String rType = "";
                if(rbCash.isChecked()){
                    //explainResult = rbCash.getText().toString();
                    rType = "0";
                }else{
                    //explainResult = rbCard.getText().toString();
                    rType  = "1";
                }

                String explain = etConsigneeExplain.getText().toString();
                String needMoney = tvNeedMoney.getText().toString();
                listener.confirmGathering(explain,needMoney,orderId,rType);
                mMaterialDialog.dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
    }
}
