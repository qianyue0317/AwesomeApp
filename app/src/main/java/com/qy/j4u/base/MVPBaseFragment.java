package com.qy.j4u.base;

import javax.inject.Inject;

public abstract class MVPBaseFragment<P extends BasePresenter> extends BaseFragment {

    @Inject
    protected P mP;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mP != null) {
            mP.detach();
            mP.cancel();
        }
    }
}
