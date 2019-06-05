package com.qy.j4u.utils.livedatabus.lifecycle.ipc.encode;

import android.content.Intent;

/**
 * Created by liaohailiang on 2019/3/25.
 */
public interface IEncoder {

    void encode(Intent intent, Object value) throws EncodeException;
}
