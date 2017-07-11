package com.qy.j4u.utils;


import com.orhanobut.logger.Logger;
import com.qy.j4u.global.constants.Constant;

/**
 * 此项目的log类
 * Created by qianyue on 2016/11/14.
 */

public class LoLog {

    /**
     * 默认的tag
     */
    private static final String TAG = "J4U";

    static {
        Logger.init(TAG);
    }


    public static void i(String content){
        if (Constant.LOG) {
            Logger.i(content);
        }
    }

    public static void v(String content){
        if (Constant.LOG) {
            Logger.v(content);
        }
    }

    public static void d(String content){
        if (Constant.LOG) {
            Logger.d(content);
        }
    }

    public static void e(String content){
        if (Constant.LOG) {
            Logger.e(content);
        }
    }

    public static void w(String content){
        if (Constant.LOG) {
            Logger.w(content);
        }
    }


    /**
     * 直接打印json字符串,如果不是json 日志会提示不是json,,(不抛异常)
     * @param json  json字符串
     */
    public static void json(String json) {
        if (Constant.LOG) {
            Logger.json(json);
        }
    }

    public static void json(String tag, String json) {
        if (Constant.LOG) {
            Logger.t(tag).json(json);
        }
    }


    // -----------------------以下是自己写tag的方法---------------------------

    public static void i(String tag,String content){
        if (Constant.LOG) {
            Logger.t(tag).i(content);
        }
    }

    public static void v(String tag,String content){
        if (Constant.LOG) {
            Logger.t(tag).v(content);
        }
    }

    public static void d(String tag,String content){
        if (Constant.LOG) {
            Logger.t(tag).d(content);
        }
    }

    public static void e(String tag,String content){
        if (Constant.LOG) {
            Logger.t(tag).e(content);
        }
    }

    public static void w(String tag,String content){
        if (Constant.LOG) {
            Logger.t(tag).w(content);
        }
    }

}
