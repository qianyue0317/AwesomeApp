package com.qy.j4u.app.main.presenters;

import com.qy.j4u.app.main.views.MainView;
import com.qy.j4u.base.BasePresenter;

public class MainPresenter extends BasePresenter<MainView> {
    public MainPresenter(MainView mainView) {
        super(mainView);
    }

    public void testGetView() {
        getView().testProxy("hello");
    }

}
