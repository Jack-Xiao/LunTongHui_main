package com.louie.luntonghui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.MineEntry;
import com.louie.luntonghui.ui.mine.MineReceiverAddressActivity;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;

import static com.louie.luntonghui.ui.register.RegisterLogin.USER_CLIENT;
import static com.louie.luntonghui.ui.register.RegisterLogin.USER_CONSUMER;
import static com.louie.luntonghui.ui.register.RegisterLogin.USER_TYPE;
import static com.louie.luntonghui.ui.register.RegisterLogin.USER_WHOLESALER;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/4.
 */
public class MineFragmentAdapter extends BaseAdapter{
    private List<MineEntry> data;
    private Context context;
    private final LayoutInflater inflater;
    private String[] mineNameListB;
    private String[] mineNameListC;


    private static final int CATEGORY_CLASS = 1;
    private static final int CATEGORY_LINE = 2;
    private static final int CATEGORY_BREAK = 3;

    public MineFragmentAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        createData();
    }

    private void createData() {
        mineNameListB = context.getResources().getStringArray(R.array.mine_list_b);
        mineNameListC = context.getResources().getStringArray(R.array.mine_list_c);
        data = new ArrayList<>();

        int[] mineImageListB = {R.drawable.product_detail_shop,
                R.drawable.product_detail_shop,
                R.drawable.product_detail_shop,
                R.drawable.product_detail_shop,
                R.drawable.product_detail_shop,
                R.drawable.product_detail_shop,
                R.drawable.product_detail_shop};

        int[] mineImageListC = {R.drawable.product_detail_shop,
                R.drawable.product_detail_shop,
                R.drawable.product_detail_shop,
                R.drawable.product_detail_shop,
                R.drawable.product_detail_shop,
                R.drawable.product_detail_shop,
        };
        int j = 0;

        String userType = DefaultShared.getString(USER_TYPE,USER_CONSUMER);

        if (userType.equals(USER_WHOLESALER)) {
            data.add(new MineEntry(mineImageListB[j], mineNameListB[j], CATEGORY_CLASS));
            j++;
            data.add(new MineEntry(mineImageListB[j], mineNameListB[j], CATEGORY_CLASS));
            j++;
            data.add(new MineEntry(mineImageListB[j], mineNameListB[j], CATEGORY_CLASS));
            data.add(new MineEntry(CATEGORY_BREAK));
            j++;
            data.add(new MineEntry(mineImageListB[j], mineNameListB[j], CATEGORY_CLASS));
            j++;
            data.add(new MineEntry(mineImageListB[j], mineNameListB[j], CATEGORY_CLASS));
            data.add(new MineEntry(CATEGORY_BREAK));
           /* j++;
            data.add(new MineEntry(mineImageListB[j], mineNameListB[j], CATEGORY_CLASS));
            j++;
            data.add(new MineEntry(mineImageListB[j], mineNameListB[j], CATEGORY_CLASS));*/

        } else {

            data.add(new MineEntry(mineImageListC[j], mineNameListC[j], CATEGORY_CLASS));
            j++;
            data.add( new MineEntry(mineImageListC[j], mineNameListC[j], CATEGORY_CLASS));
            data.add( new MineEntry(CATEGORY_BREAK));

            j++;
            data.add( new MineEntry(mineImageListC[j], mineNameListC[j], CATEGORY_CLASS));
            j++;
            data.add(new MineEntry(mineImageListC[j], mineNameListC[j], CATEGORY_CLASS));
            data.add(new MineEntry(CATEGORY_BREAK));

          /*  j++;
            data.add(new MineEntry(mineImageListC[j], mineNameListC[j], CATEGORY_CLASS));
            j++;
            data.add(new MineEntry(mineImageListC[j], mineNameListC[j], CATEGORY_CLASS));*/
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public MineEntry getItem(int position) {
        return data.get(position );
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).category;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MineEntry entry = getItem(position);
        ViewHolder viewHolder;

        viewHolder = new ViewHolder();

            switch (entry.category) {
                case CATEGORY_CLASS:
                    convertView = inflater.inflate(R.layout.mine_category_textview, null, false);
                /*TextView tv =(TextView) convertView.findViewById(R.id.category_tv);
                tv.setText(entry.name);
                ImageView img = (ImageView)convertView.findViewById(R.id.category_img);
                img.setImageResource(entry.imageId);*/
                    viewHolder.tv = (TextView) convertView.findViewById(R.id.category_tv);
                    viewHolder.imageView = (ImageView) convertView.findViewById(R.id.category_img);
                    break;
                case CATEGORY_BREAK:
                    convertView = inflater.inflate(R.layout.mine_category_break, null, false);
                    convertView.setEnabled(false);
                    convertView.setOnClickListener(null);
                    break;
                default:
                    convertView = inflater.inflate(R.layout.mine_category_break, null, false);
                    convertView.setEnabled(false);
                    convertView.setOnClickListener(null);
                    break;
            }
            viewHolder.category = entry.category;
            convertView.setTag(viewHolder);
        /*}/* else {
            viewHolder = (ViewHolder) convertView.getTag();
        }*/


        try {

            switch (entry.category) {
                case CATEGORY_CLASS:
                    Log.d("MinFrag  ------------.", viewHolder.toString());
                    viewHolder.tv.setText(entry.name);
                    viewHolder.imageView.setImageResource(entry.imageId);
                    break;
                case CATEGORY_LINE:

                    break;
                case CATEGORY_BREAK:
                    //viewHolder.name.setText(obj.getTitle());
                    break;
            }

        } catch (Exception e) {
            Log.d("error........", e.getMessage());
        }
        return convertView;
    }

    class ViewHolder {
        int category;
        ImageView imageView;
        TextView tv;
    }
}
