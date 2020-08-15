package com.qy.j4u.iot;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.aliyun.alink.dm.api.DeviceInfo;
import com.aliyun.alink.linkkit.api.ILinkKitConnectListener;
import com.aliyun.alink.linkkit.api.IoTMqttClientConfig;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linkkit.api.LinkKitInitParams;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tools.AError;
import com.google.gson.Gson;
import com.qy.j4u.lib.JLog;

import java.util.HashMap;
import java.util.Map;

public class IOTConnectService extends Service {
    private static final String TAG = IOTConnectService.class.getSimpleName();

    private String productKey = "a1ecL1xFlbu";

    private String deviceName = "qy_oneplus7_pro";

    private String deviceSecret = "iReeyflnRaqu15PvwxcO6PtsWCO8CNg3";

    @Override
    public void onCreate() {
        super.onCreate();
        JLog.i(TAG, "onCreate");
        /*
         * 设置设备三元组信息
         */
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.productKey = productKey;// 产品类型
        deviceInfo.deviceName = deviceName;// 设备名称
        deviceInfo.deviceSecret = deviceSecret;// 设备密钥
        /*
         * 设置设备当前的初始状态值，属性需要和云端创建的物模型属性一致
         * 如果这里什么属性都不填，物模型就没有当前设备相关属性的初始值。
         * 用户调用物模型上报接口之后，物模型会有相关数据缓存。
         */
        Map<String, ValueWrapper> propertyValues = new HashMap<>();
        // 示例
        // propertyValues.put("LightSwitch", new ValueWrapper.BooleanValueWrapper(0));
        IoTMqttClientConfig clientConfig = new IoTMqttClientConfig(productKey, deviceName, deviceSecret);
        LinkKitInitParams params = new LinkKitInitParams();
        params.deviceInfo = deviceInfo;
        params.propertyValues = propertyValues;
        params.mqttClientConfig = clientConfig;
        /*
         * 设备初始化建联
         * onError 初始化建联失败，需要用户重试初始化。如因网络问题导致初始化失败。
         * onInitDone 初始化成功
         */
        LinkKit.getInstance().init(this, params, new ILinkKitConnectListener() {
            @Override
            public void onError(AError error) {
                // 初始化失败 error包含初始化错误信息
                JLog.i(TAG, "连接失败：" + new Gson().toJson(error));
            }
            @Override
            public void onInitDone(Object data) {
                // 初始化成功 data 作为预留参数
                JLog.i(TAG, "连接成功：" + new Gson().toJson(data));
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ConnectBinder();
    }

    public class ConnectBinder extends Binder {

    }
}
