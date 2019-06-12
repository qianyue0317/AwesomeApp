package com.qy.j4u.lib;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * 此项目的log类
 * Created by qianyue on 2016/11/14.
 */

public class JLog {

    /**
     * 默认的tag
     */
    private static String TAG = "J4U";
    private static boolean LOG = true;

    public static void setLog(boolean log) {
        LOG = log;
    }

    public static void setTAG(String tag) {
        TAG = tag;
    }

    static {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(2)
                .tag(TAG)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.addLogAdapter(new DiskLogAdapter());
    }


    public static void i(String content) {
        if (LOG) {
            Logger.i(content);
        }
    }

    public static void i(Object object) {
        if (LOG) {
            Logger.i(String.valueOf(object));
        }
    }

    public static void v(String content) {
        if (LOG) {
            Logger.v(content);
        }
    }

    public static void d(String content) {
        if (LOG) {
            Logger.d(content);
        }
    }

    public static void e(String content) {
        if (LOG) {
            Logger.e(content);
        }
    }

    public static void w(String content) {
        if (LOG) {
            Logger.w(content);
        }
    }


    /**
     * 直接打印json字符串,如果不是json 日志会提示不是json,,(不抛异常)
     *
     * @param json json字符串
     */
    public static void json(String json) {
        if (LOG) {
            Logger.json(json);
        }
    }

    public static void json(String tag, String json) {
        if (LOG) {
            Logger.t(tag).json(json);
        }
    }


    // -----------------------以下是自己写tag的方法---------------------------

    public static void i(String tag, String content) {
        if (LOG) {
            Logger.t(tag).i(content);
        }
    }

    public static void v(String tag, String content) {
        if (LOG) {
            Logger.t(tag).v(content);
        }
    }

    public static void d(String tag, String content) {
        if (LOG) {
            Logger.t(tag).d(content);
        }
    }

    public static void e(String tag, String content) {
        if (LOG) {
            Logger.t(tag).e(content);
        }
    }

    public static void w(String tag, String content) {
        if (LOG) {
            Logger.t(tag).w(content);
        }
    }

}
