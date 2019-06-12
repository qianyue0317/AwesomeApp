package com.qy.j4u.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qy.j4u.lib.JLog;
import com.qy.j4u.services.CoreService;

public class ScreenStatusBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, CoreService.class));
        JLog.i(Intent.ACTION_SCREEN_OFF.equals(intent.getAction()) ? "屏幕暗了" : "屏幕亮了");
    }
}
