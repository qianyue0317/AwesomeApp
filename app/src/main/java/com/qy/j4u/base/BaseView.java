package com.qy.j4u.base;

/**
 * Created by abc on 2016/11/17.
 * modified by qy on 2019.05.10
 * mvp中view的基类 所有view都要继承
 */

public interface BaseView {
    void showLoading(String msg);

    void hideLoading();
}
