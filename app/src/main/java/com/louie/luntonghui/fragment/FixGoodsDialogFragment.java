package com.louie.luntonghui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.FixOrderArrayAdapter;
import com.louie.luntonghui.model.result.OrderConfirm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 16/3/16.
 */
public class FixGoodsDialogFragment extends DialogFragment {
    public static final String GOODSES = "goodsed";
    private ListView mListView;
    public OnClickFinishListener mListener;

    public static FixGoodsDialogFragment newInstance(ArrayList<OrderConfirm.FixGoods> list){

        FixGoodsDialogFragment fragment = new FixGoodsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(GOODSES,list);
        fragment.setArguments(bundle);
        fragment.setCancelable(false);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.order_fix_goods,null);
        Bundle bundle = getArguments();
        List<OrderConfirm.FixGoods> goods = bundle.getParcelableArrayList(GOODSES);
        mListView =(ListView) view.findViewById(R.id.listView);
        mListView.setAdapter(new FixOrderArrayAdapter(getContext(),
                R.layout.order_fix_order_item,goods));

        Button btnConfirm =(Button)view.findViewById(R.id.btn_Confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mListener.onClickFinish();
            }
        });
        return view;
    }
    public interface OnClickFinishListener{
        public void onClickFinish();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnClickFinishListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SearchListener.");
        }
    }
}