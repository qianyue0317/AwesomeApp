package com.qy.j4u.widget.dialog;

import android.content.Context;

/**
 * loading框的接口, 抽象出接口, 在BaseActivity进行接口的依赖,设置默认的实现
 * 如果子类或者以后需要变loading框的样式,直接依赖不同的实现类即可
 */
public interface LoadingInterface {
    void show(Context context,String content,boolean cancelable);
    void hide();

    void dismiss();
}
