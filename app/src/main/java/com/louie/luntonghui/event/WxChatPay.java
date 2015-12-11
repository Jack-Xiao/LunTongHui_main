package com.louie.luntonghui.event;

/**
 * Created by Jack on 15/11/18.
 */
public class WxChatPay {
    private int mResult;

    public WxChatPay(int result){
        mResult = result;
    }

    public int getResult() {
        return mResult;
    }
}
