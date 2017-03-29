package com.qy.j4u.utils.display;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 尺寸工具
 * Created by abc on 2016/11/18.
 */

public class SizeUtil {

    private static DisplayMetrics sMetrics;

    /**
     * 获取屏幕宽度,单位是px
     *
     * @param context 上下文对象
     * @return
     */
    public static int getScreenWidthPx(Context context) {
        return getMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕宽度,单位是dp
     *
     * @param context 上下文对象
     * @return
     */
    public static int getScreenWidthDp(Context context) {
        return px2dp(context, getMetrics(context).widthPixels);
    }


    /**
     * 获取屏幕高度,单位是px
     *
     * @param context 上下文对象
     * @return 屏幕高度px
     */
    public static int getScreenHeightPx(Context context) {
        return getMetrics(context).heightPixels;
    }

    /**
     * 获取屏幕高度,单位是px
     *
     * @param context 上下文对象
     * @return 屏幕高度px
     */
    public static int getScreenHeightDp(Context context) {
        return px2dp(context, getMetrics(context).heightPixels);
    }

    private static DisplayMetrics getMetrics(Context context) {
        if (sMetrics == null) {
            sMetrics = context.getResources().getDisplayMetrics();
        }
        return sMetrics;
    }

    /**
     * px转成dp
     *
     * @param pxValue px的值
     * @return 根据px的值, 计算出的dp的值
     */
    public static int px2dp(Context context, int pxValue) {
        float density = getMetrics(context).density;
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * dp转成px
     *
     * @param dpValue dp的值
     * @return 根据dp的值, 计算出的px的值
     */
    public static int dp2px(Context context, int dpValue) {
        float density = getMetrics(context).density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * px值转sp值
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, int pxValue) {
        float scaledDensity = getMetrics(context).scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);
    }


    /**
     * sp值转成px值
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, int spValue) {
        float scaledDensity = getMetrics(context).scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

}
