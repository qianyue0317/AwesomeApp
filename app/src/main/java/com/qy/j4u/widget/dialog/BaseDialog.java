package com.qy.j4u.widget.dialog;

import android.content.Context;

import androidx.appcompat.app.AppCompatDialog;

/**
 * 所有dialog的基类
 */
public class BaseDialog extends AppCompatDialog {
    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
