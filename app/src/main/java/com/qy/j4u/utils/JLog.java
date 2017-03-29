package com.qy.j4u.utils;


import com.orhanobut.logger.Logger;

/**
 * 此项目的log类
 * Created by qianyue on 2016/11/14.
 */

public class JLog {
    private static final String TAG = "J4U";
    /**
     * log开关,默认开启
     */
    private static final boolean LOG = true;


    static {
        Logger.init(TAG);
    }


    public static void i(String content) {
        if (LOG) {
            Logger.i(TAG, content);
        }
    }

    public static void e(String content) {
        if (LOG) {
            Logger.e(TAG, content);
        }
    }

    public static void d(String content) {
        if (LOG) {
//            Log.d(TAG, content);
            Logger.d(content);
        }
    }

    public static void w(String content) {
        if (LOG) {
            Logger.w(TAG, content);
        }
    }

    public static void v(String content) {
        if (LOG) {
            Logger.v(TAG, content);
        }
    }

}
