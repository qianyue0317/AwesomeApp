package com.qy.j4u.global;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.qy.j4u.pojo.DaoMaster;
import com.qy.j4u.pojo.DaoSession;

import org.greenrobot.greendao.database.Database;

import cn.jpush.android.api.JPushInterface;

/**
 * 项目的Application类
 * Created by abc on 2016/11/2.
 */
public class ForUApplication extends Application {

    private static ForUApplication sInstance;
    private DaoSession mDaoSession;

    public static ForUApplication getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        startCoreService();
        initJPush();
        initGreenDao();
    }

    private void startCoreService() {
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getGreenDaoSession() {
        return mDaoSession;
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
