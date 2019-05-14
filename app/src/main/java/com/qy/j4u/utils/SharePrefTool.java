package com.qy.j4u.utils;

import com.blankj.utilcode.util.SPUtils;
import com.qy.j4u.global.constants.SharePreferencesKeys;

import java.util.HashMap;
import java.util.Map;

public class SharePrefTool {

    /**
     * 工具实例缓存
     */
    private static Map<String, SharePrefTool> mToolCache = new HashMap<>();

    private SPUtils mSpUtils;

    private SharePrefTool(String shareName) {
        mSpUtils = SPUtils.getInstance(shareName);
    }

    /**
     * 根据名称进行实例化
     *
     * @param shareName 共享参数文件名称
     */
    public static SharePrefTool getShareTool(String shareName) {
        SharePrefTool instance;
        if (mToolCache.containsKey(shareName)) {// 先从缓存中拿
            instance = mToolCache.get(shareName);
        } else {
            instance = new SharePrefTool(shareName);
            mToolCache.put(shareName, instance);
        }
        return instance;
    }

    /**
     * 获取默认的共享参数文件
     */
    public static SharePrefTool getDefaultInstance() {
        return getShareTool(SharePreferencesKeys.DEFAULT_NAME);
    }


    public void putInt(String key, int value) {
        mSpUtils.put(key, value);
    }

    public void putBoolean(String key, boolean value) {
        mSpUtils.put(key, value);
    }

    public void putFloat(String key, float value) {
        mSpUtils.put(key, value);
    }

    public void putLong(String key, long value) {
        mSpUtils.put(key, value);
    }

    public void putString(String key, String value) {
        mSpUtils.put(key, value);
    }


    public int getInt(String key, int defaultValue) {
        return mSpUtils.getInt(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSpUtils.getBoolean(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return mSpUtils.getFloat(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return mSpUtils.getLong(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return mSpUtils.getString(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return mSpUtils.getAll();
    }


    public boolean containsKey(String key) {
        return mSpUtils.contains(key);
    }

    public void removeKey(String key) {
        mSpUtils.remove(key);
    }


    public void clear() {
        mSpUtils.clear();
    }


}
