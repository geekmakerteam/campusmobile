package com.geeklub.vass.mc4android.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hp on 2014/4/8.
 */
public class LoginUtil {
    private final static String SP_USER_INFO = "user_info";
    private final static String PASS_WORD    = "pass_word";
    private final static String USER_NAME    = "user_name";


    /**
     * 保存密码
     * @param context
     * @param password  密码
     */
    public static void setPassWord(Context context,String password){
        SharedPreferences sp = context.getSharedPreferences(SP_USER_INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PASS_WORD,password);
        editor.commit();
    }

    /**
     * 得到保存的密码
     * @param context
     * @return
     */
    public static  String getPassWord(Context context){
        SharedPreferences sp = context.getSharedPreferences(SP_USER_INFO,Context.MODE_PRIVATE);
        return sp.getString(PASS_WORD,"");
    }

    /**
     * 保存用户名
     * @param context
     * @param username  用户名
     */
    public static void setUserName(Context context,String username){
        SharedPreferences sp = context.getSharedPreferences(SP_USER_INFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_NAME,username);
        editor.commit();
    }


    /**
     * 得到用户名
     * @param context
     * @return  用户名
     */
    public static  String getUserName(Context context){
        SharedPreferences sp = context.getSharedPreferences(SP_USER_INFO,Context.MODE_PRIVATE);
        return sp.getString(USER_NAME,"");
    }
}
