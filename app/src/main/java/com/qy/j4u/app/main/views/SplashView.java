package com.qy.j4u.app.main.views;

import com.qy.j4u.base.BaseView;

public interface SplashView extends BaseView {
    void onLoginSuccess(String content);

    void onLoginError();
}
