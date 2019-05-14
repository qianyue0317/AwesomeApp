package com.qy.j4u.di.components;

import com.qy.j4u.app.programming.activities.EssayListActivity;
import com.qy.j4u.di.modules.EssayListModule;
import com.qy.j4u.di.scopes.J4UScope;

import dagger.Component;

@J4UScope
@Component(modules = EssayListModule.class,dependencies = NetComponent.class)
public interface EssayListComponent {
    void inject(EssayListActivity activity);
}
