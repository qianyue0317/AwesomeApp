package com.qy.j4u.di.modules;

import com.qy.j4u.app.main.views.MainView;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private MainView mMainView;

    public MainModule(MainView mainView) {
        mMainView = mainView;
    }

    @Provides
    public MainView provideMainView() {
        return mMainView;
    }

}
