package com.qy.j4u.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.qy.j4u.R;
import com.qy.j4u.base.BaseActivity;
import com.qy.j4u.utils.ARouterWrapper;
import com.qy.j4u.utils.JLog;

import androidx.annotation.Nullable;


/**
 * Created by qy on 2016/11/2.
 */

public class SplashActivity extends BaseActivity implements Handler.Callback {

    private static final int JUMP = 1;
    private static final long DURATION = 2000;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            JLog.d("MainActivity","onCreate intent flag FLAG_ACTIVITY_BROUGHT_TO_FRONT");
            finish();
        }
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
        Message obtain = Message.obtain();
        obtain.what = JUMP;
        mHandler.sendMessageDelayed(obtain, DURATION);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(JUMP);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == JUMP) {
            ARouterWrapper.build(ARouterWrapper.Route.MAIN).navigation();
            finish();
        }
        return true;
    }
}
