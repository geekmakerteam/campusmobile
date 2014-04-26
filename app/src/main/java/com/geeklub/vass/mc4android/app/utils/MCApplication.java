package com.geeklub.vass.mc4android.app.utils;

import android.app.Application;

/**
 * Created by hp on 2014/4/19.
 */
public class MCApplication extends Application {
    private static MCApplication mApplication;


    public static MCApplication getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
