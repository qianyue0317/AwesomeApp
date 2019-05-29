package com.qy.j4u.app.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.qy.j4u.R;
import com.qy.j4u.app.main.presenters.SplashPresenter;
import com.qy.j4u.app.main.views.SplashView;
import com.qy.j4u.base.BaseActivity;
import com.qy.j4u.di.components.DaggerSplashComponent;
import com.qy.j4u.di.modules.SplashModule;
import com.qy.j4u.global.ForUApplication;
import com.qy.j4u.services.KeepService;
import com.qy.j4u.utils.ARouterWrapper;
import com.qy.j4u.utils.JLog;
import com.qy.j4u.utils.JUtil;

import javax.inject.Inject;

import androidx.annotation.Nullable;


/**
 * Created by qy on 2016/11/2.
 * modified by qy on 2019/05/13
 */

public class SplashActivity extends BaseActivity implements Handler.Callback {

    private static final int JUMP = 1;
    private static final long DURATION = 2000;
    private Handler mHandler;
    @Inject
    SplashPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            JLog.d("MainActivity", "onCreate intent flag FLAG_ACTIVITY_BROUGHT_TO_FRONT");
            finish();
        }
        startService(new Intent(this, KeepService.class));
    }

    @Override
    protected void daggerInject() {
        DaggerSplashComponent.builder()
                .splashModule(new SplashModule(mView))
                .netComponent(ForUApplication.getInstance().getNetComponent())
                .build()
                .inject(this);
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    protected String getToolBarTitle() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        requestFullScreen();
        return R.layout.activity_splash;
    }

    @Override
    protected void initVariables() {
        mHandler = new Handler(this);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mPresenter.loginWithUUid(JUtil.getDeviceUUID(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(JUMP);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == JUMP) {
            ARouterWrapper.build(ARouterWrapper.Route.MAIN).navigation(this);
            finish();
        }
        return true;
    }

    private SplashView mView = new SplashView() {
        @Override
        public void showLoading(String msg) {

        }

        @Override
        public void hideLoading() {

        }

        @Override
        public void onLoginSuccess(String s) {
            Message obtain = Message.obtain();
            obtain.what = JUMP;
            mHandler.sendMessageDelayed(obtain, DURATION);
        }

        @Override
        public void onLoginError() {
            Message obtain = Message.obtain();
            obtain.what = JUMP;
            mHandler.sendMessageDelayed(obtain, DURATION);
        }
    };

}
