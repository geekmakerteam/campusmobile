package com.geeklub.vass.mc4android.app.common;

/**
 * Created by hp on 2014/4/5.
 */
public class API {


	public final static String PUBIC="/api/classnews/publish";
    /**用户登录*/
    public final static String LOGIN = "/api/login";
    /**检查用户是否登录*/
    public final static String LOGIN_STATUS = "/api/login/status";
    /**学院新闻*/
    public final static String SCHOOL_NEWS = "/api/news/";
    /**班级通知*/
    public final static String CLASS_NEWS = "/api/classnews?page=";
    /**课程*/
    public final static String COURSE = "api/course";
    /**今天的课程*/
    public final static String TODAY_CPURSRS = "api/course/week/";
    /**老师点名*/
    public final static String CALL_NAMES = "/api/course/users";
    /**老师辅助签到*/
    public final static String HELP_SIGN = "/api/course/sign/assist?";

}
