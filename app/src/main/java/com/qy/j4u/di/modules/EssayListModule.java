package com.qy.j4u.di.modules;

import com.qy.j4u.app.programming.views.EssayListView;

import dagger.Module;
import dagger.Provides;

@Module
public class EssayListModule {

    private EssayListView mView;

    public EssayListModule(EssayListView view) {
        this.mView = view;
    }

    @Provides
    public EssayListView provideEssayListView() {
        return mView;
    }

}
