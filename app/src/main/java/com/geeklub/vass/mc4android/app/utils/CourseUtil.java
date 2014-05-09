package com.geeklub.vass.mc4android.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hp on 2014/4/20.
 */
public class CourseUtil {
    //    将课表信息保存到名字为sp_course的一个SharedPreferences文件中
    private final static String COURSE_DATA = "course_data";
    private static MCApplication mApplication = MCApplication.getApplication();

    public static void saveData(Context context,String tablename,String data){
        SharedPreferences sharedPreferences = context.getSharedPreferences(COURSE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tablename +"_timetable",data);
        editor.putBoolean(tablename +"_tablesave",true);
        editor.commit();
    }

    public static String getData(Context context,String tablename){
        SharedPreferences sharedPreferences = context.getSharedPreferences(COURSE_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getString(tablename +"_timetable","");
    }

    public static boolean isSave(Context context,String tablename){
        SharedPreferences sharedPreferences = context.getSharedPreferences(COURSE_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(tablename +"_tablesave",false);
    }


}
