package com.louie.luntonghui.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.Goods;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Administrator on 2015/7/5.
 */
public class AlertDialogUtil {

    private Context mContext;
    public AlertDialogListener mListener;
    public static int COUNT = 1;
    private static AlertDialogUtil mInstance;
    private String goodsName;
    private String goodsPrice;
    private String goodsNumber;


    public interface AlertDialogListener{
        public void reset(int count);
    }

    private AlertDialogUtil(){}

    public static AlertDialogUtil  getInstance(){
         if(mInstance == null){
             mInstance = new AlertDialogUtil();
         }
        return mInstance;
    }

    public void show(Context context,AlertDialogListener listener,String goodsName,String goodsPrice,String goodnumber){
        this.goodsName = goodsName;
        this.goodsPrice ="￥"+ goodsPrice;
        this.COUNT = Integer.parseInt(goodnumber);
        Goods goods = null;
        show(context,listener,goods,COUNT);
    }

    public void show(Context context,AlertDialogListener listener,Goods goods,int count){
        mContext = context;
        mListener = listener;

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_adjust_goods_number, null);
        ImageButton btnMinus = (ImageButton) contentView.findViewById(R.id.minus);
        ImageButton btnPlus = (ImageButton) contentView.findViewById(R.id.plus);
        TextView tvGoodsName = (TextView)contentView.findViewById(R.id.goods_name);
        TextView tvShopPrice = (TextView)contentView.findViewById(R.id.shop_price);
        TextView guige = (TextView)contentView.findViewById(R.id.guige);

        Button btnConfirm = (Button)contentView.findViewById(R.id.confirm);
        Button btnCacnel = (Button)contentView.findViewById(R.id.cancel);

        if(goods != null){
            goodsName = goods.goodsName;
            goodsPrice = "￥"+goods.shopPrice + "/"+ goods.unit;
            guige.setText("规格:" + goods.guige);
        }

        tvGoodsName.setText(goodsName);
        tvShopPrice.setText(goodsPrice);


        final EditText mContent = (EditText) contentView.findViewById(R.id.content);
        final int[] curResult = new int[1];

        mContent.setText(count + "");
        mContent.selectAll();


        final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
        mMaterialDialog.setView(contentView)
                .setCanceledOnTouchOutside(true);

        mMaterialDialog.show();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    curResult[0] = Integer.parseInt(mContent.getText().toString());
                    mListener.reset(curResult[0]);
                    mMaterialDialog.dismiss();
                } catch (Exception e) {
                    ToastUtil.showShortToast(mContext, R.string.please_input_number);
                    return;
                }
            }
        });

        btnCacnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = 1;
                try {
                    result = Integer.parseInt(mContent.getText().toString());
                } catch (Exception e) {
                    result = 1;
                }
                result++;
                //notifyPriceChanged(holder, result);
                mContent.setText(result + "");
            }
        });


        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = 1;
                try {
                    //result = Integer.parseInt(holder.strContent.getText().toString());
                } catch (Exception e) {
                    result = 1;
                }


                if (result > 1) {
                    result--;
                    ///notifyPriceChanged(holder, result);
                    mContent.setText(result + "");
                } else {
                    return;
                }
            }
        });
    }

  /*  public void show(Context context,AlertDialogListener listener,SecondKillGoods goods,int count){
        mContext = context;
        mListener = listener;


        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_adjust_goods_number, null);
        ImageButton btnMinus = (ImageButton) contentView.findViewById(R.id.minus);
        ImageButton btnPlus = (ImageButton) contentView.findViewById(R.id.plus);
        TextView tvGoodsName = (TextView)contentView.findViewById(R.id.goods_name);
        TextView tvShopPrice = (TextView)contentView.findViewById(R.id.shop_price);
        TextView guige = (TextView)contentView.findViewById(R.id.guige);

        Button btnConfirm = (Button)contentView.findViewById(R.id.confirm);
        Button btnCacnel = (Button)contentView.findViewById(R.id.cancel);

        if(goods != null){
            goodsName = goods.goodsName;
            goodsPrice = "￥"+goods.shopPrice + "/"+ goods.unit;
            guige.setText("规格:" + goods.guige);
        }

        tvGoodsName.setText(goodsName);
        tvShopPrice.setText(goodsPrice);


        final EditText mContent = (EditText) contentView.findViewById(R.id.content);
        final int[] curResult = new int[1];

        mContent.setText(count + "");
        mContent.selectAll();


        final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
        mMaterialDialog.setView(contentView)
                .setCanceledOnTouchOutside(true);

        mMaterialDialog.show();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    curResult[0] = Integer.parseInt(mContent.getText().toString());
                    mListener.reset(curResult[0]);
                    mMaterialDialog.dismiss();
                } catch (Exception e) {
                    ToastUtil.showShortToast(mContext, R.string.please_input_number);
                    return;
                }
            }
        });

        btnCacnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = 1;
                try {
                    result = Integer.parseInt(mContent.getText().toString());
                } catch (Exception e) {
                    result = 1;
                }
                result++;
                //notifyPriceChanged(holder, result);
                mContent.setText(result + "");
            }
        });


        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = 1;
                try {
                    //result = Integer.parseInt(holder.strContent.getText().toString());
                } catch (Exception e) {
                    result = 1;
                }


                if (result > 1) {
                    result--;
                    ///notifyPriceChanged(holder, result);
                    mContent.setText(result + "");
                } else {
                    return;
                }
            }
        });
    }*/
}
