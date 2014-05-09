package com.geeklub.vass.mc4android.app.utils;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;


/**
 * Created by hp on 2014/4/5.
 */
public class MCRestClient {


    private static final String BASE_URL = "http://mcampus001.duapp.com/";



    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();


    private static String getAbsoluteUrl(String relativeUrl){
        return BASE_URL + relativeUrl;
    }



	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler,Context context) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        asyncHttpClient.setCookieStore(cookieStore);
        asyncHttpClient.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler,Context context) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        asyncHttpClient.setCookieStore(cookieStore);
        asyncHttpClient.post(getAbsoluteUrl(url), params, responseHandler);
    }

//专门给学生扫描二维码使用的
    public static void get_qrcode(String url, RequestParams params, AsyncHttpResponseHandler responseHandler,Context context) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        asyncHttpClient.setCookieStore(cookieStore);
        asyncHttpClient.post(url, params, responseHandler);
    }
}
