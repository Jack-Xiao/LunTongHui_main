package com.louie.luntonghui.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2015/6/16.
 */
public class EncoderURL {

    public static String encode(String what) {
        try {
            return URLEncoder.encode(what,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
