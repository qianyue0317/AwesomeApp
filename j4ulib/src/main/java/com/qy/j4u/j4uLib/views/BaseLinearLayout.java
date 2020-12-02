package com.qy.j4u.j4uLib.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class BaseLinearLayout extends LinearLayout {
    public BaseLinearLayout(Context context) {
        super(context);
    }

    public BaseLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
