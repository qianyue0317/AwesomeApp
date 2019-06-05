package com.qy.j4u.utils.livedatabus.lifecycle.ipc.decode;

import android.content.Intent;

/**
 * Created by liaohailiang on 2019/3/25.
 */
public interface IDecoder {

    Object decode(Intent intent) throws DecodeException;
}
