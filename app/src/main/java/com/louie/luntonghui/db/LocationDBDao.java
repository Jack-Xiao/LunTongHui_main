package com.louie.luntonghui.db;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Administrator on 2015/6/10.
 */
public class LocationDBDao extends BaseDataHelper {

    public LocationDBDao(Context context) {
        super(context);
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.REGION_CONTENT_URI;
    }
}
