package com.qy.j4u.app.main.presenters;

import com.qy.j4u.app.main.views.HardwareView;
import com.qy.j4u.base.BasePresenter;
import com.qy.j4u.model.http.ApiService;

import javax.inject.Inject;

public class HardwarePresenter extends BasePresenter<HardwareView> {

    @Inject
    public HardwarePresenter(HardwareView hardwareView, ApiService apiService) {
        super(hardwareView, apiService);
    }

}
