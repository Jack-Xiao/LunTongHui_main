package com.louie.luntonghui.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.CarList;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.net.ImageCacheManager;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.rest.RetrofitUtils;
import com.louie.luntonghui.rest.ServiceManager;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.util.AlertDialogUtil;
import com.louie.luntonghui.util.BaseAlertDialogUtil;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyAdjustPriceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;

import static com.louie.luntonghui.ui.register.RegisterLogin.USERUID;

/**
 * Created by Administrator on 2015/6/25.
 */
public class CarFragmentAdapter extends RecyclerView.Adapter<CarFragmentAdapter.ViewHolder>
        implements MyAdjustPriceView.TextChangeListener, AlertDialogUtil.AlertDialogListener,
        BaseAlertDialogUtil.BaseAlertDialogListener {
    //private CarList carLists;
    private Context mContext;
    private LayoutInflater inflater;
    private int curNumber;
    private String userId;
    private ServiceManager.LunTongHuiApi mApi;
    private boolean isFullSelect = true;
    private IntentFilter intentFilter;
    private LocalBroadcastManager mLocalBroadcaseManager;
    private ReferenceList refListen;
    private List<ShoppingCar> nativeCarList;
    private int mPostion;
    private ProgressDialog mProgressDialog;

    public CarFragmentAdapter(Context context, ReferenceList refListen) {
        this.mContext = context;
        mProgressDialog = new ProgressDialog(mContext);
        inflater = LayoutInflater.from(mContext);
        //carLists = new CarList();
        userId = DefaultShared.getString(USERUID, "0");
        if (userId.equals("0")) return;
        mApi = RetrofitUtils.createApi(mContext, ServiceManager.LunTongHuiApi.class);
        this.refListen = refListen;
        App.getBusInstance().register(this);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = inflater.inflate(R.layout.fragment_car_list_item, parent, false);

        return new ViewHolder(contentView);
    }

    public void setData(List<ShoppingCar> list) {
        if (nativeCarList == null) {
            nativeCarList = new ArrayList<>();

        }
        nativeCarList.clear();
        nativeCarList.addAll(list);
        notifyDataSetChanged();
    }


    public void clearData() {
        this.nativeCarList = null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int result = 1;

        holder.goodsName.setText(nativeCarList.get(position).goodsName);
        final String goodsName = nativeCarList.get(position).goodsName;
        final String goodsPrice = "￥" + nativeCarList.get(position).goodsShopPrice;
        final String strGuige = nativeCarList.get(position).guige;
        final String strUnit = nativeCarList.get(position).unit;

        boolean isChecked = nativeCarList.get(position).isChecked.equals("1") ? true : false;

        holder.checked.setChecked(isChecked);

        holder.checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String state;
                if (isChecked) {
                    state = "1";
                    mProgressDialog.show();
                    Map<String, String> options = new HashMap<String, String>();
                    options.put("goods_id", nativeCarList.get(position).goodsId);
                    options.put("number", nativeCarList.get(position).goodsNumber);
                    options.put("userid", userId);
                    //Result result = mApi.addCarGoods(options);
                    String url = String.format(ConstantURL.ADD_GOODS, userId, nativeCarList.get(position).goodsId,
                            holder.strContent.getText().toString());
                    RequestManager.addRequest(new GsonRequest(url, Result.class, changeCheckState(position, state), errorListener()), mContext);

                } else {
                    state = "0";
                    mProgressDialog.show();
                    String url = String.format(ConstantURL.CAR_GOODS_DEL, nativeCarList.get(position).carId);
                    RequestManager.addRequest(new GsonRequest(url, Result.class, changeCheckState(position, state), errorListener()), mContext);
                }
            }
        });





     /*   holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = 1;
                try {
                    result = Integer.parseInt(holder.strContent.getText().toString());
                } catch (Exception e) {
                    result = 1;
                }


                if (result > 1) {
                    result--;
                    notifyNumberChanged(holder, result, position,holder.checked.isChecked());
                } else {
                    return;
                }
            }
        });*/

        /*holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = 1;
                try {
                    result = Integer.parseInt(holder.strContent.getText().toString());
                } catch (Exception e) {
                    result = 1;
                }
                result++;
                notifyNumberChanged(holder, result, position,holder.checked.isChecked());
            }
        });
        //holder.strContent.setOnClickListener(new OnClickListener());
        holder.strContent.setTag(position);
        final String content = nativeCarList.get(position).goodsNumber;
        holder.strContent.setText(content);*/

        final String content = nativeCarList.get(position).goodsNumber;
        holder.strContent.setText(content);

        String rid = nativeCarList.get(position).rId;
        if (rid.equals(ShoppingCar.NOTGIVEAWAY)) {
            holder.imgDelete.setImageResource(R.drawable.cart_delete_icon);
            holder.imgDelete.setVisibility(View.VISIBLE);
            holder.imgDelete.setEnabled(true);
            holder.strContent.setBackgroundResource(R.drawable.base_frame);
            holder.btnPlus.setVisibility(View.VISIBLE);
            holder.btnMinus.setVisibility(View.VISIBLE);
            holder.goodsPrice.setVisibility(View.VISIBLE);
            //holder.strContent.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.imgDelete.setImageResource(R.drawable.giveaway);
            holder.strContent.setBackgroundResource(R.color.background_main_grey);
            holder.imgDelete.setEnabled(false);
            holder.goodsPrice.setVisibility(View.GONE);
            holder.btnMinus.setVisibility(View.GONE);
            holder.btnPlus.setVisibility(View.GONE);
        }

        holder.linAdjustCount.setEnabled(rid.equals(ShoppingCar.NOTGIVEAWAY));
        holder.btnPlus.setEnabled(rid.equals(ShoppingCar.NOTGIVEAWAY));
        holder.btnMinus.setEnabled(rid.equals(ShoppingCar.NOTGIVEAWAY));
        holder.strContent.setEnabled(rid.equals(ShoppingCar.NOTGIVEAWAY));


        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustGoods(goodsName, goodsPrice, strUnit, strGuige, holder, position, content);
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustGoods(goodsName, goodsPrice, strUnit, strGuige, holder, position, content);
            }
        });
        holder.strContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustGoods(goodsName, goodsPrice, strUnit, strGuige, holder, position, content);
            }
        });


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostion = position;
                //return;

                BaseAlertDialogUtil.getInstance()
                        .setMessage(R.string.sure_delete)
                        .setCanceledOnTouchOutside(true)
                        .setNegativeContent(R.string.cancel)
                        .setPositiveContent(R.string.confirm);

                BaseAlertDialogUtil.getInstance().show(mContext, CarFragmentAdapter.this);

            }
        });

        /*Picasso.with(mContext)
                .load(nativeCarList.get(position).goodsImage)
                .into(holder.goodsImg);*/

    /*    Uri uri = Uri.parse(nativeCarList.get(position).goodsImage);
        holder.goodsImg.setImageURI(uri);*/
        if (holder.imageRequest != null) {
            holder.imageRequest.cancelRequest();
        }
        holder.imageRequest = ImageCacheManager.loadImage(nativeCarList.get(position).goodsImage,
                ImageCacheManager.getImageListener(holder.goodsImg));

        holder.goodsPrice.setText("价格:￥" + nativeCarList.get(position).goodsShopPrice);

        Integer discountType = Integer.parseInt(nativeCarList.get(position).discountType);
        String birary = Integer.toBinaryString(discountType);

        holder.prim.setVisibility(View.GONE);
        holder.discount.setVisibility(View.GONE);
        holder.present.setVisibility(View.GONE);

        if (discountType != 0) {
            for (int i = birary.length() - 1; i >= 0; i--) {
                if (i == birary.length() - 1) {
                    Log.d("length ", birary.substring(birary.length() - 1) + "-1");
                    if (birary.substring(birary.length() - 1).equals("1"))
                        holder.discount.setVisibility(View.VISIBLE);
                    if (!nativeCarList.get(position).discount.equals("0")) {
                        double discount = 0;
                        discount = Double.parseDouble(nativeCarList.get(position).discount);
                        double curGoodsShopPrice = Double.parseDouble(nativeCarList.get(position).goodsShopPrice);
                        double curPrince = curGoodsShopPrice * discount / 10;
                        holder.goodsPrice.setText("价格:￥" + curPrince);
                    }
                } else if (i == birary.length() - 2) {
                    Log.d("length ", birary.substring(birary.length() - 2, birary.length() - 1) + "-2");
                    if (birary.substring(birary.length() - 2, birary.length() - 1).equals("1")) {
                        holder.present.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d("length ", birary.substring(i, i + 1) + "-3");
                    if (birary.substring(i, i + 1).equals("1")) {
                        holder.prim.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }
        holder.mineCount.setText("数量/" + nativeCarList.get(position).unit);
    }
    //adjustGoods(goodsName,goodsPrice,strUnit,strGuige,holder,position);

    public void adjustGoods(String goodsName, String goodsPrice, String strUnit, String strGuige,
                            final ViewHolder holder, final int position, String content) {
        //AlertDialogUtil.getInstance().show(mContext,CarFragmentAdapter.this, );
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
                notifyNumberChanged(holder, curResult[0], position, holder.checked.isChecked());
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

    private Response.Listener<Result> deleteGoodsRequest(final int position) {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(Result result) {
                if (result.rsgcode.equals(BaseNormalActivity.SUCCESSCODE)) {

                    ToastUtil.showShortToast(mContext, R.string.delete_success);
                    String recId = nativeCarList.get(position).carId;
                    new Delete().
                            from(ShoppingCar.class)
                            .where("car_id = ?", recId)
                            .execute();
                    refListen.reference(0);
                } else {
                    ToastUtil.showShortToast(mContext, result.rsgmsg);
                }
            }
        };
    }

    private void updateCheckState(final String carId, boolean isChecked) {
        final String state;
        if (isChecked) {
            state = "1";
        } else {
            state = "0";
        }

        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                new Update(ShoppingCar.class)
                        .set("isChecked = ?", state)
                        .where("car_id = ?", carId)
                        .execute();
                return null;
            }
        });
    }

    private Response.Listener<Result> editGoodsResponse() {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(Result result) {
                if (result.rsgcode.equals(ConstantURL.SUCCESSCODE)) {
                    // App.getBusInstance().post(new ReferenceCarList());
                    refListen.reference(0);
                } else {
                    ToastUtil.showShortToast(mContext, result.rsgmsg);
                }
            }
        };
    }

    private void notifyNumberChanged(ViewHolder holder, int result, int position, final boolean isChecked) {
        holder.strContent.setText(result + "");
        if (holder.strContent.getText().toString().equals("")) {
            holder.strContent.setText("1");
            return;
        }

        //holder.strContent.setSelection(holder.strContent.getText().length());
        String recId = nativeCarList.get(position).carId;
        String newNumber = holder.strContent.getText().toString();
        //Result result = mApi.editCarGoods(recId,userId,newNumber);

        String urlString = String.format(ConstantURL.EDIT_GOODS, recId, userId, newNumber);
        RequestManager.addRequest(new GsonRequest(urlString,
                Result.class, editGoodsResponse(), errorListener()), this);

        /*if(isChecked){

        }else{
            ToastUtil.showShortToast(mContext,"请勾选该商品");
            return;
        }

        String urlString = String.format(ConstantURL.EDIT_GOODS, recId, userId, newNumber);
        //RequestManager.addRequest(new GsonRequest(urlString, Result.class, editGoodsResponse(), errorListener()), this);

        new Update(ShoppingCar.class)
                .set("goods_number=?",newNumber)
                .where("car_id=?",recId)
                .execute();
        refListen.reference(1);*/

    }


    public Response.Listener<Result> changeCheckState(final int position, final String state) {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(Result result) {
                mProgressDialog.dismiss();
                if (result.rsgcode.equals("000")) {
                    new Update(ShoppingCar.class)
                            .set("isChecked = ?", state)
                            .where("goods_id = ?", nativeCarList.get(position).goodsId)
                            .execute();

                    refListen.reference(1);
                } else {
                    ToastUtil.showLongToast(mContext, result.rsgmsg);
                }
            }
        };
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                ToastUtil.showLongToast(mContext, error.getMessage());
                Log.d("error  ....", error.getMessage());
            }
        };
    }


    @Override
    public int getItemCount() {
        return nativeCarList == null ? 0 : nativeCarList.size();
    }

    @Override
    public void change(String text) {
        curNumber = Integer.parseInt(text);
    }

    public void setChecked(boolean isChecked) {
        isFullSelect = isChecked;
        notifyDataSetChanged();
    }

    @Override
    public void reset(int count) {
        // notifyNumberChanged(count);
    }

    @Override
    public void confirm() {

        String receId = nativeCarList.get(mPostion).carId;
        String url = String.format(ConstantURL.CAR_GOODS_DEL, receId);

        RequestManager.addRequest(new GsonRequest(url, Result.class,
                deleteGoodsRequest(mPostion), errorListener()), mContext);
    }

    class CarlListCallback implements Callback<CarList> {
        @Override
        public void success(CarList carList, retrofit.client.Response response) {
            mProgressDialog.dismiss();
            double totalPrice = carList.total.goods_amount;
            //carLists = carList;
            notifyDataSetChanged();
            //App.getBusInstance().post(new ReferenceCarList(totalPrice));
            refListen.reference(0);
        }

        @Override
        public void failure(RetrofitError error) {
            mProgressDialog.dismiss();
            ToastUtil.showLongToast(mContext, error.getMessage());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.checked)
        CheckBox checked;
        @InjectView(R.id.goods_img)
        ImageView goodsImg;
        @InjectView(R.id.goods_name)
        TextView goodsName;
        @InjectView(R.id.goods_price)
        TextView goodsPrice;
/*        @InjectView(R.id.adjust_price)
        MyAdjustPriceView adjustPrice;*/

        @InjectView(R.id.minus)
        Button btnMinus;
        @InjectView(R.id.plus)
        Button btnPlus;
        @InjectView(R.id.content)
        TextView strContent;

        @InjectView(R.id.delete)
        ImageView imgDelete;

        @InjectView(R.id.adjust_count)
        LinearLayout linAdjustCount;

        @InjectView(R.id.present)
        TextView present;
        @InjectView(R.id.discount)
        TextView discount;

        @InjectView(R.id.prim)
        TextView prim;

        @InjectView(R.id.car_mine_count)
        TextView mineCount;

        public ImageLoader.ImageContainer imageRequest;

        boolean isChecked = true;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        App.getBusInstance().unregister(this);
    }

    public interface ReferenceList {
        public void reference(int result);
    }
}