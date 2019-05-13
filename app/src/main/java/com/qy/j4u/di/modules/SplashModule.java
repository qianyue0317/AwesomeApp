package com.qy.j4u.di.modules;

import com.qy.j4u.app.main.views.SplashView;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    private SplashView mView;

    public SplashModule(SplashView view) {
        mView = view;
    }

    @Provides
    public SplashView provideView() {
        return this.mView;
    }

}
