package com.qy.j4u.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.qy.j4u.utils.JLog;

import cn.jpush.android.api.JPushInterface;

public class J4UMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        JLog.i(String.valueOf(intent));
        if ("cn.jpush.android.intent.MESSAGE_RECEIVED".equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String title = extras.getString(JPushInterface.EXTRA_TITLE);
                String message = extras.getString(JPushInterface.EXTRA_MESSAGE);
                JLog.i("收到的消息标题:" + title);
                JLog.i("收到的消息内容:" + message);
            }
        }
    }
}
