package com.louie.luntonghui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.result.ProduceOrder;
import com.louie.luntonghui.model.result.ProduceOrderResult;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.net.ImageCacheManager;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.BaseAlertDialogUtil;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.ToastUtil;

import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;


/**
 * Created by Administrator on 2015/7/8.
 */
public class ProduceOrderAdapter extends BaseAdapter implements BaseAlertDialogUtil.BaseAlertDialogListener {
    public List<ProduceOrder.CartGoodsEntity> data;
    private LayoutInflater inflater;
    private Context mContext;
    private boolean isFixOrder;
    public FixOrderListener mListener;
    private int mPosition;
    private String mOrderId;
    private String userId;


    @Override
    public void confirm() {
        String orderId = data.get(mPosition).rec_id;
        String goodsId = data.get(mPosition).goods_id;
        String delUrl = String.format(ConstantURL.DELORDERGOODS,orderId,goodsId);
        RequestManager.addRequest(new GsonRequest(delUrl,
                Result.class, delOrderGoodsRequest(), errorListener()), this);
    }

    public interface FixOrderListener{
        public void reference();
    }

    public ProduceOrderAdapter(Context context,FixOrderListener listener){
        userId = DefaultShared.getString(RegisterLogin.USERUID,RegisterLogin.DEFAULT_USER_ID);
        mContext = context;
        mListener = listener;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<ProduceOrder.CartGoodsEntity> cart_goods,boolean isFixOrder){
        if(data == null ){
            data = new ArrayList<>();
        }
        this.isFixOrder = isFixOrder;
        data.clear();
        data.addAll(cart_goods);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data == null? 0 : data.size();
    }

