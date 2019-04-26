package com.qy.j4u.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qy.j4u.utils.JLog;

public class J4UMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        JLog.i(String.valueOf(intent));
    }
}
