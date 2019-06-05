package com.qy.j4u.utils.livedatabus.lifecycle.ipc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qy.j4u.utils.livedatabus.lifecycle.LiveEventBus;
import com.qy.j4u.utils.livedatabus.lifecycle.ipc.IpcConst;
import com.qy.j4u.utils.livedatabus.lifecycle.ipc.decode.DecodeException;
import com.qy.j4u.utils.livedatabus.lifecycle.ipc.decode.IDecoder;
import com.qy.j4u.utils.livedatabus.lifecycle.ipc.decode.ValueDecoder;


/**
 * Created by liaohailiang on 2019/3/26.
 */
public class LebIpcReceiver extends BroadcastReceiver {

    private IDecoder decoder = new ValueDecoder();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (IpcConst.ACTION.equals(intent.getAction())) {
            String key = intent.getStringExtra(IpcConst.KEY);
            try {
                Object value = decoder.decode(intent);
                if (key != null) {
                    LiveEventBus
                            .get()
                            .with(key)
                            .post(value);
                }
            } catch (DecodeException e) {
                e.printStackTrace();
            }
        }
    }
}
