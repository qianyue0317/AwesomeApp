package com.qy.j4u.base;

import javax.inject.Inject;

/**
 * 此项目的MVP模式的Activity都要继承基类
 * @param <P> Presenter的泛型
 */
public abstract class MVPBaseActivity<P extends BasePresenter> extends BaseActivity {

    /**
     * 通过
     */
    @Inject
    protected P mP;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mP != null) {
            mP.detach();
            mP.cancel();
        }
    }
}
