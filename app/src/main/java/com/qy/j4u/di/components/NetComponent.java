package com.qy.j4u.di.components;


import com.qy.j4u.di.modules.NetModule;
import com.qy.j4u.model.http.ApiService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NetModule.class)
public interface NetComponent {
    ApiService getApiService();
}
