package com.geeklub.vass.mc4android.app.beans;

import java.util.List;

/**
 * Created by hp on 2014/4/23.
 */
public class CallNamesResult {
    private List<Student> users;
    private int total;


    public List<Student> getUsers() {
        return users;
    }

    public void setUsers(List<Student> users) {
        this.users = users;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
