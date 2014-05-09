package com.geeklub.vass.mc4android.app.common;


import com.loopj.android.http.RequestParams;

/**
 * Created by hp on 2014/4/6.
 */
public class APIParams extends RequestParams {

    public APIParams with(String key, String value) {
        put(key, value);
        return this;
    }




}
