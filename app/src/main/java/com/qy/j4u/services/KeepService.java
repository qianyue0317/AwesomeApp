package com.qy.j4u.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class KeepService extends Service {

    public native void createWatcher(String userId);

    public native void connectServer();

    @Override
    public void onCreate() {
        super.onCreate();
        createWatcher("123");
        connectServer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
