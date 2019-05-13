package com.qy.j4u.base;

import com.qy.j4u.model.http.ApiService;
import com.qy.j4u.utils.JLog;
import com.qy.j4u.utils.RxLifecycleUtils;
import com.uber.autodispose.AutoDisposeConverter;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * presenter接口基类
 * Created by abc on 2016/11/2, modified by qy on 2019/5/3
 * 用动态代理监控view是否为空,并捕获view方法执行过程中未捕获的异常
 * 添加Activity,Fragment生命周期监听
 */
@SuppressWarnings("all")
public class BasePresenter<V extends BaseView> implements IPresenter {

    private final RealViewHandler mH;
    private WeakReference<V> mRealView;
    private V mProxyView;
    private ApiService mApiService;
    private LifecycleOwner mLifecycleOwner;


    public BasePresenter(V v, ApiService apiService) {
        mRealView = new WeakReference<>(v);
        Class<?> vClass =
                (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mH = new RealViewHandler(v);
        mProxyView = (V) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{vClass},
                mH);
        mApiService = apiService;
    }

    public ApiService getApiService() {
        return mApiService;
    }

    public V getView() {
        return mProxyView;
    }

    public boolean isAttached() {
        return mRealView != null && mRealView.get() != null;
    }


    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        if (null == mLifecycleOwner)
            throw new NullPointerException("lifecycleOwner == null");
        return RxLifecycleUtils.bindLifecycle(mLifecycleOwner);
    }


    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {
        mLifecycleOwner = owner;
        JLog.i("BasePresenter", "onCreate:" + owner + "  " + getClass().getName());
    }

    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {
//        JLog.i("BasePresenter", "onDestroy:" + getClass().getName());
        if (mRealView != null) {
            mRealView.clear();
            mRealView = null;
        }
        if (mH != null && mH.mTarget != null) {
            mH.mTarget.clear();
            mH.mTarget = null;
        }
    }

    @Override
    public void onLifecycleChanged(@NotNull LifecycleOwner owner, @NotNull Lifecycle.Event event) {
//        JLog.i("BasePresenter", "onLifecycleChanged:" + getClass().getName() + " " + event.name
//        ());
    }


    public class RealViewHandler implements InvocationHandler {
        WeakReference<V> mTarget;

        public RealViewHandler(V v) {
            mTarget = new WeakReference<>(v);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (mTarget != null && mTarget.get() != null) {
                try {
                    /* ***********捕获其他异常(此处是view的实际调用的位置)************ */
                    JLog.i("实际的view方法执行的位置---------");
                    Object invoke = method.invoke(mTarget.get(), args);
                    return invoke;
                } catch (Exception e) {
                    JLog.i("view在执行的时候出了异常=====");
                    e.printStackTrace();
                    return null;
                }
            }
            JLog.i("view是空");
            return null;
        }
    }


}
