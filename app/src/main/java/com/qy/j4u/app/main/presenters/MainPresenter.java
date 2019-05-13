package com.qy.j4u.app.main.presenters;

import com.qy.j4u.app.main.views.MainView;
import com.qy.j4u.base.BasePresenter;
import com.qy.j4u.model.http.ApiService;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainView> {


    @Inject
    public MainPresenter(MainView mainView, ApiService apiService) {
        super(mainView, apiService);
    }

    public void testGetView() {
        getView().testProxy("hello");
    }

}
