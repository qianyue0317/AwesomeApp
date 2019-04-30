package com.qy.j4u.base;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity的基类
 * Created by abc on 2016/11/2.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        initListener();
    }

    /**
     * 初始化变量值等等,例如:getIntent()从Intent中获取传过来的值
     */
    protected abstract void initVariable();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

}
