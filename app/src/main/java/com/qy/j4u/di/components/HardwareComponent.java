package com.qy.j4u.di.components;

import com.qy.j4u.app.main.fragments.HardwareFragment;
import com.qy.j4u.di.modules.HardwareModule;
import com.qy.j4u.di.scopes.J4UScope;

import dagger.Component;

@J4UScope
@Component(modules = HardwareModule.class,dependencies = NetComponent.class)
public interface HardwareComponent {
    void inject(HardwareFragment fragment);
}
