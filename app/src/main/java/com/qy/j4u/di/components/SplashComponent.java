package com.qy.j4u.di.components;

import com.qy.j4u.app.main.activities.SplashActivity;
import com.qy.j4u.di.modules.SplashModule;
import com.qy.j4u.di.scopes.J4UScope;

import dagger.Component;

@J4UScope
@Component(modules = SplashModule.class,dependencies = NetComponent.class)
public interface SplashComponent {
    void inject(SplashActivity activity);
}
