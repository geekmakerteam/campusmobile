package com.geeklub.vass.mc4android.app.utils;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2014/4/5.
 */
public class FastJSONUtil {

    public static <T> T getObject(String jsonString,Class<T> cls){
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }


    public static <T> List<T> getObjects(String jsonString,Class<T> cls){
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
