package com.qy.j4u.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.qy.j4u.global.constants.SharePreferencesKeys;

import java.util.Random;
import java.util.UUID;

import androidx.annotation.Nullable;

/**
 * 项目相关的小工具集合
 */
public class JUtil {

    public static String getDeviceUUID(Context context) {
        String uuid = loadDeviceUUID(context);
        if (TextUtils.isEmpty(uuid)) {
            uuid = buildDeviceUUID(context);
            saveDeviceUUID(context, uuid);
        }
        return uuid;
    }

    private static String buildDeviceUUID(Context context) {
        String androidId = getAndroidId(context);
        if ("9774d56d682e549c".equals(androidId)) {
            Random random = new Random();
            androidId = Integer.toHexString(random.nextInt())
                    + Integer.toHexString(random.nextInt())
                    + Integer.toHexString(random.nextInt());
        }
        return new UUID(androidId.hashCode(), getBuildInfo().hashCode()).toString();
    }

    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    private static void saveDeviceUUID(Context context, String uuid) {
        context.getSharedPreferences(SharePreferencesKeys.DEFAULT_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(SharePreferencesKeys.KEY_UUID, uuid)
                .apply();
    }

    @Nullable
    private static String loadDeviceUUID(Context context) {
        return context.getSharedPreferences(SharePreferencesKeys.DEFAULT_NAME, Context.MODE_PRIVATE)
                .getString(SharePreferencesKeys.KEY_UUID, null);
    }

    public static String getBuildInfo() {
        return Build.BRAND + "/" +
                Build.PRODUCT + "/" +
                Build.DEVICE + "/" +
                Build.ID + "/" +
                Build.VERSION.INCREMENTAL;
    }

}
