package com.qy.j4u.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

/*
 * 将app运行中由于网络原因未上传成功的数据,重新上传
 */
class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        TODO("Return the communication channel to the service.")

        return null
    }
}
