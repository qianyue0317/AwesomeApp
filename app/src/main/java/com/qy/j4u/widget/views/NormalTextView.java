package com.qy.j4u.widget.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import com.qy.j4u.R;

import androidx.appcompat.widget.AppCompatTextView;

public class NormalTextView extends  AppCompatTextView{
    public NormalTextView(Context context) {
        super(context);
    }

    public NormalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }


    public NormalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        Resources resources = context.getResources();
        setTextSize(resources.getDimension(R.dimen.normal_text_size));
        setTextColor(resources.getColor(R.color.colorNormalText));
    }



}
