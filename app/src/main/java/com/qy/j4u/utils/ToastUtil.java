package com.qy.j4u.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.blankj.utilcode.util.Utils;

import es.dmoral.toasty.Toasty;

public class ToastUtil {
    private ToastUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static final int INFO = 1;
    private static final int WARNING = 2;
    private static final int ERROR = 3;
    private static final int SUCCESS = 4;
    private static final int NORMAL = 5;


    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     * @param type     显示类型是 WARNING INFO ERROR
     */
    @SuppressLint("ShowToast")
    public static void showToast(CharSequence text, int duration, int type) {
        Toast toast = null;
        switch (type) {
            case ERROR:
                toast = Toasty.error(Utils.getApp(), text, duration);
                break;
            case SUCCESS:
                toast = Toasty.success(Utils.getApp(), text, duration);
                break;
            case INFO:
                toast = Toasty.info(Utils.getApp(), text, duration);
                break;
            case WARNING:
                toast = Toasty.warning(Utils.getApp(), text, duration);
                break;
            case NORMAL:
                toast = Toasty.normal(Utils.getApp(), text, duration);
                break;
        }
        if (toast != null) {
            toast.show();
        }
    }

    /**
     * @param content 显示的内容
     */
    public static void showNormalShort(CharSequence content) {
        showToast(content, Toast.LENGTH_SHORT, NORMAL);
    }

    /**
     * @param content 显示的内容
     */
    public static void showSuccessShort(CharSequence content) {
        showToast(content, Toast.LENGTH_SHORT, SUCCESS);
    }

    /**
     * @param content 显示的内容
     */
    public static void showErrorShort(CharSequence content) {
        showToast(content, Toast.LENGTH_SHORT, ERROR);
    }

    /**
     * @param content 显示的内容
     */
    public static void showInfoShort(CharSequence content) {
        showToast(content, Toast.LENGTH_SHORT, INFO);
    }

    /**
     * @param content 显示的内容
     */
    public static void showWarningShort(CharSequence content) {
        showToast(content, Toast.LENGTH_SHORT, WARNING);
    }


}
