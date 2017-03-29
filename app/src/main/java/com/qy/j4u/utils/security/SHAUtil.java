package com.qy.j4u.utils.security;

import com.qy.just4u.utils.JLog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * sha 加密工具类
 * Created by abc on 2016/11/18.
 */

public class SHAUtil {

    // TODO: 2016/11/18 这里尝试使用Jni编程进行加密
//    public static native String  encryptNative(String content);


    /**
     * 这里对字符串用SHA-256进行简单的加密
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-256");
            byte[] source = content.getBytes();
            byte[] digest = sha.digest(source);
            return bytes2Hex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            JLog.d("SHAUtil没有此种加密算法");
            return "";
        }
    }

    /**
     * byte数组转换为16进制字符串
     *
     * @param bts
     *            数据源
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}