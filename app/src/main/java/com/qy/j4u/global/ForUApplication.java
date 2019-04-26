package com.qy.j4u.global;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import cn.jpush.android.api.JPushInterface;

/**
 * 项目的Application类
 * Created by abc on 2016/11/2.
 */
public class ForUApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JPush();
    }

    private void JPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
