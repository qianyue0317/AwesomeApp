package com.qy.j4u.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.jakewharton.rxbinding3.view.RxView;
import com.qy.j4u.utils.JLog;
import com.qy.j4u.utils.RxLifecycleUtils;
import com.uber.autodispose.AutoDisposeConverter;

import java.util.concurrent.TimeUnit;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Fragment基类
 * Created by abc on 2016/11/2.
 */
@SuppressWarnings("all")
public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;
    protected View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        JLog.i(getClass().getSimpleName() + "setUserVisibleHint:" + isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        daggerInject();
        initVariables(getArguments());
        ButterKnife.bind(this, mRootView);
        initView(mRootView);
        return mRootView;
    }

    protected abstract void daggerInject();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * rxjava observer绑定生命周期
     */
    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return RxLifecycleUtils.bindLifecycle(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     * 隐藏软键盘
     */
    protected void hideInputMethod() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService
                (Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getWindow().peekDecorView()
                .getWindowToken(), 0);
    }


    /**
     * 子类直接调用此方法实例化控件
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        if (mRootView == null) {
            return null;
        }

        return (T) mRootView.findViewById(id);
    }

    /**
     * 初始化变量
     */
    protected abstract void initVariables(Bundle bundle);

    /**
     * 初始化控件
     */
    protected abstract void initView(View view);

    /**
     * 子类决定布局id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * 加载数据
     */
    protected abstract void loadData();


    /**
     * 避免按键抖动的设置监听器  0.5秒内的点击只取一次
     *
     * @param view            被点击的view
     * @param onClickListener 监听器
     */
    @SuppressLint("CheckResult")
    protected void setOnClickSolveShake(final View view, final View.OnClickListener
            onClickListener) {
        //noinspection ResultOfMethodCallIgnored
        RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                onClickListener.onClick(view);
            }
        });
    }

}
