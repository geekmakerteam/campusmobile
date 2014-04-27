package com.geeklub.vass.mc4android.app.beans;

import com.geeklub.vass.mc4android.app.utils.MCApplication;

/**
 * Created by hp on 2014/4/26.
 */
public class DrawerMenuItem {
    private String mNameRes;
    private int mIconRes;
    private MCApplication mcApplication = MCApplication.getApplication();

    public String getmNameRes() {
        return mNameRes;
    }

    public void setmNameRes(String mNameRes) {
        this.mNameRes = mNameRes;
    }

    public int getmIconRes() {
        return mIconRes;
    }

    public void setmIconRes(int mIconRes) {
        this.mIconRes = mIconRes;
    }

    public DrawerMenuItem(int nameRes,int iconRes){
        mNameRes =  mcApplication.getResources().getText(nameRes).toString();
        mIconRes = iconRes;
    }
}
