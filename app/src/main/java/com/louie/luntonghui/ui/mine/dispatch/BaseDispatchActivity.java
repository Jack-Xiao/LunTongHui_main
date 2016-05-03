package com.louie.luntonghui.ui.mine.dispatch;

import com.louie.luntonghui.ui.BaseCenterToolbarActivity;

/**
 * Created by Jack on 16/4/1.
 */
public abstract class BaseDispatchActivity extends BaseCenterToolbarActivity {

    public static final int DISPATCH_NONE = 0;
    public static final int DISPATCH_HAS = 1;
    public static final int DISPATCH_WHOLE = 2;
    private int dispatchType;

    public static final String HAS_GET = "1";
    public static final String NOT_GET = "0";
    protected static final String REMARK_EXPLAIN = "d_remark";

}
