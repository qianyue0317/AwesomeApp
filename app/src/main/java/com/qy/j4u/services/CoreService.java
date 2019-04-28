package com.qy.j4u.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.qy.j4u.utils.JLog;

/**
 * 主进程内的核心服务
 */
public class CoreService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        JLog.i("coreservice启动了");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
