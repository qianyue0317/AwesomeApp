package com.qy.j4u.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.qy.j4u.eventmessages.RaspberryIp;
import com.qy.j4u.lib.JLog;

import org.greenrobot.eventbus.EventBus;

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
                if (TextUtils.equals(title, "raspberry_ip")) {
                    EventBus.getDefault().postSticky(new RaspberryIp().setIp(message));
                }
            }
        }
    }
}
