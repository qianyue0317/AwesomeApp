package com.qy.j4u.app.main.fragments;

import android.os.Bundle;
import android.view.View;

import com.qy.j4u.R;
import com.qy.j4u.app.main.presenters.HardwarePresenter;
import com.qy.j4u.app.main.views.HardwareView;
import com.qy.j4u.base.MVPBaseFragment;
import com.qy.j4u.di.components.DaggerHardwareComponent;
import com.qy.j4u.di.modules.HardwareModule;
import com.qy.j4u.global.ForUApplication;

public class HardwareFragment extends MVPBaseFragment<HardwarePresenter> {

    public static HardwareFragment newInstance() {
        return new HardwareFragment();
    }

    @Override
    protected void daggerInject() {
        DaggerHardwareComponent.builder().hardwareModule(new HardwareModule(mView))
                .netComponent(ForUApplication.getInstance().getNetComponent())
                .build().inject(this);
    }

    @Override
    protected void initVariables(Bundle bundle) {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hardware;
    }

    @Override
    protected void loadData() {

    }

    private HardwareView mView = new HardwareView() {
        @Override
        public void showLoading(String msg) {

        }

        @Override
        public void hideLoading() {

        }
    };

}
