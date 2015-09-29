package com.louie.luntonghui.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.louie.luntonghui.R;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.ToastUtil;

/**
 * Created by Administrator on 2015/6/2.
 */
public abstract class SecondLevelBaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected static final String SUCCESSCODE = ConstantURL.SUCCESSCODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mToolbar =(Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(setToolTitle()));

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.actionbar_back);
    }

    protected abstract int setToolTitle();

    protected abstract int getContentView();

    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error !=null)
                ToastUtil.showLongToast(SecondLevelBaseActivity.this,error.getMessage());

            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == 16908332){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
