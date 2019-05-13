package com.qy.j4u.di.components;

import com.qy.j4u.app.main.activities.MainActivity;
import com.qy.j4u.di.modules.MainModule;
import com.qy.j4u.di.scopes.J4UScope;

import dagger.Component;

@J4UScope
@Component(modules = MainModule.class,dependencies = NetComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
