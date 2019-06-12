package com.qy.j4u.base;

import android.os.Bundle;

import com.qy.j4u.lib.JLog;

import javax.inject.Inject;

import androidx.annotation.Nullable;

/**
 * 此项目的MVP模式的Activity都要继承基类
 * @param <P> Presenter的泛型
 */
public abstract class MVPBaseActivity<P extends BasePresenter> extends BaseActivity {

    public MVPBaseActivity() {
        super();
        JLog.i("MVPBaseActivity","构造方法");
    }


    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 让presenter能够监听Activity的生命周期 */
        getLifecycle().addObserver(mPresenter);
    }

    public P getPresenter() {
        return mPresenter;
    }

}
