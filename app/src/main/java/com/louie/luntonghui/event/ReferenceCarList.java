package com.louie.luntonghui.event;

/**
 * Created by Administrator on 2015/6/26.
 */
public class ReferenceCarList {
    private double totalPrice;
    public static final double INITVALUE = 0.00;

    public ReferenceCarList(double totalPrice){
        this.totalPrice = totalPrice;
    }
    public ReferenceCarList(){
        this.totalPrice = INITVALUE;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append(totalPrice)
                .toString();
    }
}