package com.louie.luntonghui.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.OrderDetailResult;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Jack on 16/4/29.
 */
public class CancelOrderDialogUtil {
    private String name;
    private String buyCount;

    private static CancelOrderDialogUtil mInstance;
    private Context mContext;
    private int maxGoodsCount;
    private List<RadioButton> mRadioButtonList;
    RadioButton rbMass;
    RadioButton rbNotGoodSell;
    RadioButton rbDownErrorOrder;
    RadioButton rbOthers;

    public interface CancelOrderListener{
        public void onConfirm(int position,String returnCount,
                              String reason );
    }

    private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked == false) return;
            if(buttonView instanceof RadioButton){
                for (RadioButton radio: mRadioButtonList) {
                    if(radio.getId() != buttonView.getId()){
                        radio.setChecked(false);
                    }
                }
            }
        }
    };



    public void show(int position,OrderDetailResult.GoodsListEntity entity,
                     Context context, final CancelOrderListener listener,String returnCount){
        this.mContext = context;
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_cancel_order,null);
        TextView content = (TextView)contentView.findViewById(R.id.content);
        TextView tvName = (TextView)contentView.findViewById(R.id.goods_name);
        TextView buyGoodsCount = (TextView)contentView.findViewById(R.id.buy_goods_count);

        Button positive = (Button)contentView.findViewById(R.id.positive);
        Button negative = (Button)contentView.findViewById(R.id.negative);

        rbMass = (RadioButton)contentView.findViewById(R.id.mass);
        rbNotGoodSell = (RadioButton)contentView.findViewById(R.id.not_good_sell);
        rbDownErrorOrder = (RadioButton)contentView.findViewById(R.id.down_error_order);
        rbOthers = (RadioButton)contentView.findViewById(R.id.others);

        Button btnMinus = (Button) contentView.findViewById(R.id.minus);
        Button btnPlus = (Button) contentView.findViewById(R.id.plus);

        maxGoodsCount = Integer.parseInt(entity.goods_number);
        mRadioButtonList = new ArrayList<>();
        mRadioButtonList.add(rbMass);
        mRadioButtonList.add(rbNotGoodSell);
        mRadioButtonList.add(rbDownErrorOrder);
        mRadioButtonList.add(rbOthers);

        //mRadioButtonStream = mRadioButtonList.str

        tvName.setText(entity.goods_name);
        buyGoodsCount.setText("已购买数量：" + entity.goods_number);
        content.setText(returnCount);

        rbMass.setOnCheckedChangeListener(changeListener);
        rbNotGoodSell.setOnCheckedChangeListener(changeListener);
        rbMass.setOnCheckedChangeListener(changeListener);
        rbOthers.setOnCheckedChangeListener(changeListener);

        btnMinus.setOnClickListener(v -> {
            String resource = content.getText().toString();
            try{
                int inResult = Integer.parseInt(resource);
                if(inResult > 0){
                    inResult = inResult - 1;
                    content.setText(inResult + "");
                    return;
                }
                ToastUtil.showShortToast(mContext, "数量不能小于0");
            }catch (Exception e){
                content.setText(resource);
            }
        });

        btnPlus.setOnClickListener( v ->{
            String resource = content.getText().toString();

            try{
                int inResult = Integer.parseInt(resource);
                if(inResult < maxGoodsCount){
                    inResult += 1;
                    content.setText(inResult + "");
                    return;
                }

                ToastUtil.showShortToast(mContext,"最大数不能超过" + maxGoodsCount);

            }catch(Exception e){
                content.setText(resource);
                ToastUtil.showShortToast(mContext, "请输入数字");
            }
        });

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String resource = s.toString();
                try{
                    int result = Integer.parseInt(resource);
                    if(result < 0){
                        ToastUtil.showShortToast(mContext,"数量不能小于 0");
                        return;
                    }

                    if(result > maxGoodsCount){
                        ToastUtil.showShortToast(mContext,"数量不能小于 0");
                        return;
                    }
                }catch (Exception e){
                    content.setText("0");
                    ToastUtil.showShortToast(mContext,"'" + resource + "' 不是整数");
                }
            }
        });



        final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
        mMaterialDialog.setView(contentView)
                .setCanceledOnTouchOutside(true);

        mMaterialDialog.show();

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = "";
                mMaterialDialog.dismiss();
                for (RadioButton button : mRadioButtonList) {
                    if(button.isChecked()) reason = button.getText().toString();

                }
                listener.onConfirm(position,content.getText().toString(),reason);
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
    }

    public static CancelOrderDialogUtil getInstance(){
        if(mInstance == null){
            mInstance = new CancelOrderDialogUtil();
        }
        return mInstance;
    }

}
