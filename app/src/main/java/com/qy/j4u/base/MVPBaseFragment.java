package com.qy.j4u.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class MVPBaseFragment<P extends BasePresenter> extends BaseFragment {

    @Inject
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        /* 让presenter能够监听Activity的生命周期 */
        getLifecycle().addObserver(mPresenter);
        return view;
    }
}
