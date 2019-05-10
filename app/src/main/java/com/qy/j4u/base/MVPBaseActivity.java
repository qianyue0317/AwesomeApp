package com.qy.j4u.base;

public abstract class MVPBaseActivity<P extends BasePresenter> extends BaseActivity {

    P mP;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mP != null) {
            mP.detach();
        }
    }
}