    @Override
    public ProduceOrder.CartGoodsEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.car_produce_order_item,null);
            viewHolder = new ViewHolder();
            viewHolder.goodsImg = (ImageView)convertView.findViewById(R.id.goods_img);
            viewHolder.goodsName =(TextView)convertView.findViewById(R.id.goods_name);
            viewHolder.goodsPrice = (TextView)convertView.findViewById(R.id.goods_price);
            viewHolder.goodsNumber = (TextView)convertView.findViewById(R.id.goods_number);
            viewHolder.delImage = (ImageView)convertView.findViewById(R.id.delete);
            viewHolder.discount = (TextView)convertView.findViewById(R.id.discount);
            viewHolder.present = (TextView)convertView.findViewById(R.id.present);
            viewHolder.prim = (TextView)convertView.findViewById(R.id.prim);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(viewHolder.imageRequest !=null){
            viewHolder.imageRequest.cancelRequest();
        }
        viewHolder.imageRequest = ImageCacheManager.loadImage(data.get(position).goods_thumb
            ,ImageCacheManager.getImageListener(viewHolder.goodsImg));
        
        viewHolder.goodsName.setText(data.get(position).goods_name);
        viewHolder.goodsPrice.setText("价格:￥" + data.get(position).goods_price +"/" + data.get(position).danwei);
        viewHolder.goodsNumber.setText(data.get(position).goods_number);

        Integer discountType = Integer.parseInt(data.get(position).discount_type);
        String birary = Integer.toBinaryString(discountType);

        viewHolder.prim.setVisibility(View.GONE);
        viewHolder.discount.setVisibility(View.GONE);
        viewHolder.present.setVisibility(View.GONE);

        if(discountType !=0) {
            for(int i = birary.length() -1;i>=0;i--){
                if(i == birary.length() -1){
                    if(birary.substring(birary.length()-1).equals("1"))
                        viewHolder.discount.setVisibility(View.VISIBLE);
                    if (!data.get(position).discount.equals("0")) {
                        double discount = Double.parseDouble(data.get(position).discount);
                        double curGoodsShopPrice = Double.parseDouble(data.get(position).goods_price);
                        double curPrince = curGoodsShopPrice * discount / 10;
                        viewHolder.goodsPrice.setText("价格:￥" + curPrince +"/" + data.get(position).danwei);
                    }
                }else if(i == birary.length() -2){
                    if(birary.substring(birary.length()-2,birary.length() -1).equals("1"))
                        viewHolder.present.setVisibility(View.VISIBLE);
                }else{
                    if(birary.substring(i,i+1).equals("1")){
                        viewHolder.prim.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }

        if(isFixOrder){
            viewHolder.delImage.setTag(position);
            viewHolder.delImage.setVisibility(View.VISIBLE);
            viewHolder.goodsNumber.setTag(position);
            viewHolder.goodsNumber.setEnabled(true);
            viewHolder.goodsNumber.setBackgroundResource((R.drawable.base_frame));
            viewHolder.delImage.setOnClickListener(delClickListener);
            viewHolder.goodsNumber.setOnClickListener(fixGoodsClickListener);
        }else{
            viewHolder.delImage.setTag(position);
            viewHolder.goodsNumber.setBackgroundResource(R.color.background_main_grey);
            viewHolder.delImage.setVisibility(View.GONE);
            viewHolder.goodsNumber.setEnabled(false);
        }


    /*    Log.d("rec_id:",data.get(position).rec_id);
        Log.d("rec_id_rid:",data.get(position).rid);
        Log.d("rec_id:",position + "");*/

        if(!data.get(position).rid.equals("0")){
            viewHolder.goodsPrice.setVisibility(View.GONE);
            viewHolder.delImage.setImageResource(R.drawable.giveaway);
            viewHolder.goodsNumber.setEnabled(false);
            viewHolder.goodsNumber.setBackgroundResource(R.color.background_main_grey);
        }else{
            viewHolder.goodsPrice.setVisibility(View.VISIBLE);
            viewHolder.delImage.setImageResource(R.drawable.cart_delete_icon);
            viewHolder.goodsNumber.setEnabled(true);
            viewHolder.goodsNumber.setBackgroundResource(R.drawable.base_frame);
        }

        return convertView;
    }

    private View.OnClickListener delClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mPosition =  (Integer)v.getTag();
            BaseAlertDialogUtil.getInstance()
                    .setMessage(R.string.sure_delete)
                    .setCanceledOnTouchOutside(true)
                    .setNegativeContent(R.string.cancel)
                    .setPositiveContent(R.string.confirm);

            BaseAlertDialogUtil.getInstance().show(mContext, ProduceOrderAdapter.this);
        }
    };

    private Response.Listener<Result> delOrderGoodsRequest() {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(Result result) {
                if(result.rsgcode.equals(ConstantURL.SUCCESSCODE)) {
                    mListener.reference();
                }
                ToastUtil.showShortToast(mContext, result.rsgmsg);
            }
        };
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse == null){
                    ToastUtil.showShortToast(mContext, R.string.network_connect_fail);
                    return;
                }
                switch (error.networkResponse.statusCode){
                    case HttpStatus.SC_NO_CONTENT:
                }
                ToastUtil.showLongToast(mContext, error.getMessage());
            }
        };
    }


    private View.OnClickListener fixGoodsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer)v.getTag();
            ProduceOrder.CartGoodsEntity order = data.get(position);
            String goodsName = order.goods_name;
            String goodsPrice = order.goods_price;
            String strUnit = order.danwei;
            String strGuige = order.guige;

            String content = order.goods_number;
            final String goodsId = order.goods_id;

            View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_adjust_goods_number, null);
            ImageButton btnMinus = (ImageButton) contentView.findViewById(R.id.minus);
            ImageButton btnPlus = (ImageButton) contentView.findViewById(R.id.plus);
            TextView tvGoodsName = (TextView) contentView.findViewById(R.id.goods_name);
            TextView tvShopPrice = (TextView) contentView.findViewById(R.id.shop_price);
            TextView guige = (TextView) contentView.findViewById(R.id.guige);

            Button btnConfirm = (Button) contentView.findViewById(R.id.confirm);
            Button btnCacnel = (Button) contentView.findViewById(R.id.cancel);


            tvGoodsName.setText(goodsName);
            tvShopPrice.setText(goodsPrice + "/" + strUnit);

            guige.setText("规格:" + strGuige);


            final EditText mContent = (EditText) contentView.findViewById(R.id.content);
            final int[] curResult = new int[1];

            mContent.setText(content + "");
            mContent.setSelection(mContent.length());


            final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);

            mMaterialDialog.setView(contentView)
                    .setCanceledOnTouchOutside(true);

            mMaterialDialog.show();

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        curResult[0] = Integer.parseInt(mContent.getText().toString());
                        //mListener.reset(curResult[0]);
                        //
                    } catch (Exception e) {
                        ToastUtil.showShortToast(mContext, R.string.please_input_number);
                        return;
                    }
                    //notifyNumberChanged(holder, curResult[0], position, holder.checked.isChecked());
                    notifyNumberChanged(goodsId,curResult[0]);
                    mMaterialDialog.dismiss();
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
                    //notifyNumberChanged(holder, result, position, holder.checked.isChecked());
                }
            });


            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int result = 1;
                    try {
                        result = Integer.parseInt(mContent.getText().toString());
                    } catch (Exception e) {
                        result = 1;
                    }


                    if (result > 1) {
                        result--;
                        ///notifyPriceChanged(holder, result);
                        mContent.setText(result + "");
                        //notifyNumberChanged(holder, result, position, holder.checked.isChecked());
                    } else {
                        return;
                    }
                }
            });


        }
    };

    private void notifyNumberChanged(String goodsId, int result) {
        String fixCount = goodsId +"," + result;
        String fixUrl = String.format(ConstantURL.FIXORDERNUMBER,userId,mOrderId,fixCount);
        RequestManager.addRequest(new GsonRequest(fixUrl,ProduceOrderResult.class,
                fixOrderNumberRequest(),errorListener()),this);
    }

    private Response.Listener<ProduceOrderResult> fixOrderNumberRequest() {
        return new Response.Listener<ProduceOrderResult>() {
            @Override
            public void onResponse(ProduceOrderResult produceOrderResult) {
                if(produceOrderResult.rsgcode.equals(ConstantURL.SUCCESSCODE)){
                    mListener.reference();
                }
            }
        };
    }

    public void fixOrder(boolean isFinishFixOrder,String orderId) {
        mOrderId = orderId;
        isFixOrder = isFinishFixOrder;
        notifyDataSetChanged();
    }

    class ViewHolder{
        ImageView goodsImg;
        TextView goodsName;
        TextView goodsPrice;
        TextView goodsNumber;
        ImageView delImage;

        TextView present;
        TextView discount;
        TextView prim;

        public ImageLoader.ImageContainer imageRequest;
    }
}
