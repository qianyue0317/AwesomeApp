package com.qy.j4u.widget.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.qy.j4u.R;

import androidx.appcompat.widget.AppCompatTextView;

public class NormalGrayTextView extends  AppCompatTextView{
    public NormalGrayTextView(Context context) {
        super(context);
    }

    public NormalGrayTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }


    public NormalGrayTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        Resources resources = context.getResources();
        setTextSize(resources.getDimension(R.dimen.normal_text_size));
        setTextColor(resources.getColor(R.color.colorGrayText));
    }

}
