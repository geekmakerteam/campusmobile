package com.geeklub.vass.mc4android.app.beans;

import java.util.List;

/**
 * Created by hp on 2014/4/19.
 */
public class LoginStatus {
    private boolean status;
    private List<String> datas;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
