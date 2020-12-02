package com.qy.j4u.j4uLib.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

/**
 * 控件最上层的基类
 */
public class BaseButton extends AppCompatButton {
    public BaseButton(Context context) {
        super(context);
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
