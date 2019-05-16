package com.qy.j4u.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qy.j4u.di.components.DaggerNetComponent;
import com.qy.j4u.di.components.NetComponent;
import com.qy.j4u.global.constants.Constants;
import com.qy.j4u.pojo.DaoMaster;
import com.qy.j4u.pojo.DaoSession;

import org.greenrobot.greendao.database.Database;
import org.litepal.LitePal;

import androidx.multidex.MultiDex;
import cn.jpush.android.api.JPushInterface;
import es.dmoral.toasty.Toasty;

/**
 * 项目的Application类
 * Created by abc on 2016/11/2.
 */
public class ForUApplication extends Application {

    private static ForUApplication sInstance;
    private DaoSession mDaoSession;
    private NetComponent mNetComponent;


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
        initUser();
        initActivityLife();
        initARouter();
        initNetModule();
        startCoreService();
        initJPush();
        initLitePal();
        initGreenDao();
        initToasty();
    }

    private void initLitePal() {
        LitePal.initialize(this);
    }

    private void initUser() {
        User.initFromLocal();
    }

    /**
     * 注册activity生命周期监听
     */
    private void initActivityLife() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//                JLog.i("activity_life", "onActivityCreated:" + activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
//                JLog.i("activity_life", "onActivityStarted:" + activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
//                JLog.i("activity_life", "onActivityResumed:" + activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
//                JLog.i("activity_life", "onActivityPaused:" + activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
//                JLog.i("activity_life", "onActivityStopped:" + activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//                JLog.i("activity_life", "onActivitySaveInstanceState:" + activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
//                JLog.i("activity_life", "onActivityDestroyed:" + activity);
            }
        });
    }

    public NetComponent getNetComponent() {
        return mNetComponent == null ? DaggerNetComponent.create() : mNetComponent;
    }

    private void initNetModule() {
        mNetComponent = DaggerNetComponent.create();
    }

    private void initToasty() {
        Toasty.Config.getInstance()
                .tintIcon(true)
                .setTextSize(13)
                .allowQueue(true)
                .apply();
    }

    private void initARouter() {
        if (Constants.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
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
