package com.qy.j4u.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;

import com.qy.j4u.utils.JLog;

/**
 * 远程进程中的核心服务, 可通过监听屏幕
 * 利用广播自我拉起  在oncreate里注册广播接受者, 在onDestroy中发送广播
 */
public class RemoteCoreService extends Service {

    //    private ScreenStatusBroadCastReceiver mScreenStatusBroadCastReceiver;
//    private RestartBroadcastReceiver mRestartBroadcastReceiver;
    private OtherBroadcastReceiver mOtherBroadcastReceiver; ;
    private static final String RESTART_ACTION = "restart_core_service";

    @Override
    public void onCreate() {
        super.onCreate();
//        mRestartBroadcastReceiver = new RestartBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(RESTART_ACTION);
//        registerReceiver(mRestartBroadcastReceiver, intentFilter);

//        mScreenStatusBroadCastReceiver = new ScreenStatusBroadCastReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_SCREEN_ON);
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        registerReceiver(mScreenStatusBroadCastReceiver, filter);

        mOtherBroadcastReceiver = new OtherBroadcastReceiver();
        IntentFilter otherFilter = new IntentFilter();
        otherFilter.addAction("com.xiaomi.mipush.RECEIVE_MESSAGE");
        registerReceiver(mOtherBroadcastReceiver, otherFilter);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CoreBinder();
    }

    static class CoreBinder extends com.qy.j4u.CoreBinder.Stub {


        @Override
        public String getContent() throws RemoteException {
            return "这是从服务端返回的数据";
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mScreenStatusBroadCastReceiver);
        JLog.i("remote_core_service:destroy");
//        sendBroadcast(new Intent(RESTART_ACTION));
//        if (mRestartBroadcastReceiver != null) {
//            unregisterReceiver(mRestartBroadcastReceiver);
//        }
        if (mOtherBroadcastReceiver != null) {
            unregisterReceiver(mOtherBroadcastReceiver);
        }
    }

//    static class ScreenStatusBroadCastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            context.startService(new Intent(context, CoreService.class));
//            JLog.i(Intent.ACTION_SCREEN_OFF.equals(intent.getAction()) ? "屏幕暗了" : "屏幕亮了");
//        }
//    }

    static class RestartBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            JLog.i("收到重启服务请求");
            context.startService(new Intent(context, RemoteCoreService.class));
        }
    }
    static class OtherBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            JLog.i("收到了小米的广播");
        }
    }

}
