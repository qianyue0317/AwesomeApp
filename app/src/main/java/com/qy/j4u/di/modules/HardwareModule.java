package com.qy.j4u.di.modules;

import com.qy.j4u.app.main.views.HardwareView;

import dagger.Module;
import dagger.Provides;

@Module
public class HardwareModule {

    private HardwareView mHardwareView;

    public HardwareModule(HardwareView view) {
        this.mHardwareView = view;
    }

    @Provides
    public HardwareView provideHardwareView() {
        return this.mHardwareView;
    }

}
