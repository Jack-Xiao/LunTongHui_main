package com.louie.luntonghui.event;

/**
 * Created by Jack on 16/4/9.
 */
public class ConfirmDispatchGatheringEvent {
    private String mOrderId;

    public ConfirmDispatchGatheringEvent(String orderId) {
        this.mOrderId = orderId;
    }
}
