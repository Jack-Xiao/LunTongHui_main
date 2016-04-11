package com.louie.luntonghui.adapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.Address;
import com.louie.luntonghui.model.result.AddAddressResult;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.mine.MineAdditionAddressActivity;
import com.louie.luntonghui.ui.mine.MineReceiverAddressActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.BaseAlertDialogUtil;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.EncoderURL;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/6/10.
 */
public class MineReceiverAddressAdapter extends RecyclerView.Adapter<MineReceiverAddressAdapter.ViewHolder>
        implements BaseAlertDialogUtil.BaseAlertDialogListener {
    private List<Address> data;
    private MineReceiverAddressActivity mContext;
    private int position;
    public static final String ADDRESSKEY = "address_key";
    private String uid;
    private int curDelPostion;
    private boolean isSelected = false;
    public AddressListener mListener;

    public interface AddressListener {
        public void onModify(String addressId);
    }

    public Address getAddress(int position) {

        return data.get(position);
    }

    public void setData(List<Address> data) {
        this.data.clear();
        this.data = data;
    }

    public MineReceiverAddressAdapter(MineReceiverAddressActivity context) {
        this(context, false);
    }

    public MineReceiverAddressAdapter(MineReceiverAddressActivity context, boolean isSelected) {
        this.mContext = context;
        mListener = context;
        uid = DefaultShared.getString(RegisterLogin.USERUID, "-1");
        if (data == null) {
            data = new ArrayList<Address>();
        }
        this.isSelected = isSelected;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.address_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String prvince = ((App) mContext.getApplication()).idNList.get(data.get(position).province);
        String city = ((App) mContext.getApplication()).idNList.get(data.get(position).city);
        String district = ((App) mContext.getApplication()).idNList.get(data.get(position).district);
        String detail = prvince + city + district + data.get(position).address;

        holder.mConsignee.setText(data.get(position).consignee);
        holder.mMobile.setText(data.get(position).phone);
        holder.mAddressDetail.setText(detail);
        //holder.mDefaultSelect.setEnabled(Integer.parseInt(data.get(position).defaultSelect) != 0 ? true : false);
        int select = Integer.parseInt(data.get(position).defaultSelect);
        holder.mDefaultSelect.setChecked(select != 0);
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                curDelPostion = position;

                BaseAlertDialogUtil.getInstance()
                        .setMessage(R.string.sure_delete)
                        .setNegativeContent(R.string.choose_not_delete)
                        .setPositiveContent(R.string.choose_delete);

                BaseAlertDialogUtil.getInstance().show(mContext, MineReceiverAddressAdapter.this);

                //RemoveOpteration(data.get(position).addressId, position);

            }
        });
        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Address address = data.get(position);
                bundle.putParcelable(ADDRESSKEY, address);
                IntentUtil.startActivity(((MineReceiverAddressActivity) mContext), MineAdditionAddressActivity.class, bundle);
            }
        });
        holder.poition = position;

        if (isSelected) {
            holder.mDelete.setVisibility(View.GONE);
            holder.mEdit.setVisibility(View.GONE);
            holder.mDefaultSelect.setEnabled(false);
        } else {
            holder.mDelete.setVisibility(View.VISIBLE);
            holder.mEdit.setVisibility(View.VISIBLE);
            holder.mDefaultSelect.setEnabled(true);
        }
        holder.mDefaultSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = uid;
                String addressId = data.get(position).addressId;
                String strPlace = data.get(position).address;
                String strConsignee = data.get(position).consignee;
                String strMobileValue = data.get(position).phone;
                String provinceId = data.get(position).province;
                String cityId = data.get(position).city;
                String districtId = data.get(position).district;
                String intSelect = Address.DEFAULT_SELECTED;

                strConsignee = EncoderURL.encode(strConsignee);
                strPlace = EncoderURL.encode(strPlace);

                String url = String.format(ConstantURL.MODIFYADDRESS, userId, addressId,
                        strPlace, strConsignee, strMobileValue,
                        provinceId, cityId, districtId, intSelect);

                RequestManager.addRequest(new GsonRequest(url, AddAddressResult.class, modifyAddress(addressId), errorListener()),MineReceiverAddressAdapter.this);

            }
        });
    }
    public Response.Listener<AddAddressResult> modifyAddress(final String addressId){
        return new Response.Listener<AddAddressResult>() {
            @Override
            public void onResponse(AddAddressResult response) {
                if(response.rsgcode.equals(Result.SUCCESS)){
                    mListener.onModify(addressId);
                }
                ToastUtil.showLongToast(mContext,response.rsgmsg);
            }
        };
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null)
                    ToastUtil.showLongToast(mContext, "修改失败");
            }
        };
    }


    private void RemoveOpteration(String addressId, int position) {
        String url = String.format(ConstantURL.DELADDRESS, addressId, uid);

        RequestManager.addRequest(new GsonRequest<Result>(url, Result.class,
                delAddressListener(addressId, position), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.showLongToast(mContext, volleyError.getMessage());
            }
        }), this);
    }


    private Response.Listener<Result> delAddressListener(final String addressId, final int position) {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(final Result result) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        if (result.rsgcode.equals(ConstantURL.SUCCESSCODE)) {
                            new Delete()
                                    .from(Address.class)
                                    .where("address_id = ?", addressId)
                                    .execute();

                        } else {
                            ToastUtil.showShortToast(mContext, result.rsgmsg);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        data.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
        };
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public void confirm() {
        RemoveOpteration(data.get(curDelPostion).addressId, curDelPostion);
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.address_consignee)
        TextView mConsignee;
        @InjectView(R.id.address_mobile)
        TextView mMobile;
        @InjectView(R.id.address_detail)
        TextView mAddressDetail;
        @InjectView(R.id.address_default_select)
        RadioButton mDefaultSelect;
        @InjectView(R.id.address_edit)
        TextView mEdit;
        @InjectView(R.id.address_delete)
        TextView mDelete;
        public int poition;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
             /*   case R.id.address_default_select:
                    break;
                case R.id.address_edit:
                    Address address = data.get(poition);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ADDRESSKEY,address);
                    IntentUtil.startActivity(mContext,MineAdditionAddressActivity.class,bundle);
                    break;
                case R.id.address_delete:
                    //data.


                    break;*/
            }
        }
    }
}
